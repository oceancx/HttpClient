package com.oceancx.task;

/**
 * 模型任务，执行错误返回错误封装。
 * 
 * 以code 区分。
 * 
 * @author dujun
 * 
 */
public class TaskError {

	public final static int WEAK = -1;
	public final static int NOR = 0;
	public final static int WARN = 1;
	public final static int STRONG = 2;

	/**
	 * 默认错误，
	 */
	public static final int CODE_DEFAULT = 1;
	/**
	 * 服务业务错误
	 */
	public static final int CODE_SERVICE = 2;
	/**
	 * 身份认证失败
	 */
	public static final int CODE_TOKEN_INVALID = 3;
	/**
	 * 网络异常
	 */
	public static final int CODE_NETWORK_ERROR = 4;

	private int code;

	/**
	 * 接口code。
	 */
	private int apiCode;
	private int level = NOR;
	private String msg;

	private boolean handled;

	public TaskError() {

	}

	/**
	 * default level is {@link #NOR}
	 * 
	 * @param msg
	 */
	public TaskError(String msg) {
		this(1, NOR, msg);
	}

	public TaskError(int code, String msg) {
		this(code, NOR, msg);
	}

	public TaskError(int code, int level, String msg) {
		this.code = code;
		this.level = level;
		this.msg = msg;
	}

	/**
	 * 接口错误码
	 * 
	 * @return
	 */
	public int getApiCode() {
		return apiCode;
	}

	public void setApiCode(int apiCode) {
		this.apiCode = apiCode;
	}

	/**
	 * 1 默认错误，显示Toast；2 业务错误 ；3 身份认证失败；4：无网络异常。
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMsg() {
		return msg == null ? "" : msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isHandled() {
		return handled;
	}

	public void setHandled(boolean handled) {
		this.handled = handled;
	}

}
