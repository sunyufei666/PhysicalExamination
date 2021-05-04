package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="news")
public class News {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String news_content;
	private String news_date;
	private String if_view;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNews_content() {
		return news_content;
	}
	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}
	public String getNews_date() {
		return news_date;
	}
	public void setNews_date(String news_date) {
		this.news_date = news_date;
	}
	
	public String getIf_view() {
		return if_view;
	}
	public void setIf_view(String if_view) {
		this.if_view = if_view;
	}
	@Override
	public String toString() {
		return "News [id=" + id + ", news_content=" + news_content + ", news_date=" + news_date + ", if_view=" + if_view
				+ "]";
	}
	
	
	
	
}
