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
	 * �ص�ҳ��,��ʾ��Ϣ
	 */
	private void showMessage(String msg) {

	}

	
	/*
	 * ��UserInfo�������л���Ӳ��
	 */
	public void saveFileForCookies(String path) {

	}

	public List<UserInfo> getInfos() {
		return infos;
	}

	public MainWindow getWindow() {
		return window;
	}
}
