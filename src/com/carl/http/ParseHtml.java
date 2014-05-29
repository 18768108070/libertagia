package com.carl.http;

import java.io.IOException;

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
		HtmlCleaner c = new HtmlCleaner();
		TagNode root =c.clean(result);
		TagNode[] t = root.getElementsByAttValue("name", "secret", true, false);
		for (TagNode tagNode : t) {
			return tagNode.getAttributeByName("value");
		}
		return null;
	}
	
	public String getNextTask(String result) {
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
	public static void main(String[] args) throws IOException, XPatherException {
//		String result = Request.getURLResult("", "http://www.baidu.com");
//		System.out.println(result);
//		HtmlCleaner c = new HtmlCleaner();
//		TagNode node =c.clean(result);
//		TagNode[] t = node.getElementsByAttValue("id", "lk", true, false);
//		for (int i = 0; i < t.length; i++) {
//			System.out.println("i="+i+"\t"+ c.getInnerHtml(t[i]));
//		}
	}
}
