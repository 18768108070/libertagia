package com.carl.http;

import org.apache.regexp.RE;
import org.apache.regexp.RECompiler;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class ParseHtml {
	/*
	 * ȡ�ý���http://tasks.libertagia.com��POST����
	 * ������:secret
	 * ������ʽ:HtmlCleaner
	 * Ŀ��:<input type="hidden" value="5386c7b1aa2a948b1492c545" name="secret">
	 */
	public static String getSecretParam(String result) {
		HtmlCleaner c = new HtmlCleaner();
		TagNode root =c.clean(result);
		TagNode[] t = root.getElementsByAttValue("name", "secret", true, false);
		for (TagNode tagNode : t) {
			return tagNode.getAttributeByName("value");
		}
		return null;
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
				return 0;
			}
			String[] a = tmp.split(",");
			System.out.println(a.length);
			return a.length;
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
		return result.contains("Your daily tasks have been completed successfully");
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
	public static void main(String[] args) {
		String s = "[\"ZWFmZDJkYmM5YTY3ZDQ0OGM1MWNhZWNkOGZmYTNiM2Z8ZDNkeEdldEI4RG1ZMWQ5Qw==\",\"ZWFmZDJkYmM5YTY3ZDQ0OGM1MWNhZWNkOGZmYTNiM2Z8V09Pc3JnTnNUTEdVZlQ3VA==\",\"ZWFmZDJkYmM5YTY3ZDQ0OGM1MWNhZWNkOGZmYTNiM2Z8ZmE1YjA1ZWJhMjRlOTk0Mg==\"]";
		RE re = new RE(); // �½�������ʽ����;
		RECompiler compiler = new RECompiler(); // �½��������;
		re.setProgram(compiler.compile("\\[.+,*\\]")); // ����
		boolean bool = re.match(s); // �����Ƿ�ƥ��;
		System.out.println(bool);
		if (bool) {
			String tmp = re.getParen(0);
			System.out.println(tmp);
			tmp = tmp.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "");
			String[] a = tmp.split(",");
			System.out.println(a.length);
			for (String string : a) {
				System.out.println(string);
			}
		}
	}
}
