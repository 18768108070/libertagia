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
	// ���밴ť
	private JButton btnLoadAccount;
	// ������
	private MainController controller;
	// ��־�����
	private JTextArea logTextArea;
	// ��ģ��
	private DefaultTableModel model;
	// �û�����ǩ
	private JLabel accountLabel;
	// ��ʼ����ť
	private JButton BtnInit;
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
	private JPanel panelAccountOperation;
	private JLabel lblCaptcha;
	private JPanel panelDataOperation;
	private JButton btnStartTask;
	private JButton btnOpenAutoStay;
	private JLabel lblOperation;
	private JLabel lblThread;
	private JLabel lblThreadCount;

	public MainWindow() {
		init();
	}

	private void init() {
		setTitle("< Auto - Task > By: Carl.Huang QQ:284642743");
		setBackground(Color.WHITE);
		// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 946, 499);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		initAccountsTable();
		initAccountOperation();
		initDataOperation();
		initOutput();
		initEvents();
	}

	/*
	 * ���湹��,�˻���Ϣ���
	 */
	private void initAccountsTable() {
		String[] name = { "username", "status" };
		model = new DefaultTableModel(null, name);
		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.BOLD, 13));
		table.setEnabled(false);
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 10));
		JScrollPane scrollPaneTable = new JScrollPane(table);
		scrollPaneTable.setBounds(10, 11, 276, 455);
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
		logTextArea.setEditable(false);
		logTextArea.setLineWrap(true);
//		logTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		logTextArea.setBounds(0, 0, 628, 283);
		// �������
		scrollOutput = new JScrollPane(logTextArea);
		scrollOutput.setBounds(298, 183, 628, 283);
		scrollOutput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		contentPane.add(scrollOutput);
