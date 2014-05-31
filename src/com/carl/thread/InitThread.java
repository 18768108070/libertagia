package com.carl.thread;

import java.io.IOException;

import com.carl.controller.MainController;
import com.carl.http.ParseHtml;
import com.carl.http.Request;
import com.carl.message.ThreadMessage;
import com.carl.message.UserMessage;
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
			//�ý����ʼ��״̬
			updateUserInfo(UserMessage.UserStatus.IN_INIT, UserMessage.UserProgress.IN_INIT);
			showInfo("����У���Ƿ��ѵ�¼...");
			String result = Request.getURLResult(userInfo, Request.index);
			if (ParseHtml.verifyLoginStatus(result, "Welcome")) {
				// �ж��ѵ�¼,��״̬�ѵ�¼.
				updateUserInfo(UserMessage.UserStatus.IS_LOGIN, UserMessage.UserProgress.IS_LOGIN);
				showInfo("�ѵ�¼.....��ʼ�Զ�����....");
				new InitTaskThread(controller, userInfo).start();
				return;
			}
			showInfo("δ��¼....");
			showInfo("���ڳ�ʼ��....��ǰ���Դ���:" + tryTimes + "....");
			Request.getInitCookiesAndCaptcha(userInfo);
			if (userInfo.getCaptcha()!=null) {
				//��ʼ�����,��״̬��ʼ�����.
				updateUserInfo(UserMessage.UserStatus.DONE_INIT, UserMessage.UserProgress.DONE_INIT);
				showInfo("��ʼ�����.....");
				return;
			}
			//��ʼ��ʧ��,��״̬δ��¼
			updateUserInfo(UserMessage.UserStatus.FAIL_INIT, UserMessage.UserProgress.NO_LOGIN);				
			showInfo("��ʼ��ʧ��.....");
		} catch (IOException e) {
			//��ʼ���쳣,��״̬δ��¼.
			updateUserInfo(UserMessage.UserStatus.FAIL_INIT, UserMessage.UserProgress.NO_LOGIN);
			if (this.tryTimes <= ThreadMessage.MAX_TRY_TIME) {
				showError("��ʼ��״̬��������,��������..������2�������...");
				new InitThread(controller, userInfo, 2000, tryTimes + 1).start();
			} else {
				showError("��ʼ��״̬��������,��������..���������Գ���"
						+ ThreadMessage.MAX_TRY_TIME + "��,��ֹͣ���β���....");
			}
		}
	}

}
