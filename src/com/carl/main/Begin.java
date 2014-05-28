package com.carl.main;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

import com.carl.controller.MainController;
import com.carl.pojo.UserInfo;
import com.carl.window.MainWindow;

public class Begin {
	private static MainWindow frame;
	private static Map<String, UserInfo> infos = new HashMap<String, UserInfo>();

	// public static void main(String[] args) {
	// frame = new MainWindow();
	// frame.setVisible(true);
	// infos.put("1114486604@qq.com", new UserInfo("1114486604@qq.com",
	// "567890"));
	// // infos.get("1114486604@qq.com")
	// frame.setCaptcha(infos.get("1114486604@qq.com").getCaptcha());
	// }
	
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainController controller = new MainController();
					MainWindow window = new MainWindow();
					controller.setWindow(window);
					window.setController(controller);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
