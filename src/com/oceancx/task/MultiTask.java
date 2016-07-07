package com.oceancx.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @param <Param>
 * @param <Result>
 * @author dujun
 */
public abstract class MultiTask<Param, Result> {

    /**
     * @author dujun
     */
    public interface OnMutiTaskQueryListener {
        /**
         * 请求开始
         *
         * @param index {@link MultiTask#MultiTask(List)}
         */
        public void onStart(int index);

        /**
         * 请求结束
         *
         * @param index
         */
        public void onStop(int index);

        public void onError(TaskError error);

        public void onUpdate(int index);

    }

    private ExecutorService excutor = Executors.newCachedThreadPool();

    /**
     * 初始化参数，不变。容器内部元素不变。
     */
    private List<Param> params = new ArrayList<>();

    private Object dataLock = 1;

    private final List<Result> results;

    /**
     * 当前操作参数集，参数替换将记录再此。
     */
    private List<Param> currentParams;

    /**
     * 由参数生成的querys。
     */
    private List<MultiTaskQuery<Param, Result>> querys;
    /**
     * 任务监听。
     */
    private OnMutiTaskQueryListener onMutiTaskQueryListener;

    public MultiTask(List<Param> params) {
        if (params == null || params.size() == 0) {
            throw new IllegalArgumentException("querys 必须不为空");
        }
        if (this.params != null) {
            this.params.clear();
        }
        this.params = params;
        this.currentParams = new ArrayList<Param>();
        this.currentParams.addAll(params);
        results = new Vector<Result>(currentParams.size());
        initResult();

        querys = new ArrayList<MultiTaskQuery<Param, Result>>();
        createQueryList(currentParams);
    }

    public List<Result> getResults() {
        synchronized (dataLock) {
            return results;
        }
    }

    protected void setResult(final int index, Result result) {
        synchronized (dataLock) {
            results.set(index, result);

            if (onMutiTaskQueryListener != null) {
                onMutiTaskQueryListener.onUpdate(index);

            }

        }
    }

    protected void queryStoped(final int index) {
        synchronized (dataLock) {


                    if (onMutiTaskQueryListener != null) {
                        onMutiTaskQueryListener.onStop(index);
                    }

        }
    }

    private void initResult() {
        results.clear();
        for (int i = 0; i < getQueryCount(); i++) {
            results.add(null);
        }
    }

    /**
     * 当前参数集，如有替换请求将反应到该集合。
     *
     * @return
     */
    public List<Param> getCurrentParams() {
        return currentParams;
    }

    /**
     * 初始参数集。替换后集合请见 {@link #getCurrentParams()}
     *
     * @return
     */
    public List<Param> getParams() {
        return params;
    }

    public List<MultiTaskQuery<Param, Result>> getQuerys() {
        return querys;
    }

    private void createQueryList(List<Param> params) {
        querys.clear();
        for (Param p : params) {
            querys.add(createQuery(p));
        }
    }

    /**
     * 异步执行。
     */
    public void start() {
        boolean block = false;
        start(block);
    }

    /**
     * @param block false 异步执行。true 阻塞线程。
     */
    public void start(boolean block) {
        if (isQuery()) {
            // querying don't load again.
            return;
        }
        if (!block) {
            query(null);
        } else {
            int count = getQueryCount();
            CountDownLatch downLatch = new CountDownLatch(count);
            query(downLatch);
            try {
                downLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重置，重置后可重新 {@link #start()}
     */
    public void reset() {
        cancel();
        // TODO 重新请求，是使用更好后的参数，还是更改之前的参数？
        createQueryList(currentParams);
        initResult();
    }

    public void cancel() {
        for (MultiTaskQuery<Param, Result> q : querys) {
            if (!q.isCancel()) {
                q.cancel();
            }
        }
    }

    private void query(CountDownLatch downLatch) {
        for (int i = 0; i < getQueryCount(); i++) {
            final MultiTaskQuery<?, ?> q = querys.get(i);
            q.setLatch(downLatch);
            // q.start(scheExcutor, i, onMutiTaskQueryListener, firstQueryTime,
            // queryIntervalTime);
            q.start(excutor, i, onMutiTaskQueryListener);
        }
    }

    /**
     * 替换query
     *
     * @param index
     * @param param
     * @return
     */
    public boolean replaceQuery(int index, Param param) {

        synchronized (dataLock) {
            results.set(index, null);
        }

        getQuerys().get(index).cancel();
        currentParams.set(index, param);
        MultiTaskQuery<Param, Result> query = createQuery(param);
        querys.set(index, query);
        query.start(excutor, index, onMutiTaskQueryListener);

        return true;
    }

    public void setOnMutiTaskQueryListener(OnMutiTaskQueryListener listener) {
        this.onMutiTaskQueryListener = listener;
        for (MultiTaskQuery<Param, Result> q : querys) {
            q.setOnMutiTaskQueryListener(listener);
        }
    }

    public boolean isQuery() {
        for (MultiTaskQuery<Param, Result> q : querys) {
            if (q.getState() == MultiTaskQuery.QUERY_ING) {
                return true;
            }
        }
        return false;
    }

    /**
     * 子进程数量，与Param 相关。
     *
     * @return
     */
    public int getQueryCount() {
        return querys == null ? 0 : querys.size();
    }

    protected abstract MultiTaskQuery<Param, Result> createQuery(Param param);

}
