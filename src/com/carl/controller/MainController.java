package com.carl.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.carl.message.LogLevel;
import com.carl.pojo.UserInfo;
import com.carl.thread.RequestThread;
import com.carl.window.MainWindow;

/**
 * ��������
 * 
 * @author carl.huang
 * 
 */
public class MainController {
	// �����˻�״̬��Ϣ
	private List<UserInfo> infos = new ArrayList<UserInfo>();
	// ����������
	private MainWindow window;
	// ϵͳ���з�
	private String lineSeparator = System.getProperty("line.separator", "\n");

	/*
	 * ����UserInfo�����еı��ȡ���˻�
	 */
	public void getAccountByIndex(int index) {

	}

	/*
	 * ���ݱ��ȡ����֤��ͼƬ
	 */
	public void getCaptchaByIndex(int index) {
		
	}

	/*
	 * ���ļ������˻���Ϣ ���ڳ�ʼ���˻���Ϣ
	 */
	public void loadAccountFile(String path) {
		this.showLogs(LogLevel.LOG_INFO, "�յ������˻��ļ���Ϣ.����ȷ���ļ�....");
		File accountFile = new File(path);
		if (!accountFile.exists()) {
			this.showMessage("�ļ�������,������ѡ��.");
			return;
		}
		try {
			this.showLogs(LogLevel.LOG_INFO, "�ļ���ȷ��,���ڶ�ȡ����....");
			List<String> context = FileUtils.readLines(accountFile);
			this.parseAccountFile(context);
		} catch (IOException e) {
			this.showMessage("�ļ���ȡʧ��,������.");
			e.printStackTrace();
		}
	}

	private void parseAccountFile(List<String> context) {
		this.showLogs(LogLevel.LOG_INFO, "�ļ��Ѷ�ȡ���,���ڽ�������....");
		int success = 0, fail = 0, count = context.size();
		for (String account : context) {
			String[] tmp = account.split(" ");
			if (tmp.length != 2) {
				fail++;
			} else {
				infos.add(new UserInfo(tmp[0], tmp[1]));
				success++;
			}
		}
		if (fail != 0) {
			this.window.clearTable();
			this.showMessage(String.format("�������: ���� %d , �ɹ� %d , ʧ�� %d",
					count, success, fail));
		} else {
			this.window.showAllAccountInTable(infos);
			this.showLogs(LogLevel.LOG_INFO, String.format(
					"�������: ���� %d , �ɹ� %d , ʧ�� %d", count, success, fail));
		}
	}

	/*
	 * ��Ӳ�̷����л��õ�UserInfo����
	 */
	public void loadFileForCookies(String path) {

	}

	/*
	 * �յ��û��������֤��
	 */
	public void pushCaotcha(int index, String captcha) {
		// TODO �����߳� ���е�½cookie��ȡ
//		UserInfo u = infos.get(index);
		
	}
	
	public void nextAccount() {
		for (UserInfo u : infos) {
			if("δ����".equals(u.getStatus())){
				window.setCaptchaAndAccount(u);
				return;
			}
		}
		this.showLogs(LogLevel.LOG_INFO, "���˻���Ҫ��ʼ��.");
	}
	
	/*
	 * ��ʼ�������˻�����
	 */
	public void initAllAccount() {
		for (UserInfo u : infos) {
			new RequestThread(this,u).start();
		}
	}

	/*
	 * ��UserInfo�������л���Ӳ��
	 */
	public void saveFileForCookies(String path) {

	}

	/*
	 * �ص�ҳ��,��ʾ��Ϣ
	 */
	public void showMessage(String msg) {
		this.showLogs(LogLevel.LOG_ERROR, msg);
		this.window.showMessage(msg);
	}

	/*
	 * �ص�ҳ��,��ʾ��־
	 */
	public void showLogs(String level, String log) {
		this.window.showLogs(level + log + lineSeparator);
	}

	/*
	 * ���±������
	 */
	public void updateTable(UserInfo userInfo) {
		
	}
	public List<UserInfo> getInfos() {
		return infos;
	}

	public MainWindow getWindow() {
		return window;
	}

	public void setWindow(MainWindow window) {
		this.window = window;
	}
}
