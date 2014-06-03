package com.carl.thread;

import java.io.IOException;

import com.carl.controller.MainController;
import com.carl.http.ParseHtml;
import com.carl.http.Request;
import com.carl.message.UserMessage;
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
			//�����ڵ�¼״̬
			updateUserInfo(UserMessage.UserStatus.IN_LOGIN, UserMessage.UserProgress.IN_LOGIN);
			showInfo("���ڵ�¼.....");
			Request.getLoginCookies(userInfo, code);
			String result = Request.getURLResult(userInfo, Request.index);
			if (ParseHtml.verifyLoginStatus(result)) {
				//��¼�ɹ�,��״̬�ѵ�¼
				updateUserInfo(UserMessage.UserStatus.IS_LOGIN,
						UserMessage.UserProgress.IS_LOGIN);
				showInfo("��¼�ɹ�........");
				//new InitTaskThread(controller, userInfo).start();
			} else {
				showError("��¼ʧ��...�����˻���Ϣ...");
				//��¼ʧ��,��״̬δ��¼
				updateUserInfo(UserMessage.UserStatus.FAIL_LOGIN,
						UserMessage.UserProgress.NO_LOGIN);
				return;
			}
		} catch (IOException e) {
			//��¼�쳣,��״̬δ��¼
			updateUserInfo(UserMessage.UserStatus.FAIL_LOGIN,
					UserMessage.UserProgress.NO_LOGIN);
			showError("��¼����������,��������....");
//			if (tryTimes <= ThreadMessage.MAX_TRY_TIME) {
//				showError("��¼����������,��������..������2�������...");
//				new LoginThread(controller, userInfo, 2000, tryTimes + 1, code)
//						.start();
//			} else {
//				showError("��¼����������,��������..���������Գ���"
//						+ ThreadMessage.MAX_TRY_TIME + "��,��ֹͣ���β���....");
//			}

			e.printStackTrace();
		}
	}

}
