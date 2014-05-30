package com.carl.thread;

import com.carl.controller.MainController;
import com.carl.message.LogLevel;
import com.carl.pojo.UserInfo;

public abstract class RequestThread extends Thread {
	protected MainController controller;
	protected UserInfo userInfo;
	public RequestThread(MainController controller, UserInfo userInfo) {
		super();
		this.controller = controller;
		this.userInfo = userInfo;
	}

	/*
	 * �߳�����
	 */
	public abstract void threadTask();
	
	/*
	 * ���������Ϣ
	 */
	public  void showLogs(boolean isError,String msg){
		if (isError) {
			controller.showErrorLogs(msg);
		}else {
			controller.showInfoLogs(msg);
		}
	}
	
	public void updateSatus(String status) {
		userInfo.setStatus(status);
		controller.updateTable(userInfo);
	}
	
	public void run() {
		threadTask();
	}

	public void setController(MainController controller) {
		this.controller = controller;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
