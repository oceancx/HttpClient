package com.oceancx.task;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;


/**
 * 
 * @author dujun
 * 
 * @param <Param>
 * @param <Result>
 */
public abstract class MultiTaskQuery<Param, Result> implements Runnable {

	/**
	 * 未加载状态，包括加载完成。
	 */
	public static final int QUERY_NOR = 0;
	/**
	 * 加载中。
	 */
	public static final int QUERY_ING = 1;
	/**
	 * 已取消。
	 */
	public static final int QUERY_CANCEL = 2;

	private final Object lock = 0;
	private CountDownLatch latch;

	private int state = QUERY_NOR;
	private final Param param;
	private Result result;
	private TaskError error;

	private int index;
	private MultiTask.OnMutiTaskQueryListener onMutiTaskQueryListener;

	public MultiTaskQuery(Param param) {
		this.param = param;
	}

	protected void setOnMutiTaskQueryListener(
			MultiTask.OnMutiTaskQueryListener onMutiTaskQueryListener) {
		this.onMutiTaskQueryListener = onMutiTaskQueryListener;
	}

	protected void setIndex(int index) {
		this.index = index;
	}

	protected void start(ExecutorService service, int index,
			MultiTask.OnMutiTaskQueryListener listener) {
		this.index = index;
		this.onMutiTaskQueryListener = listener;
		if (onMutiTaskQueryListener != null) {
			onMutiTaskQueryListener.onStart(index);
		}
		service.execute(this);
	}

	protected void cancel() {
		setState(QUERY_CANCEL);
	}

	public Param getParam() {
		return param;
	}

	public Result getResult() {
		return result;
	}

	public TaskError getError() {
		return error;
	}

	protected void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	/**
	 * {@link #}/ {@link #QUERY_ING} / {@link #QUERY_NOR}
	 * 
	 * @return
	 */
	public int getState() {
		synchronized (lock) {
			return state;
		}
	}

	private void setState(int state) {
		synchronized (lock) {
			if (state == QUERY_CANCEL && this.state == QUERY_ING) {
				// cancel querying,but not other state.
				this.state = state;
				System.out.println("query canceled");
			} else {
				this.state = state;
			}
		}
	}

	private void setResult(Result result) {
		synchronized (lock) {
			this.result = result;
		}
	}

	protected boolean isCancel() {
		synchronized (lock) {
			return state == QUERY_CANCEL;
		}
	}

	@Override
	public void run() {
		if (isCancel()) {
			return;
		}
		setState(QUERY_ING);
		Result taskResult = query(param);
		if (isCancel()) {
			return;
		}
		setResult(taskResult);
		setState(QUERY_NOR);
		if (onMutiTaskQueryListener != null) {
			onMutiTaskQueryListener.onStop(index);
		}
		if (latch != null) {
			latch.countDown();
		}
	}

	protected abstract Result query(Param param);

	@Override
	public String toString() {
		return "MultiTaskQuery [lock=" + lock + ", latch=" + latch + ", state="
				+ state + ", param=" + param + ", result=" + result
				+ ", error=" + error + ", index=" + index
				+ ", onMutiTaskQueryListener=" + onMutiTaskQueryListener + "]";
	}

}
