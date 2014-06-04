package com.carl.http;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.regexp.RE;
import org.apache.regexp.RECompiler;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class ParseHtml {
	/*
	 * ȡ�ý���http://tasks.libertagia.com��POST����
	 * ������:secret
	 * ������ʽ:HtmlCleaner
	 * Ŀ��:<input type="hidden" value="5386c7b1aa2a948b1492c545" name="secret">
	 */
	public static String getSecretParam(String result) {
		 HtmlCleaner cleaner = new HtmlCleaner();  
		 TagNode node = cleaner.clean(result);
		 String secret = null;
		 try {
			 Object[] ns = node.evaluateXPath("//input[@type='hidden']");
			 if(ns.length > 0){
				 secret = ((TagNode)ns[0]).getAttributeByName("value");
			 }
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return secret;
	}
	
	/*
	 * ȡ��json���¸������token
	 */
	public static String getNextTask(String result) {
		RE re = new RE(); // �½�������ʽ����;
		RECompiler compiler = new RECompiler(); // �½��������;
		re.setProgram(compiler.compile("\"next_task\":\"[\\d\\w=]+")); // ����
		boolean bool = re.match(result); // �����Ƿ�ƥ��;
		System.out.println(bool);
		if (bool) {
			String tmp = re.getParen(0);
			tmp = tmp.replaceFirst("\"next_task\":\"", "");
			return tmp;
		}
		return null;
	}
	
	public static int getTaskCaptcha(String result) {
		RE re = new RE(); // �½�������ʽ����;
		RECompiler compiler = new RECompiler(); // �½��������;
		re.setProgram(compiler
				.compile("<span id=\"cap_text\".*>\\d+\\s*.+\\s\\d+")); // ����
		boolean bool = re.match(result); // �����Ƿ�ƥ��;
		System.out.println(bool);
		if (bool) {
			String tmp = re.getParen(0).replaceAll(
					"<span id=\"cap_text\" class=\"input-group-addon\">", "");
			String[] a = tmp.split(" ");
			if (a.length != 3) {
				return 0;
			}
			if (a[1].equals("+")) {
				int one = Integer.parseInt(a[0]);
				int two = Integer.parseInt(a[2]);
				return one+two;
			}else if (a[1].equals("-")) {
				int one = Integer.parseInt(a[0]);
				int two = Integer.parseInt(a[2]);
				return one - two;
			}
			
		}
		return -1;
	}
	
	/*
	 * ��ȡjson��������������
	 */
	public static int getDoneTaskCount(String result) {
		RE re = new RE(); // �½�������ʽ����;
		RECompiler compiler = new RECompiler(); // �½��������;
		re.setProgram(compiler.compile("\\[.*,*\\]")); // ����
		boolean bool = re.match(result); // �����Ƿ�ƥ��;
		System.out.println(bool);
		if (bool) {
			String tmp = re.getParen(0);
			System.out.println(tmp);
			tmp = tmp.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "");
			if ("".equals(tmp)) {
				return 1;
			}
			String[] a = tmp.split(",");
			System.out.println(a.length);
			return a.length+1;
		}
		return 0;
	}
	/*
	 * ��ȡjson���Ƿ����������
	 */
	public static boolean isDoneTask(String result) {
		return !result.contains("\"finished\":false");
		
	}
	/*
	 * HTML�ж������Ƿ����
	 */
	public static boolean isCompletedTask(String result) {
		if(result.contains("Your daily tasks have been completed successfully"))
			return true;
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String today = f.format(new Date());
		HtmlCleaner cleaner = new HtmlCleaner();  
		 TagNode node = cleaner.clean(result);
		 try {
			 Object[] td = node.evaluateXPath("//table//tbody//tr//td[1]");
			 if(td.length == 0)
				 return false;
			 for (Object o : td) {
				String date = ((TagNode)o).getText().toString();
				if(today.equals(date))
					return true;
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		 return false;
	}
	/*
	 * ��֤��¼״̬�Ƿ���Ч
	 */
	public static boolean verifyLoginStatus(String result ,String find) {
		return result.contains(find);
	}
	/*
	 * ��֤��¼״̬�Ƿ���Ч
	 */
	public static boolean verifyLoginStatus(String result) {
		return result.contains("Welcome");
	}
}
