package com.carl.thread;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.carl.controller.MainController;
import com.carl.http.ParseHtml;
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
	/*********����׼��***********
	1.GET	http://libertagia.com/office/tasks/	���:ȡ��secret����ֵ
	2.POST	http://tasks.libertagia.com/		����:secret	���:����cookie
	3.GET	http://tasks.libertagia.com/		���:����cookie
	***************&***********/
	/*********����ʼ***********
	1.POST	http://tasks.libertagia.com/index.php	����:action=verifyStatus		���:�õ�JOSN���,��������һ������token,����ɵ�����token,��������ʾ
	2.POST	http://tasks.libertagia.com/		����:action=view	code=��һ������token	���:ȡ���������ҳ�� captcha
	3.POST	http://tasks.libertagia.com/index.php	����:result=������&action=finishTask	���:true or false
	*********�������(����ݹ�)***********/

	/*
	 * ȡ������POST�������
	 */
	private void getTaskToken() throws IOException{
		//	1.GET	http://libertagia.com/office/tasks/	���:ȡ��secret����ֵ
		String result = Request.getURLResult(userInfo, Request.task);
		String token = ParseHtml.getSecretParam(result);
		//	2.POST	http://tasks.libertagia.com/		����:secret	���:����cookie
		Map<String, String> data = new HashMap<String, String>();
		data.put("secret", token);
		result = Request.postURLResult(userInfo, Request.task_run, data, null,true);
		//	3.GET	http://tasks.libertagia.com/		���:����cookie
		Request.getURLResult(userInfo, Request.task_run);
	}
	
	/*
	 * ��һ������
	 * �ݹ����
	 */
	private void nextWork() {
		//1.POST	http://tasks.libertagia.com/index.php	����:action=verifyStatus		���:�õ�JOSN���,��������һ������token,����ɵ�����token,��������ʾ
		
		//2.POST	http://tasks.libertagia.com/		����:action=view	code=��һ������token	���:ȡ���������ҳ�� captcha
		//3.POST	http://tasks.libertagia.com/index.php	����:result=������&action=finishTask	���:true or false
		
	}

	public void setController(MainController controller) {
		this.controller = controller;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
