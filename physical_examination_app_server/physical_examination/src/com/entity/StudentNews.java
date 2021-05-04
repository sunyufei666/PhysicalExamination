package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student_news")
public class StudentNews {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int student_id;
	private int news_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public int getNews_id() {
		return news_id;
	}
	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}
	@Override
	public String toString() {
		return "StudentNews [id=" + id + ", student_id=" + student_id + ", news_id=" + news_id + "]";
	}
	
}
