package com.carl.message;

public class UserMessage {
	public static class UserProgress {
		public static final int NO_LOGIN = 0; // δ��¼
		public static final int IS_LOGIN = 1;// �ѵ�¼
		public static final int IN_TASK = 2;// ������
		public static final int DONE_TASK = 3;// �������
	}

	public static class UserStatus {
		public static final String NO_LOGIN = "δ��¼";
		public static final String IS_LOGIN = "�ѵ�¼";
		public static final String IN_INIT = "��ʼ����";
		public static final String DONE_INIT = "��ʼ�����";
		public static final String IN_VERIFY = "��֤��";
		public static final String FAIL_VARIFY = "��֤ʧ��";
		public static final String IN_INIT_TASK = "��ʼ��������";
		public static final String FAIL_INIT_TASK = "��ʼ������ʧ��";
		public static final String DONE_INIT_TASK = "��ʼ���������";
		public static final String IN_TASK = "���������";
		public static final String DENO_TASK = "�������";
		public static final String FAIL_TASK = "����ʧ��";
	}
}
