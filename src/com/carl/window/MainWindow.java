package com.carl.window;

import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.carl.controller.MainController;
import com.carl.pojo.UserInfo;

public class MainWindow extends JFrame {

	// �������
	private JPanel contentPane;
	// �˻����
	private JTable table;
	// ��֤��ͼƬ
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
	private JButton initBtn;
	// �����л���ť
	private JButton btnLoadStatus;
	// ���л���ť
	private JButton btnSaveStatus;
	// �׸��˻���ʾ
	private boolean first = false;
	// ��¼��ť
	private JButton btnLogin;
	// ��ǰ�û�
	private UserInfo currentUserInfo;
	// ���������
	private JScrollPane scrollOutput;

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public MainWindow() {
		setBackground(Color.WHITE);
		// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 946, 585);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		initBtn = new JButton("init");
		initBtn.setBackground(Color.WHITE);
		initBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		initBtn.setBounds(773, 24, 117, 36);
		contentPane.add(initBtn);

		String[] name = { "username", "status" };
		model = new DefaultTableModel(null, name);
		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.BOLD, 13));
		table.setEnabled(false);
		table.setRowHeight(25);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 429, 381);
		contentPane.add(scrollPane);
		captcha = new JLabel();
		captcha.setBounds(484, 147, 120, 43);
		captcha.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPane.add(captcha);
		captchaText = new JTextField(20);
		captchaText.setBounds(484, 194, 117, 36);
		contentPane.add(captchaText);

		JLabel currentLabel = new JLabel("Account :");
		currentLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		currentLabel.setBounds(484, 76, 117, 26);
		contentPane.add(currentLabel);

		JButton nextBtn = new JButton("Next");
		nextBtn.setBackground(Color.WHITE);

		nextBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
		nextBtn.setBounds(484, 242, 117, 36);
		contentPane.add(nextBtn);

		accountLabel = new JLabel("");
		accountLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		accountLabel.setBounds(484, 110, 117, 26);
		accountLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPane.add(accountLabel);

		btnLoadAccount = new JButton("LoadAccount");
		btnLoadAccount.setFont(new Font("Dialog", Font.BOLD, 13));
		btnLoadAccount.setBounds(773, 167, 117, 36);
		contentPane.add(btnLoadAccount);

		logTextArea = new JTextArea();
		logTextArea.setLineWrap(true);
		logTextArea.setBounds(10, 413, 623, 133);

		scrollOutput = new JScrollPane(logTextArea);
		scrollOutput.setBounds(10, 413, 623, 133);
		contentPane.add(scrollOutput);

		JLabel lblNewLabel = new JLabel("Output:");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 395, 86, 14);

		btnSaveStatus = new JButton("SaveStatus");
		btnSaveStatus.setFont(new Font("Dialog", Font.BOLD, 13));
		btnSaveStatus.setBackground(Color.WHITE);
		btnSaveStatus.setBounds(773, 72, 117, 36);
		contentPane.add(btnSaveStatus);

		btnLoadStatus = new JButton("LoadStatus");
		btnLoadStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnLoadStatus.setBounds(773, 120, 117, 36);
		contentPane.add(btnLoadStatus);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Dialog", Font.BOLD, 13));
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setBounds(613, 242, 117, 36);
		contentPane.add(btnLogin);
		/*************** �ؼ��¼����� *****************/

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
					return;
				}
				showMessage("��֤��Ϊ��,������.");
			}
		});
		// ��ʼ����ť�¼�
		initBtn.addActionListener(new ActionListener() {
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
		/******************************************/

		contentPane.add(lblNewLabel);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
		rdbtnNewRadioButton.setBounds(475, 333, 109, 23);
		contentPane.add(rdbtnNewRadioButton);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton(
				"New radio button");
		rdbtnNewRadioButton_1.setBounds(475, 359, 109, 23);
		contentPane.add(rdbtnNewRadioButton_1);

		JLabel groupLabel = new JLabel("");
		groupLabel.setEnabled(false);
		groupLabel.setBounds(449, 11, 491, 381);
		groupLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPane.add(groupLabel);

	}

	/*
	 * �����˻���Ϣȡ��֤��
	 */
	public void setCaptchaAndAccount(UserInfo userInfo) {
		currentUserInfo = userInfo;
		accountLabel.setText(userInfo.getUsername());
		captcha.setIcon(new ImageIcon(userInfo.getCaptcha()));
		captcha.repaint();
	}

	/*
	 * ȡ�������˻���Ϣ,����ʾ������� ���ݱ���������˻��������
	 */
	public void showAllAccountInTable(List<UserInfo> infos) {
		// TODO ���˻����
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

	public void setController(MainController controller) {
		this.controller = controller;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}
}
