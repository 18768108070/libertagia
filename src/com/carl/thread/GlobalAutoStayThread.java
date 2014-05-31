package com.carl.thread;

import com.carl.controller.MainController;
import com.carl.pojo.UserInfo;

public class GlobalAutoStayThread extends RequestThread {
	private boolean stopRequest;
	

	public GlobalAutoStayThread(MainController controller, UserInfo userInfo, boolean stopRequest) {
		super(controller, userInfo);
		this.stopRequest = stopRequest;
	}

	public GlobalAutoStayThread(MainController controller, UserInfo userInfo,
			int delay, int tryTimes) {
		super(controller, userInfo, delay, tryTimes);
	}

	@Override
	public void threadTask() {
		showInfo("ȫ����������߳�������.....");
		while (stopRequest) {
			int i = 0;
			while(i <= 20){
				try {
					showInfo("ȫ�����������,���´μ�⻹��"+(20-i)*5+"��.....");
					i++;
					Thread.sleep(1000 * 5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			controller.startTask();
			showInfo("ȫ���������ڽ��ж�ʱ���.....");
		}
		showInfo("ȫ����������߳��ѽ���.....");
	}

	public void setStopRequest(boolean stopRequest) {
		this.stopRequest = stopRequest;
	}

}