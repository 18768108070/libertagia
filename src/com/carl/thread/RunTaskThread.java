package com.carl.thread;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.carl.controller.MainController;
import com.carl.http.ParseHtml;
import com.carl.http.Request;
import com.carl.pojo.UserInfo;

/*********����׼��***********
1.GET	http://libertagia.com/office/tasks/	���:ȡ��secret����ֵ
2.POST	http://tasks.libertagia.com/		����:secret	���:����cookie
3.GET	http://tasks.libertagia.com/		���:����cookie
***************&***********/
/*********����ʼ***********
1.POST	http://tasks.libertagia.com/index.php	����:action=verifyStatus		���:�õ�JOSN���,��������һ������token,����ɵ�����token,��������ʾ
2.POST	http://tasks.libertagia.com/			����:action=view	code=��һ������token	���:ȡ���������ҳ�� captcha
3.POST	http://tasks.libertagia.com/index.php	����:result=������&action=finishTask	���:true or false
*********�������(����ݹ�)***********/

public class RunTaskThread extends RequestThread {

	public RunTaskThread(MainController controller, UserInfo userInfo) {
		super(controller, userInfo);
	}

	@Override
	public void threadTask() {
		try {
			getTaskToken();
			nextWork();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
}
