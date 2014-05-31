package com.carl.thread;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.carl.controller.MainController;
import com.carl.http.ParseHtml;
import com.carl.http.Request;
import com.carl.pojo.UserInfo;


/*********����ʼ***********
1.POST	http://tasks.libertagia.com/index.php	����:action=verifyStatus		���:�õ�JOSN���,��������һ������token,����ɵ�����token,��������ʾ
2.POST	http://tasks.libertagia.com/			����:action=view	code=��һ������token	���:ȡ���������ҳ�� captcha
3.POST	http://tasks.libertagia.com/index.php	����:result=������&action=finishTask	���:true or false
*********�������(����ݹ�)***********/
public class RunTaskThread extends RequestThread {
	private int taskNO ;
	public RunTaskThread(MainController controller, UserInfo userInfo) {
		super(controller, userInfo);
	}
	
	public RunTaskThread(MainController controller, UserInfo userInfo,
			int delay, int tryTimes) {
		super(controller, userInfo, delay, tryTimes);
	}

	public RunTaskThread(MainController controller, UserInfo userInfo,
			int delay, int tryTimes, int taskNO) {
		super(controller, userInfo, delay, tryTimes);
		this.taskNO = taskNO;
	}

	@Override
	public void threadTask() {
		try {
			// 1.POST http://tasks.libertagia.com/index.php ����:action=verifyStatus
			// ���:�õ�JOSN���,��������һ������token,����ɵ�����token,��������ʾ
			Map<String, String> data = new HashMap<String, String>();
			data.put("action", "verifyStatus");
			String result = Request.postURLResult(userInfo, Request.task_run_index, data, null, false);
			boolean done = ParseHtml.isDoneTask(result);
			if(done){
				//TODO���������
				System.out.println("���!!!!!!!!!!!!!!!!!!");
				System.out.println("���!!!!!!!!!!!!!!!!!!");
				System.out.println("���!!!!!!!!!!!!!!!!!!");
				System.out.println("���!!!!!!!!!!!!!!!!!!");
				System.out.println("���!!!!!!!!!!!!!!!!!!");
				return;
			}
			taskNO = ParseHtml.getDoneTaskCount(result);
			String token = ParseHtml.getNextTask(result);
			
			// 2.POST http://tasks.libertagia.com/ ����:action=view code=��һ������token
			// ���:ȡ���������ҳ�� captcha
			data = new HashMap<String, String>();
			data.put("action", "view");
			data.put("code", token);
			result = Request.postURLResult(userInfo, Request.task_run, data, null, false);
			//System.out.println(result);
			int r = ParseHtml.getTaskCaptcha(result);
			// 3.POST http://tasks.libertagia.com/index.php
			// ����:result=������&action=finishTask ���:true or false
			data = new HashMap<String, String>();
			data.put("result", r+"");
			data.put("action", "finishTask");
			result = Request.postURLResult(userInfo, Request.task_run_index, data, null, false);
			new RunTaskThread(controller, userInfo).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
