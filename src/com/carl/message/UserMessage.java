package com.carl.message;

public class UserMessage {
	/**
	 * �����˻�||�ָ�״̬ -> δ��¼ <- initThread�߳�ʶ��
	 * �û��ֶ���¼��״̬ || ������ɺ�״̬ -> �ѵ�¼ <- initTaskThread�߳�ʶ��
	 * initTaskThread�߳�ִ����״̬ -> ������
	 * ������:	δ��¼->��ʼ�����->�ѵ�¼->������->�ѵ�¼
	 * @author Carl.Huang
	 *
	 */
	public static class UserProgress {
		// δ��¼	�����˻��ļ���״̬ || ״̬�ָ���֤��״̬
		//			initThread�߳�����ʶ��.(��δ��¼,���ʼ������.���ѵ�¼,���޸�״̬���ѵ�¼)
		public static final int NO_LOGIN = 0;
		// �ѵ�¼	�û��ֶ���¼��״̬ || ������ɺ�״̬
		//			initTaskThread�߳�ʶ��.(��δ�������,�޸�״̬�������в������Զ������߳�.�������,���޸�״̬���ѵ�¼)
		//TODO		���ȫ���Զ������߳�ע��.(ȫ���Զ������߳�,ʶ���ѵ�¼״̬�˻�,�Զ�����initTaskThread�߳�)			
		public static final int IS_LOGIN = 1;
		// ������	initTaskThread�߳�ִ����״̬.
		//			���߳�ʶ��
		public static final int IN_TASK = 2;	
		// ��ʼ�����	���û��ֶ������һ���˻���ťʱʶ��.
		public static final int DONE_INIT = 3;
		public static final int IN_INIT = 4;
		public static final int IN_LOGIN = 5;
		
	}

	public static class UserStatus {
		public static final String NO_LOGIN = "δ��¼";
		public static final String IN_LOGIN = "��¼��";
		public static final String IS_LOGIN = "�ѵ�¼";
		public static final String FAIL_LOGIN = "��¼ʧ��";
		public static final String IN_INIT = "��ʼ����";
		public static final String DONE_INIT = "��ʼ�����";
		public static final String FAIL_INIT = "��ʼ��ʧ��";
		public static final String IN_VERIFY = "��֤��";
		public static final String FAIL_VARIFY = "��֤ʧ��";
		public static final String IN_INIT_TASK = "��ʼ��������";
		public static final String FAIL_INIT_TASK = "��ʼ������ʧ��";
		public static final String DONE_INIT_TASK = "��ʼ���������";
		public static final String IN_TASK = "���������";
		public static final String DONE_TASK = "�������";
		public static final String FAIL_TASK = "����ʧ��";
	}
}
