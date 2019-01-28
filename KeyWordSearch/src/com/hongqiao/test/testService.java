package com.hongqiao.test;

import org.junit.Test;

import com.hongqiao.dto.Text;
import com.hongqiao.service.TextService;
import com.hongqiao.service.TextServiceImpl;

public class testService {
	 TextService service=new TextServiceImpl();
	@Test
	public void testcreateTable() {
		service.crateTable();
	}
 
	@Test
	public void testSelect() {
		String keyWord="a";
		System.out.println(service.selectAll().get(0).getId()+", "+service.selectAll().get(0).getSummary());
		System.out.println("keyword: "+service.selectByKeyWord(keyWord).get(0).getSummary());
		
	}
	
	@Test
	public void testInsert() {
		Text text=new Text();
		text.setId("2");
		text.setSummary("Practicing meditation and mindfulness will make you at least 10 percent happier.");
		text.setTitle("10% Happier");
		service.insertText(text);
		
		Text text1=new Text();
		text1.setId("3");
		text1.setSummary("Taking massive action is the only way to fulfill your true potential.");
		text1.setTitle("The 10X Rule");
		service.insertText(text1);
		
		text1.setId("4");
		text1.setSummary("The only thing you have that nobody else has is control of your life. ");
		text1.setTitle("A Short Guide to a Happy Life");
		service.insertText(text1);
		
		text1.setId("5");
		text1.setSummary("An idea occurs when you develop a new combination of old elements. ");
		text1.setTitle("A Technique for Producing Ideas");
		service.insertText(text1);
	}
}