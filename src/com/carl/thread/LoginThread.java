package com.carl.thread;

import java.io.IOException;

import com.carl.controller.MainController;
import com.carl.http.ParseHtml;
import com.carl.http.Request;
import com.carl.message.ThreadMessage;
import com.carl.pojo.UserInfo;

public class LoginThread extends RequestThread {
	private String code;

	public LoginThread(MainController controller, UserInfo userInfo, int delay,
			int tryTimes, String code) {
		super(controller, userInfo, delay, tryTimes);
		this.code = code;
	}

	public LoginThread(MainController controller, UserInfo userInfo) {
		super(controller, userInfo);
	}

	public LoginThread(MainController controller, UserInfo userInfo, String code) {
		super(controller, userInfo);
		this.code = code;
	}

	@Override
	public void threadTask() {
		try {
			showLogs(false, String.format(
					"<Thread-ID:%d> �˻�:%s ���ڵ�¼...��ǰ���Դ���:%d....", this.getId(),
					userInfo.getUsername(), tryTimes));
			Request.getLoginCookies(userInfo, code);
			// TODO ������ܻᷢ������,������������.
			// new VerifyThread(controller, userInfo).start();
			String result = Request.getURLResult(userInfo, Request.index);
			if (ParseHtml.verifyLoginStatus(result, "Welcome")) {
				showLogs(
						false,
						String.format("<Thread-ID:%d> �˻�:%s\t��¼�ɹ�.",
								this.getId(), userInfo.getUsername()));
				updateSatus("�ѵ�¼.");
			} else {
				showLogs(true, String.format(
						"<Thread-ID:%d> �˻�:%s\t��¼ʧ��,�����˻���Ϣ.", this.getId(),
						userInfo.getUsername()));
				updateSatus("δ��¼.");
			}
		} catch (IOException e) {
			if (tryTimes <= ThreadMessage.MAX_TRY_TIME) {
				showLogs(true, "�û���:" + userInfo.getUsername()
						+ "\t��¼����������,��������..������2�������...");
				new LoginThread(controller, userInfo, 2000, tryTimes + 1, code).start();
			} else {
				showLogs(true, "�û���:" + userInfo.getUsername()
						+ "\t��¼����������,��������..���������Գ���"
						+ ThreadMessage.MAX_TRY_TIME + "��,��ֹͣ���β���....");
			}

			e.printStackTrace();
		}
	}

}
