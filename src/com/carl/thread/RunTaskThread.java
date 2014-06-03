package com.carl.thread;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.carl.controller.MainController;
import com.carl.http.ParseHtml;
import com.carl.http.Request;
import com.carl.message.UserMessage;
import com.carl.pojo.UserInfo;


/*********����ʼ***********
1.POST	http://tasks.libertagia.com/index.php	����:action=verifyStatus		���:�õ�JOSN���,��������һ������token,����ɵ�����token,��������ʾ
2.POST	http://tasks.libertagia.com/			����:action=view	code=��һ������token	���:ȡ���������ҳ�� captcha
3.POST	http://tasks.libertagia.com/index.php	����:result=������&action=finishTask	���:true or false
*********�������(����ݹ�)***********/
public class RunTaskThread extends RequestThread {
	public RunTaskThread(MainController controller, UserInfo userInfo) {
		super(controller, userInfo);
	}
	
	public RunTaskThread(MainController controller, UserInfo userInfo,
			int delay, int tryTimes) {
		super(controller, userInfo, delay, tryTimes);
	}


	@Override
	public void threadTask() {
		try {
			// 1.POST http://tasks.libertagia.com/index.php ����:action=verifyStatus
			// ���:�õ�JOSN���,��������һ������token,����ɵ�����token,��������ʾ
			showInfo("��ʼִ������....");
			Map<String, String> data = new HashMap<String, String>();
			data.put("action", "verifyStatus");
			String result = Request.postURLResult(userInfo, Request.task_run_index, data, null, false);
			if(!ParseHtml.verifyLoginStatus(result, "finished")){
				showError("����ʧ��...��ʧ��¼״̬...");
				//����ʧ��,��״̬δ��¼
				updateUserInfo(UserMessage.UserStatus.FAIL_TASK,
						UserMessage.UserProgress.NO_LOGIN);
				return;
			}
			
			if(ParseHtml.isDoneTask(result)){
				//���������
				updateUserInfo(UserMessage.UserStatus.DONE_TASK,
						UserMessage.UserProgress.IS_LOGIN);
				showInfo("���������...");
				return;
			}
			int taskNO = ParseHtml.getDoneTaskCount(result);
			String token = ParseHtml.getNextTask(result);
			showInfo("��ǰִ�е�" + taskNO + "������..");
			
			
			// 2.POST http://tasks.libertagia.com/ ����:action=view code=��һ������token
			// ���:ȡ���������ҳ�� captcha
			data = new HashMap<String, String>();
			data.put("action", "view");
			data.put("code", token);
			result = Request.postURLResult(userInfo, Request.task_run, data, null, false);
			int r = ParseHtml.getTaskCaptcha(result);
			if(r==-1){
				showError("����ʧ��...��ʧ��¼״̬...");
				//����ʧ��,��״̬δ��¼
				updateUserInfo(UserMessage.UserStatus.FAIL_TASK,
						UserMessage.UserProgress.NO_LOGIN);
				return;
			}
				
			// 3.POST http://tasks.libertagia.com/index.php
			// ����:result=������&action=finishTask ���:true or false
			data = new HashMap<String, String>();
			data.put("result", r+"");
			data.put("action", "finishTask");
			result = Request.postURLResult(userInfo, Request.task_run_index, data, null, false);
			
			if(ParseHtml.verifyLoginStatus(result, "true") && ParseHtml.verifyLoginStatus(result, "false")){
				showError("����ʧ��...��ʧ��¼״̬...");
				//����ʧ��,��״̬δ��¼
				updateUserInfo(UserMessage.UserStatus.FAIL_TASK,
						UserMessage.UserProgress.NO_LOGIN);
				return;
			}
			new RunTaskThread(controller, userInfo).start();
		} catch (IOException e) {
			showError("����ʱʧ��...��ʧ��¼״̬...");
			//����ʧ��,��״̬δ��¼
			updateUserInfo(UserMessage.UserStatus.FAIL_TASK,
					UserMessage.UserProgress.NO_LOGIN);
			e.printStackTrace();
		}
		
	}

}
