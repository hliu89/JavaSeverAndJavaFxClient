package com.hongqiao.service;

import java.util.List;
import com.hongqiao.dto.Text;

public interface TextService {

	public List<Text> selectAll();
	public Text selectById(String id);
	public Boolean deleteText(String id);
	public Boolean insertText(Text text);
	public List<Text> selectByKeyWord(String keyWord);
	public Boolean crateTable();
	public Boolean updateText(Text text);
}