//		scrollOutput.add(logTextArea);
	}

	/*
	 * ���湹��,���ݲ�����
	 */
	private void initDataOperation() {
		panelDataOperation = new JPanel();
		panelDataOperation.setBounds(665, 11, 261, 160);
		panelDataOperation.setBorder(BorderFactory.createLineBorder(
				Color.BLACK, 3));
		panelDataOperation.setLayout(null);
		contentPane.add(panelDataOperation);

		BtnInit = new JButton("Init");
		BtnInit.setBackground(Color.WHITE);
		BtnInit.setFont(new Font("Dialog", Font.BOLD, 13));
		BtnInit.setBounds(135, 13, 117, 36);
//		panelDataOperation.add(BtnInit);

		btnLoadAccount = new JButton("LoadAccount");
		btnLoadAccount.setFont(new Font("Dialog", Font.BOLD, 13));
		btnLoadAccount.setBounds(9, 13, 117, 36);
		panelDataOperation.add(btnLoadAccount);

		btnSaveStatus = new JButton("SaveStatus");
		btnSaveStatus.setFont(new Font("Dialog", Font.BOLD, 13));
		btnSaveStatus.setBackground(Color.WHITE);
		btnSaveStatus.setBounds(138, 13, 117, 36);
		panelDataOperation.add(btnSaveStatus);

		btnLoadStatus = new JButton("LoadStatus");
		btnLoadStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnLoadStatus.setBounds(9, 62, 117, 36);
		panelDataOperation.add(btnLoadStatus);

		btnStartTask = new JButton("StartTask");
		btnStartTask.setFont(new Font("Dialog", Font.BOLD, 13));
		btnStartTask.setBounds(9, 111, 117, 36);
		panelDataOperation.add(btnStartTask);

		btnOpenAutoStay = new JButton("OpenAutoStay");
		btnOpenAutoStay.setFont(new Font("Dialog", Font.BOLD, 13));
		btnOpenAutoStay.setBounds(138, 62, 117, 36);
		panelDataOperation.add(btnOpenAutoStay);
		
		lblThread = new JLabel("Thread:");
		lblThread.setBounds(138, 120, 61, 16);
		lblThread.setFont(new Font("Dialog", Font.BOLD, 13));
		panelDataOperation.add(lblThread);
		
		lblThreadCount = new JLabel("0");
		lblThreadCount.setFont(new Font("Dialog", Font.BOLD, 13));
		lblThreadCount.setBounds(200, 120, 39, 16);
		panelDataOperation.add(lblThreadCount);
	}

	/*
	 * ���湹��,�˻�������
	 */
	private void initAccountOperation() {
		panelAccountOperation = new JPanel();
		panelAccountOperation.setBounds(298, 11, 355, 160);
		panelAccountOperation.setLayout(null);
		panelAccountOperation.setBorder(BorderFactory.createLineBorder(
				Color.BLACK, 3));
		contentPane.add(panelAccountOperation);

		// ��ǰ�˻����
		accountLabel = new JLabel("");
		accountLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		accountLabel.setBounds(85, 15, 249, 43);
		accountLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panelAccountOperation.add(accountLabel);

		// ��֤��ͼƬ
		captcha = new JLabel();
		captcha.setBounds(85, 66, 120, 43);
		captcha.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panelAccountOperation.add(captcha);

		// ��֤�������
		captchaText = new JTextField(7);
		captchaText.setEnabled(false);
		captchaText.setBounds(217, 66, 120, 43);
		panelAccountOperation.add(captchaText);

		// ��ǰ�˻���ǩ
		currentLabel = new JLabel("Account :");
		currentLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		currentLabel.setBounds(6, 26, 68, 26);
		panelAccountOperation.add(currentLabel);

		// �¸��˻���ť
		nextBtn = new JButton("Next");
		nextBtn.setBackground(Color.WHITE);
		nextBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
		nextBtn.setBounds(85, 118, 117, 36);
		panelAccountOperation.add(nextBtn);

		// ��֤���ǩ
		lblCaptcha = new JLabel("Captcha:");
		lblCaptcha.setFont(new Font("Dialog", Font.BOLD, 13));
		lblCaptcha.setBounds(6, 74, 68, 26);
		panelAccountOperation.add(lblCaptcha);

		// ��¼��ť
		btnLogin = new JButton("Login");
		btnLogin.setEnabled(false);
		btnLogin.setFont(new Font("Dialog", Font.BOLD, 13));
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setBounds(217, 118, 117, 36);
		panelAccountOperation.add(btnLogin);

		lblOperation = new JLabel("Operation:");
		lblOperation.setFont(new Font("Dialog", Font.BOLD, 13));
		lblOperation.setBounds(6, 122, 85, 26);
		panelAccountOperation.add(lblOperation);

	}

	/*
	 * ��ʼ���¼�
	 */
	private void initEvents() {
		/*************** �ؼ��¼����� *****************/
		//��֤�������س��¼�
		captchaText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==10){
					btnLogin.doClick();
				}
			}
		});
		
		// loadAccount��ť�¼�
		btnLoadAccount.addActionListener(new ActionListener() {
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
					captcha.setIcon(null);
					accountLabel.setText("");
					nextBtn.doClick();
					return;
				}
				showMessage("��֤��Ϊ��,������.");
			}
		});
		// ��ʼ����ť�¼�
		BtnInit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.initAllAccount();
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
		
		//��ʼ����
		btnStartTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.startTask();
			}
		});
		
		btnOpenAutoStay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//ȫ���Զ������߳�
				String title = btnOpenAutoStay.getText();
				if ("OpenAutoStay".equals(title)) {
					btnOpenAutoStay.setText("StopAutoStay");
					btnOpenAutoStay.setForeground(Color.RED);
					controller.runGlobalThread();
					setAllEnable(false);
				}else {
					btnOpenAutoStay.setText("OpenAutoStay");
					btnOpenAutoStay.setForeground(Color.BLACK);
					controller.shutdownGlobalThread();
					setAllEnable(true);
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
		btnLogin.setEnabled(true);
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
		nextBtn.setEnabled(b);
		btnLoadAccount.setEnabled(b);
		btnLoadStatus.setEnabled(b);
		btnLogin.setEnabled(b);
		btnStartTask.setEnabled(b);
		btnSaveStatus.setEnabled(b);
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
		if (logTextArea.getLineCount()> 100) {
			logTextArea.setText(null);
		}
		logTextArea.append(log);
		JScrollBar bar = scrollOutput.getVerticalScrollBar();
		bar.setValue(bar.getMaximum());
	}

	/*
	 * ���������
	 */
	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "����",
				JOptionPane.PLAIN_MESSAGE);
	}
	public synchronized void updateThread(int count,boolean isGlobalRun) {
		if (!isGlobalRun) {
			if (count==0) {
				btnOpenAutoStay.setEnabled(true);
			}else {
				btnOpenAutoStay.setEnabled(false);
			}
		}
		lblThreadCount.setText(""+count);
	}
	/*
	 * ���õ�¼��ť
	 */
	public void setLoginBtnAble(boolean b) {
		btnLogin.setEnabled(b);
		captchaText.setEnabled(b);
		
	}
	public void setController(MainController controller) {
		this.controller = controller;
	}

}
