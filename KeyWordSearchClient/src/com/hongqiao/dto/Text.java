package com.hongqiao.dto;

public class Text {

	private String title;
	private String summary;
	private String id;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Text() {
		
	}
	
	public Text(String id, String title, String summary) {
		this.id=id;
		this.title=title;
		this.summary=summary;		
	}
	
}
