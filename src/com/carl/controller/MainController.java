package com.carl.controller;

import java.util.List;

import com.carl.pojo.UserInfo;
import com.carl.window.MainWindow;

/**
 * ��������
 * 
 * @author carl.huang
 * 
 */
public class MainController {
	// �����˻�״̬��Ϣ
	private List<UserInfo> infos;
	// ����������
	private MainWindow window;

	/*
	 * ����UserInfo�����еı��ȡ���˻�
	 */
	public void getAccountByIndex(int index) {

	}

	/*
	 * ���ļ������˻���Ϣ ���ڳ�ʼ���˻���Ϣ
	 */
	public void loadAccounts(String path) {

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
	}

	/*
	 * ��UserInfo�������л���Ӳ��
	 */
	public void saveFileForCookies(String path) {

	}
	
	/*
	 * �ص�ҳ��,��ʾ��Ϣ
	 */
	private void showMessage(String msg) {

	}
	
	/*
	 * �ص�ҳ��,��ʾ��־
	 */
	private void showLogs(String log) {
		
	}
	public List<UserInfo> getInfos() {
		return infos;
	}

	public MainWindow getWindow() {
		return window;
	}
}
