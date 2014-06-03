package com.carl.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.carl.controller.MainController;
import com.carl.pojo.UserInfo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	// �������
	private JPanel contentPane;
	// �˻����
	private JTable table;
	// ��֤��ͼƬ��ǩ
	private JLabel captcha;
	// ��֤�������
	private JTextField captchaText;
	// ������
	private MainController controller;
	// ��־�����
	private JTextArea logTextArea;
	// ��ģ��
	private DefaultTableModel model;
	// �û�����ǩ
	private JLabel accountLabel;
	// �����л���ť
	private JButton btnLoadStatus;
	// ���л���ť
	private JButton btnSaveStatus;
	// ��¼��ť
	private JButton btnLogin;
	// ��ǰ�û�
	private UserInfo currentUserInfo;
	// ���������
	private JScrollPane scrollOutput;
	// ��ǰ�˻���ǩ
	private JLabel currentLabel;
	// �¸��˻���ť
	private JButton nextBtn;
	// �˻��������
	private JPanel panelAccountOperation;
	// ��֤��
	private JLabel lblCaptcha;
	// ���ݲ������
	private JPanel panelDataOperation;
	// ��ʼ����ť
	private JButton btnStartTask;
	// ��ʼ���Ӱ�ť
	private JButton btnOpenAutoStay;

	private JLabel lblOperation;
	// ��������ǩ
	private JLabel lblThread;
	// ������
	private JLabel lblThreadCount;
	// ����������
	private JPanel panelTaskOperation;
	// ״̬У�鰴ť
	private JButton btnVerifyStatus;

	private JPanel panelAccount;
	// �˻����밴ť
	private JButton btnImportAccount;
	// ׼����½��ť
	private JButton btnInitLogin;
	private JLabel label;
	public JLabel lblNextCheck;

	public MainWindow() {
		init();
	}

	private void init() {
		setTitle("< Auto - Task > By: Carl.Huang");
		setBackground(Color.WHITE);
		// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 438, 374);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		initAccountsTable();
		initAccountOperation();
		initDataOperation();
		initTaskOperation();
		initOutput();
		initEvents();
	}

	/*
	 * ���湹��,�˻���Ϣ���
	 */
	private void initAccountsTable() {
		String[] name = { "�û���", "״̬��Ϣ" };
		model = new DefaultTableModel(null, name);
		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.BOLD, 13));
		table.setEnabled(false);
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 10));
		JScrollPane scrollPaneTable = new JScrollPane(table);
		scrollPaneTable.setBounds(10, 11, 310, 160);
		contentPane.add(scrollPaneTable);
	}

	/*
	 * ���湹��,��Ϣ�����
	 */
	private void initOutput() {
		// �����ı���
		logTextArea = new JTextArea();
		logTextArea.setTabSize(2);
		logTextArea.setWrapStyleWord(true);
		logTextArea.setLineWrap(true);
		// logTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK,
		// 4));
		logTextArea.setBounds(0, 0, 418, 211);
		// �������
		scrollOutput = new JScrollPane(logTextArea);
		scrollOutput.setBounds(10, 183, 418, 211);
		scrollOutput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
//		contentPane.add(scrollOutput);

		// scrollOutput.add(logTextArea);
	}

	/*
	 * ���湹��,���������
	 */
	private void initTaskOperation() {
		panelTaskOperation = new JPanel();
		panelTaskOperation.setBounds(332, 217, 99, 126);
		panelTaskOperation.setBorder(BorderFactory.createLineBorder(
				Color.BLACK, 1));
		panelTaskOperation.setLayout(null);
		contentPane.add(panelTaskOperation);

		btnStartTask = new JButton("\u5F00\u59CB\u4EFB\u52A1");
		btnStartTask.setFont(new Font("Dialog", Font.BOLD, 13));
		btnStartTask.setBounds(6, 6, 89, 36);
		panelTaskOperation.add(btnStartTask);

		btnOpenAutoStay = new JButton("\u5F00\u542F\u81EA\u52A8\u76D1\u89C6");
		btnOpenAutoStay.setFont(new Font("Dialog", Font.BOLD, 13));
		btnOpenAutoStay.setBounds(6, 41, 89, 36);
		panelTaskOperation.add(btnOpenAutoStay);

		lblThread = new JLabel("\u4EFB\u52A1\u6570:");
		lblThread.setBounds(6, 79, 42, 16);
		lblThread.setFont(new Font("Dialog", Font.BOLD, 13));
		panelTaskOperation.add(lblThread);

		lblThreadCount = new JLabel("0");
		lblThreadCount.setFont(new Font("Dialog", Font.BOLD, 13));
		lblThreadCount.setBounds(53, 79, 42, 16);
		panelTaskOperation.add(lblThreadCount);
		
		label = new JLabel("\u5012\u8BA1\u65F6:");
		label.setFont(new Font("Dialog", Font.BOLD, 13));
		label.setBounds(6, 98, 42, 16);
		panelTaskOperation.add(label);
		
		lblNextCheck = new JLabel("\u672A\u542F\u52A8");
		lblNextCheck.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNextCheck.setBounds(53, 98, 42, 16);
		panelTaskOperation.add(lblNextCheck);
	}

	/*
	 * ���湹��,���ݲ�����
	 */
	private void initDataOperation() {
		panelDataOperation = new JPanel();
		panelDataOperation.setBounds(332, 11, 99, 117);
		panelDataOperation.setBorder(BorderFactory.createLineBorder(
				Color.BLACK, 1));
		panelDataOperation.setLayout(null);
		contentPane.add(panelDataOperation);

		// ����״̬��ť
		btnSaveStatus = new JButton("\u4FDD\u5B58\u72B6\u6001");
		btnSaveStatus.setForeground(Color.BLUE);
		btnSaveStatus.setFont(new Font("Dialog", Font.BOLD, 13));
		btnSaveStatus.setBackground(Color.WHITE);
		btnSaveStatus.setBounds(6, 41, 90, 36);
		panelDataOperation.add(btnSaveStatus);

		// ����״̬��ť
		btnLoadStatus = new JButton("\u8F7D\u5165\u72B6\u6001");
		btnLoadStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnLoadStatus.setForeground(Color.BLUE);
		btnLoadStatus.setBounds(6, 6, 90, 36);
		panelDataOperation.add(btnLoadStatus);

		// ��֤״̬��ť
		btnVerifyStatus = new JButton("\u68C0\u9A8C\u72B6\u6001");
		btnVerifyStatus.setForeground(Color.BLUE);
		btnVerifyStatus.setFont(new Font("Dialog", Font.BOLD, 13));
		btnVerifyStatus.setBackground(Color.WHITE);
		btnVerifyStatus.setBounds(6, 78, 90, 36);
		panelDataOperation.add(btnVerifyStatus);

		// �˻��������
		panelAccount = new JPanel();
		panelAccount.setLayout(null);
		panelAccount.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		panelAccount.setBounds(332, 133, 99, 82);
		contentPane.add(panelAccount);

		// �����˻��ļ���ť
		btnImportAccount = new JButton("\u8F7D\u5165\u8D26\u6237");
		btnImportAccount.setForeground(Color.RED);
		btnImportAccount.setBounds(2, 3, 94, 36);
		btnImportAccount.setFont(new Font("Dialog", Font.BOLD, 13));
		panelAccount.add(btnImportAccount);

		// ׼����½��ť
		btnInitLogin = new JButton("\u51C6\u5907\u767B\u9646");
		btnInitLogin.setForeground(Color.RED);
		btnInitLogin.setFont(new Font("Dialog", Font.BOLD, 13));
		btnInitLogin.setBounds(2, 42, 94, 36);
		panelAccount.add(btnInitLogin);
	}

	/*
	 * ���湹��,�˻�������
	 */
	private void initAccountOperation() {
		panelAccountOperation = new JPanel();
		panelAccountOperation.setBounds(10, 183, 310, 160);
		panelAccountOperation.setLayout(null);
		panelAccountOperation.setBorder(BorderFactory.createLineBorder(
				Color.BLACK, 1));
		contentPane.add(panelAccountOperation);

		// ��ǰ�˻����
		accountLabel = new JLabel("");
		accountLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		accountLabel.setBounds(53, 9, 249, 43);
		accountLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panelAccountOperation.add(accountLabel);

		// ��֤��ͼƬ
		captcha = new JLabel();
		captcha.setBounds(53, 60, 120, 43);
		captcha.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panelAccountOperation.add(captcha);

		// ��֤�������
		captchaText = new JTextField(7);
		captchaText.setEnabled(false);
		captchaText.setBounds(185, 60, 120, 43);
		panelAccountOperation.add(captchaText);

		// ��ǰ�˻���ǩ
		currentLabel = new JLabel("\u8D26\u6237 :");
		currentLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		currentLabel.setBounds(6, 26, 68, 26);
		panelAccountOperation.add(currentLabel);

		// �¸��˻���ť
		nextBtn = new JButton("\u4E0B\u4E00\u4E2A");
		nextBtn.setEnabled(false);
		nextBtn.setForeground(Color.BLACK);
		nextBtn.setBackground(Color.WHITE);
		nextBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
		nextBtn.setBounds(53, 112, 117, 36);
		panelAccountOperation.add(nextBtn);

		// ��֤���ǩ
		lblCaptcha = new JLabel("\u9A8C\u8BC1\u7801:");
		lblCaptcha.setFont(new Font("Dialog", Font.BOLD, 13));
		lblCaptcha.setBounds(6, 74, 68, 26);
		panelAccountOperation.add(lblCaptcha);

		// ��¼��ť
		btnLogin = new JButton("\u767B\u9646");
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setEnabled(false);
		btnLogin.setFont(new Font("Dialog", Font.BOLD, 13));
		btnLogin.setBackground(Color.BLACK);
		btnLogin.setBounds(185, 112, 117, 36);
		panelAccountOperation.add(btnLogin);

		// ������ǩ
		lblOperation = new JLabel("\u64CD\u4F5C:");
		lblOperation.setFont(new Font("Dialog", Font.BOLD, 13));
		lblOperation.setBounds(6, 122, 85, 26);
		panelAccountOperation.add(lblOperation);

	}

	/*
	 * ��ʼ���¼�
	 */
	private void initEvents() {
		/*************** �ؼ��¼����� *****************/
		// ״̬��֤
		btnVerifyStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.initAllAccount();
			}
		});
		// ��֤�������س��¼�
		captchaText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					btnLogin.doClick();
				}
			}
		});

		// �¸��˻���ť�¼�
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.nextAccount();
			}
		});

		// ��¼��ť�¼�
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = captchaText.getText();
				if (!"".equals(code)) {
					controller.Login(currentUserInfo, code);
					captchaText.setText("");
					captchaText.setEditable(false);
					captcha.setIcon(null);
					accountLabel.setText("");
					// nextBtn.doClick();
					setLoginBtnAble(false);
					return;
				}
				showMessage("��֤��Ϊ��,������.");
			}
		});

		// ���л���ť�¼�
		btnSaveStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(); // �Ի���
				int i = fileChooser.showSaveDialog(getContentPane()); // opendialog
				if (i == JFileChooser.APPROVE_OPTION) // �ж��Ƿ�Ϊ�򿪵İ�ť
				{
					File selectedFile = fileChooser.getSelectedFile(); // ȡ��ѡ�е��ļ�
					String path = selectedFile.getPath();
					saveSerializableFile(path);
				}
			}
		});

		// �����л���ť�¼�
		btnLoadStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(); // �Ի���
				int i = fileChooser.showOpenDialog(getContentPane());
				if (i == JFileChooser.APPROVE_OPTION) // �ж��Ƿ�Ϊ�򿪵İ�ť
				{
					File selectedFile = fileChooser.getSelectedFile(); // ȡ��ѡ�е��ļ�
					String path = selectedFile.getPath();
					loadSerializableFile(path);
				}
			}
		});

		// �����˻��¼�
		btnImportAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser(); // �Ի���
				int i = fileChooser.showOpenDialog(getContentPane()); // opendialog
				if (i == JFileChooser.APPROVE_OPTION) // �ж��Ƿ�Ϊ�򿪵İ�ť
				{
					File selectedFile = fileChooser.getSelectedFile(); // ȡ��ѡ�е��ļ�
					String path = selectedFile.getPath();
					loadAccountFile(path);
				}
			}
		});

		// ׼����½�¼�
		btnInitLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.initAllAccount();
			}
		});

		// ��ʼ����
		btnStartTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.startTask();
			}
		});

		btnOpenAutoStay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// ȫ���Զ������߳�
				String title = btnOpenAutoStay.getText();
				if ("\u5F00\u542F\u81EA\u52A8\u76D1\u89C6".equals(title)) {
					btnOpenAutoStay.setText("ֹͣ�Զ�����");
					btnOpenAutoStay.setForeground(Color.RED);
					controller.runGlobalThread();
					//setAllEnable(false);
				} else {
					btnOpenAutoStay
							.setText("\u5F00\u542F\u81EA\u52A8\u76D1\u89C6");
					btnOpenAutoStay.setForeground(Color.BLACK);
					controller.shutdownGlobalThread();
					//setAllEnable(true);
				}
			}
		});
		/******************************************/
	}

	/*
	 * �����˻���Ϣȡ��֤��
	 */
	public void setCaptchaAndAccount(UserInfo userInfo) {
		currentUserInfo = userInfo;
		accountLabel.setText(userInfo.getUsername());
		captcha.setIcon(new ImageIcon(userInfo.getCaptcha()));
		captcha.repaint();
		setLoginBtnAble(true);
		captchaText.setEnabled(true);
	}

	/*
	 * ȡ�������˻���Ϣ,����ʾ������� ���ݱ���������˻��������
	 */
	public void showAllAccountInTable(List<UserInfo> infos) {
		String[][] data = new String[infos.size()][2];
		for (int j = 0; j < infos.size(); j++) {
			UserInfo u = infos.get(j);
			u.setRowIndex(j);
			data[j] = new String[] { u.getUsername(), u.getStatus() };
		}
		String[] head = { "username", "status" };
		model = new DefaultTableModel(data, head);
		table.setModel(model);
		((DefaultTableModel) table.getModel()).fireTableDataChanged();
		// ((DefaultTableModel)table.getModel()).fireTableStructureChanged();;
	}

	/*
	 * ���µ����˻���Ϣ
	 */
	public void showAccountInTable(UserInfo userInfo) {
		model.setValueAt(userInfo.getStatus(), userInfo.getRowIndex(), 1);
		table.invalidate();
	}

	/*
	 * ����������
	 */
	public void clearTable() {
		model.setRowCount(0);
	}

	/*
	 * ȫ�ּ�������������
	 */
	public void setAllEnable(boolean b) {
		captchaText.setEnabled(b);
		//nextBtn.setEnabled(b);
		btnImportAccount.setEnabled(b);
		btnLoadStatus.setEnabled(b);
		//btnLogin.setEnabled(b);
		btnStartTask.setEnabled(b);
		btnSaveStatus.setEnabled(b);
		btnVerifyStatus.setEnabled(b);
		btnInitLogin.setEnabled(b);
	}

	/*
	 * ����������͵����˻��ļ�����
	 */
	private void loadAccountFile(String path) {
		controller.loadAccountFile(path);
	}

	/*
	 * ����������ͷ����л�����
	 */
	private void loadSerializableFile(String path) {
		controller.loadSerializableFile(path);
	}

	/*
	 * ��������������л�����
	 */
	private void saveSerializableFile(String path) {
		controller.saveSerializableFile(path);
	}

	/*
	 * �����־
	 */
	public void showLogs(String log) {
		if (logTextArea.getLineCount() > 100) {
			logTextArea.setText(null);
		}
		logTextArea.append(log);
		JScrollBar bar = scrollOutput.getVerticalScrollBar();
		bar.setValue(bar.getMaximum() + 10);
	}

	/*
	 * ���������
	 */
	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "����",
				JOptionPane.PLAIN_MESSAGE);
	}

	public synchronized void updateThread(int count, boolean isGlobalRun) {
		if (!isGlobalRun) {
			if (count == 0) {
				btnOpenAutoStay.setEnabled(true);
			} else {
				btnOpenAutoStay.setEnabled(false);
			}
		}
		lblThreadCount.setText("" + count);
	}

	/*
	 * ���õ�¼��ť
	 */
	public void setLoginBtnAble(boolean b) {
		btnLogin.setEnabled(b);
		captchaText.setEnabled(b);

	}
	/*
	 * ���������˻���ť
	 */
	public void setBtnImportAccountAble(boolean b) {
		btnImportAccount.setEnabled(b);
	}
	/*
	 * ��������״̬��ť
	 */
	public void setBtnLoadStatus(boolean b) {
		btnLoadStatus.setEnabled(b);
	}
	/*
	 * ����У��״̬��ť
	 */
	public void setBtnVerifyStatus(boolean b) {
		btnVerifyStatus.setEnabled(b);
	}
	/*
	 * ����׼����½��ť
	 */
	public void setBtnInitLogin(boolean b) {
		btnInitLogin.setEnabled(b);
	}
	public void setController(MainController controller) {
		this.controller = controller;
	}
}
