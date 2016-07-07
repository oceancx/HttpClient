package com.oceancx.task;

/**
 * 任务执行结果封装。 <br>
 * 
 *  <th>
 * <li>{@link #getData()}业务数据获取。</li>
 * <li>{@link #getError()}获取任务错误信息。</li></th>
 * 
 * @author dujun
 * 
 * @param <D>
 */
public class TaskResult<D> {

	private D data;
	private TaskError error;

	/**
	 * 
	 * @return task正确执行结果,{@link #isError()} 判断是正确执行。
	 */
	public D getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 */
	public void setData(D data) {
		this.data = data;
	}

	/**
	 * <code>true</code> 有错， {@link #getError()}
	 * 
	 * @return
	 */
	public boolean isError() {
		return error != null;
	}

	public void setError(TaskError error) {
		this.error = error;
	}

	/**
	 * 获取错误信息。<code>null</code>无错误。
	 * 
	 * @return
	 */
	public TaskError getError() {
		return error;
	}
}
