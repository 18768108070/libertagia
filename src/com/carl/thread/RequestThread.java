package com.carl.thread;

import java.io.IOException;

import com.carl.controller.MainController;
import com.carl.http.Request;
import com.carl.message.LogLevel;
import com.carl.pojo.UserInfo;

public class RequestThread extends Thread {
	private MainController controller;
	private UserInfo userInfo;
	private boolean isInit = false;

	public RequestThread(MainController controller, UserInfo userInfo) {
		super();
		this.controller = controller;
		this.userInfo = userInfo;
	}

	public RequestThread(MainController controller, UserInfo userInfo,
			boolean isInit) {
		super();
		this.controller = controller;
		this.userInfo = userInfo;
		this.isInit = isInit;
	}

	public void run() {
		if (isInit) {
			try {
				Request.getInitCookiesAndCaptcha(userInfo);
				controller.updateTable(userInfo);
				controller.showLogs(LogLevel.LOG_INFO,
						"�û���:" + userInfo.getUsername() + "\t��ʼ�����.");
			} catch (IOException e) {
				controller.showLogs(LogLevel.LOG_ERROR,
						"�û���:" + userInfo.getUsername() + "\t��ʼ��״̬����,��������.");
			}
		}else {
			
		}
		
	}
	
	private void startWork() {
		
	}

	public void setController(MainController controller) {
		this.controller = controller;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
