package com.carl.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.carl.message.LogLevel;
import com.carl.message.UserMessage;
import com.carl.pojo.UserInfo;
import com.carl.thread.InitTaskThread;
import com.carl.thread.InitThread;
import com.carl.thread.LoginThread;
import com.carl.thread.RequestThread;
import com.carl.thread.VerifyThread;
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
	 * ���ļ������˻���Ϣ ���ڳ�ʼ���˻���Ϣ
	 */
	public void loadAccountFile(String path) {
		this.showInfoLogs("�յ������˻��ļ���Ϣ.����ȷ���ļ�....");
		File accountFile = new File(path);
		if (!accountFile.exists()) {
			this.showMessage("�ļ�������,������ѡ��.");
			return;
		}
		try {
			this.showInfoLogs("�ļ���ȷ��,���ڶ�ȡ����....");
			List<String> context = FileUtils.readLines(accountFile);
			this.parseAccountFile(context);
		} catch (IOException e) {
			this.showMessage("�������ڲ�����,����ϵ����!!!");
			e.printStackTrace();
		}
	}

	/*
	 * �����˻��ļ�
	 */
	private void parseAccountFile(List<String> context) {
		this.showInfoLogs("�ļ��Ѷ�ȡ���,���ڽ�������....");
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
			this.updateTable();
			this.showInfoLogs(String.format("�������: ���� %d , �ɹ� %d , ʧ�� %d",
					count, success, fail));
		}
	}

	/*
	 * ��Ӳ�̷����л��õ�UserInfo����
	 */
	@SuppressWarnings("unchecked")
	public void loadSerializableFile(String path) {
		showInfoLogs("��ʼ��ȡ״̬�ļ�....<PATH>:" + path);
		File load = new File(path);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(load));
			infos = (List<UserInfo>) ois.readObject();
			ois.close();
			for (UserInfo u : infos) {
				System.out.println(u);
			}
			this.updateTable();
			showInfoLogs("״̬�ļ���ȡ���,��ʼУ���¼״̬....");
			verifyAllAccount();
		} catch (FileNotFoundException e) {
			showMessage("�ļ������ڻ򲻿ɶ�,������.");
			e.printStackTrace();
		} catch (IOException e) {
			showMessage("�������ڲ�����,����ϵ����!!!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			showMessage("�������ڲ�����,����ϵ����!!!");
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					showMessage("�������ڲ�����,����ϵ����!!!");
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * ��Ӳ��д��UserInfo�������л��ļ�
	 */
	public void saveSerializableFile(String path) {
		File save = new File(path);
		ObjectOutputStream oos = null;
		showInfoLogs("д��״̬�ļ�.... <PATH>:" + path);
		try {
			oos = new ObjectOutputStream(new FileOutputStream(save));
			oos.writeObject(infos);
			oos.flush();
			showInfoLogs("״̬�ļ��ѱ���...");
		} catch (FileNotFoundException e) {
			showMessage("�ļ������ڻ򲻿ɶ�,������.");
			e.printStackTrace();
		} catch (IOException e) {
			showMessage("�������ڲ�����,����ϵ����!!!");
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					showMessage("�������ڲ�����,����ϵ����!!!");
					e.printStackTrace();
				}
			}
		}
		infos = null;
	}

	/*
	 * �յ��û��������֤��
	 */
	public void Login(UserInfo userInfo, String captcha) {
		userInfo.setInprogress(1);
		RequestThread thread = new LoginThread(this, userInfo, captcha);
		thread.start();
	}

	/*
	 * �����¸��˻�
	 */
	public void nextAccount() {
		for (UserInfo u : infos) {
			if (u.getInprogress() == UserMessage.UserProgress.NO_LOGIN) {
				window.setCaptchaAndAccount(u);
				return;
			}
		}
		this.showMessage("���˻���Ҫ��¼.");
	}

	/*
	 * У�������˻�
	 */
	public void verifyAllAccount() {
		for (UserInfo u : infos) {
			new VerifyThread(this, u).start();
		}
	}

	/*
	 * �����˻���ʼ����
	 */
	public void startTask() {
		for (UserInfo userInfo : infos) {
			new InitTaskThread(this, userInfo).start();
		}
	}
	/*
	 * ��ʼ�������˻�����
	 */
	public void initAllAccount() {
		for (UserInfo u : infos) {
			new InitThread(this, u).start();
		}
	}

	/*
	 * �ص�ҳ��,��ʾ������
	 */
	public void showMessage(String msg) {
		this.showErrorLogs(msg);
		this.window.showMessage(msg);
	}

	/*
	 * �ص�ҳ��,��ʾInfo
	 */
	public void showInfoLogs(String log) {
		this.window.showLogs(LogLevel.LOG_INFO + log + lineSeparator);
	}

	/*
	 * �ص�ҳ��,��ʾError
	 */
	public void showErrorLogs(String log) {
		this.window.showLogs(LogLevel.LOG_ERROR + log + lineSeparator);
	}

	/*
	 * ���±���е����û�����
	 */
	public void updateTable(UserInfo userInfo) {
		window.showAccountInTable(userInfo);
	}

	/*
	 * ���±���������û�����
	 */
	public void updateTable() {
		window.showAllAccountInTable(infos);
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
