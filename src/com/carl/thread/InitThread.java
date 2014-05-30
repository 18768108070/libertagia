package com.carl.thread;

import java.io.IOException;

import com.carl.controller.MainController;
import com.carl.http.Request;
import com.carl.message.ThreadMessage;
import com.carl.pojo.UserInfo;

public class InitThread extends RequestThread {

	public InitThread(MainController controller, UserInfo userInfo) {
		super(controller, userInfo);
	}

	public InitThread(MainController controller, UserInfo userInfo, int delay,
			int tryTimes) {
		super(controller, userInfo, delay, tryTimes);
	}

	@Override
	public void threadTask() {
		try {
			showLogs(false, String.format(
					"<Thread-ID:%d> �˻�:%s ���ڳ�ʼ��....��ǰ���Դ���:%d....", this.getId(),
					userInfo.getUsername(), tryTimes));
			Request.getInitCookiesAndCaptcha(userInfo);
			userInfo.setStatus("��ʼ�����");
			controller.updateTable(userInfo);
			showLogs(false, String.format("<Thread-ID:%d> �˻�:%s\t��ʼ�����.....",
					this.getId(), userInfo.getUsername()));
		} catch (IOException e) {
			if (this.tryTimes <= ThreadMessage.MAX_TRY_TIME) {
				showLogs(true, "�û���:" + userInfo.getUsername()
						+ "\t��ʼ��״̬��������,��������..������2�������...");
				new InitThread(controller, userInfo, 2000, tryTimes + 1).start();
			} else {
				showLogs(true, "�û���:" + userInfo.getUsername()
						+ "\t��ʼ��״̬��������,��������..���������Գ���"+ThreadMessage.MAX_TRY_TIME+"��,��ֹͣ���β���....");
			}
		}
	}

}
