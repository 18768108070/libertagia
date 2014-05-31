package com.carl.thread;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.carl.controller.MainController;
import com.carl.http.ParseHtml;
import com.carl.http.Request;
import com.carl.message.ThreadMessage;
import com.carl.message.UserMessage;
import com.carl.pojo.UserInfo;

/*********����׼��***********
1.GET	http://libertagia.com/office/tasks/	���:ȡ��secret����ֵ
2.POST	http://tasks.libertagia.com/		����:secret	���:����cookie
3.GET	http://tasks.libertagia.com/		���:����cookie
***************************/
public class InitTaskThread extends RequestThread {

	public InitTaskThread(MainController controller, UserInfo userInfo) {
		super(controller, userInfo);
	}

	public InitTaskThread(MainController controller, UserInfo userInfo,
			int delay, int tryTimes) {
		super(controller, userInfo, delay, tryTimes);
	}

	@Override
	public void threadTask() {
		try {
			// 1.GET http://libertagia.com/office/tasks/ ���:ȡ��secret����ֵ
			updateUserInfo(UserMessage.UserStatus.IN_INIT_TASK,
					UserMessage.UserProgress.IN_TASK);
			String result = Request.getURLResult(userInfo, Request.task);
			String token = ParseHtml.getSecretParam(result);
			if (token == null) {
				updateUserInfo(UserMessage.UserStatus.FAIL_INIT_TASK,
						UserMessage.UserProgress.NO_LOGIN);
				showLogs(true, String.format(
						"<Thread-ID:%d> �˻�:%s\t��ʼ������ʧ��,�����˻���Ϣ.", this.getId(),
						userInfo.getUsername()));
				return;
			}
			
			// 2.POST http://tasks.libertagia.com/ ����:secret ���:����cookie
			Map<String, String> data = new HashMap<String, String>();
			data.put("secret", token);
			result = Request.postURLResult(userInfo, Request.task_run, data,
					null, true);
			
			// 3.GET http://tasks.libertagia.com/ ���:����cookie
			Request.getURLResult(userInfo, Request.task_run);
			showLogs(true, "<Thread-ID:%d> �˻�:" + userInfo.getUsername()
					+ "\t��ʼ������ɹ�...��ʼ��������....");
			updateUserInfo(UserMessage.UserStatus.IN_TASK,
					UserMessage.UserProgress.IN_TASK);
			new RunTaskThread(controller, userInfo).start();
			
		} catch (IOException e) {
			e.printStackTrace();
			if (tryTimes <= ThreadMessage.MAX_TRY_TIME) {
				showLogs(true, "<Thread-ID:%d> �˻�:" + userInfo.getUsername()
						+ "\t��ʼ������������,��������..������2�������...");
				new InitTaskThread(controller, userInfo, 2000, tryTimes + 1).start();
			} else {
				showLogs(true, "<Thread-ID:%d> �˻�:" + userInfo.getUsername()
						+ "\t��ʼ������������,��������..���������Գ���"
						+ ThreadMessage.MAX_TRY_TIME + "��,��ֹͣ���β���....");
			}
		}
	}
}
