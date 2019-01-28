package com.hongqiao.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hongqiao.dao.TextDao;
import com.hongqiao.dao.TextDaoImpl;
import com.hongqiao.dto.Text;

import javafx.util.Pair;

public class TextServiceImpl implements TextService {

	final String TextTable="textTable";
	final String[] textItems= {"id", "summary","title"};
	TextDao textDao=new TextDaoImpl();
	
	@Override
	public List<Text> selectAll() {
		try {
			List<Text> allTexts=textDao.selectAll(TextTable);
			return allTexts;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Boolean deleteText(String id) {
		try {
			return textDao.delete(TextTable, id);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}


	@Override
	public List<Text> selectByKeyWord(String keyWord) {
		StringBuilder param=new StringBuilder();
		for(int i=0;i<textItems.length;i++) {
			if(i!=textItems.length-1)param.append(" ("+textItems[i]+" Like '%"+keyWord+"%') or");
			else param.append(" ("+textItems[i]+" Like '%"+keyWord+"%') ");
		}
		
//		param.add(" id="+id);
		List<String> params=new ArrayList<String>();
		params.add(param.toString());
		try {
			List<Text> texts=textDao.select(TextTable, textItems,params);
			return texts;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Text selectById(String id) {
		List<String> param=new ArrayList<String>();
		param.add(" id="+id);
		try {
			List<Text> texts=textDao.select(TextTable, textItems, param);
			if(texts.size()!=1) {
				return null;
			}else {
				return texts.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Boolean crateTable() {
		try {
			textDao.crateTable(TextTable, textItems);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	@Override
	public Boolean insertText(Text text) {	
		String[] values={text.getId(),text.getSummary(),text.getTitle()};
		try {
			return textDao.insert(TextTable, textItems, values);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public Boolean updateText(Text text) {
		List< Pair<String, String>> items=new ArrayList<Pair<String,String>>();
		items.add(new Pair<String,String>("id",text.getId()));
		items.add(new Pair<String,String>("title",text.getTitle()));
		items.add(new Pair<String,String>("summary",text.getSummary()));
		try {
			return textDao.update(TextTable, items);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
