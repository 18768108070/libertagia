package com.carl.thread;

import java.io.IOException;

import com.carl.controller.MainController;
import com.carl.http.Request;
import com.carl.pojo.UserInfo;

public class InitThread extends RequestThread {

	public InitThread(MainController controller, UserInfo userInfo) {
		super(controller, userInfo);
	}

	@Override
	public void threadTask() {
		// TODO Auto-generated method stub
		try {
			Request.getInitCookiesAndCaptcha(userInfo);
			userInfo.setStatus("��ʼ�����");
			controller.updateTable(userInfo);
			showLogs(false,"�û���:" + userInfo.getUsername() + "\t��ʼ�����.");
		} catch (IOException e) {
			showLogs(true,"�û���:" + userInfo.getUsername() + "\t��ʼ��״̬����,��������.");
		}
	}

}
