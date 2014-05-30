package com.carl.thread;

import com.carl.controller.MainController;
import com.carl.pojo.UserInfo;

public abstract class RequestThread extends Thread {
	protected MainController controller;
	protected UserInfo userInfo;
	// �����ӳ�ʱ��
	protected int delay;
	// ���Դ���
	protected int tryTimes = 1;

	public RequestThread(MainController controller, UserInfo userInfo,
			int delay, int tryTimes) {
		super();
		this.controller = controller;
		this.userInfo = userInfo;
		this.delay = delay;
		this.tryTimes = tryTimes;
	}

	public RequestThread(MainController controller, UserInfo userInfo) {
		this(controller, userInfo, 0, 0);
	}

	/*
	 * �߳�����
	 */
	public abstract void threadTask();

	/*
	 * ���������Ϣ
	 */
	public void showLogs(boolean isError, String msg) {

		if (isError) {
			controller.showErrorLogs(msg);
		} else {
			controller.showInfoLogs(msg);
		}
	}

	@Override
	public synchronized void start() {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.start();
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
