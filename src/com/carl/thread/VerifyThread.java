package com.carl.thread;

import java.io.IOException;

import com.carl.controller.MainController;
import com.carl.http.ParseHtml;
import com.carl.http.Request;
import com.carl.pojo.UserInfo;

public class VerifyThread extends RequestThread {

	public VerifyThread(MainController controller, UserInfo userInfo) {
		super(controller, userInfo);
	}

	@Override
	public void threadTask() {
		try {
			String result = Request.getURLResult(userInfo, Request.index);
//			System.out.println(result);
			if(ParseHtml.verifyLoginStatus(result, "Welcome")){
				showLogs(false, String.format("<Thread-ID:%d> �˻�:%s\t��¼״̬��Ч.",this.getId(), userInfo.getUsername()));
				updateSatus("�ѵ�¼.");
			}else {
				showLogs(true, String.format("<Thread-ID:%d> �˻�:%s\t��¼״̬��ʧЧ,��Ҫ���µ�¼.", this.getId() ,userInfo.getUsername()));
				updateSatus("δ��¼.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
