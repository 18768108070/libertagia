package com.carl.thread;

import java.io.IOException;

import com.carl.controller.MainController;
import com.carl.http.ParseHtml;
import com.carl.http.Request;
import com.carl.message.ThreadMessage;
import com.carl.message.UserMessage;
import com.carl.pojo.UserInfo;

public class VerifyThread extends RequestThread {

	public VerifyThread(MainController controller, UserInfo userInfo) {
		super(controller, userInfo);
	}

	public VerifyThread(MainController controller, UserInfo userInfo,
			int delay, int tryTimes) {
		super(controller, userInfo, delay, tryTimes);
	}

	@Override
	public void threadTask() {
		try {
			showLogs(false, String.format(
					"<Thread-ID:%d> �˻�:%s ����У��״̬....��ǰ���Դ���:%d...",
					this.getId(), userInfo.getUsername(), tryTimes));
			String result = Request.getURLResult(userInfo, Request.index);
			if (ParseHtml.verifyLoginStatus(result, "Welcome")) {
				showLogs(
						false,
						String.format("<Thread-ID:%d> �˻�:%s\t��¼״̬��Ч.",
								this.getId(), userInfo.getUsername()));
				updateUserInfo(UserMessage.UserStatus.IS_LOGIN, UserMessage.UserProgress.IS_LOGIN);
			} else {
				showLogs(true, String.format(
						"<Thread-ID:%d> �˻�:%s\t��¼״̬��ʧЧ,��Ҫ���µ�¼.", this.getId(),
						userInfo.getUsername()));
				updateUserInfo(UserMessage.UserStatus.NO_LOGIN, UserMessage.UserProgress.NO_LOGIN);
			}
		} catch (IOException e) {
			if (this.tryTimes <= ThreadMessage.MAX_TRY_TIME) {
				showLogs(true, "�û���:" + userInfo.getUsername()
						+ "\t��¼״̬У������������,��������..������2�������...");
				new VerifyThread(controller, userInfo, 2000, tryTimes + 1).start();
			} else {
				showLogs(true, "�û���:" + userInfo.getUsername()
						+ "\t��¼״̬У������������,��������..���������Գ���"
						+ ThreadMessage.MAX_TRY_TIME + "��,��ֹͣ���β���....");
			}
			e.printStackTrace();
		}
	}

}
