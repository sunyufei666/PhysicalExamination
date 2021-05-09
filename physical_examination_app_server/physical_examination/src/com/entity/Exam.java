package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="exam")
public class Exam {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String place;
	private String all_people;
	private String exam_date;
	private String item="立定跳远、50米、800米、1000米、引体向上、坐位体前屈、仰卧起坐、肺活量";
	private String public_date;
	private String teacher;
	private String current_people="0";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getAll_people() {
		return all_people;
	}
	public void setAll_people(String all_people) {
		this.all_people = all_people;
	}
	public String getExam_date() {
		return exam_date;
	}
	public void setExam_date(String exam_date) {
		this.exam_date = exam_date;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getPublic_date() {
		return public_date;
	}
	public void setPublic_date(String public_date) {
		this.public_date = public_date;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public String getCurrent_people() {
		return current_people;
	}
	public void setCurrent_people(String current_people) {
		this.current_people = current_people;
	}
	@Override
	public String toString() {
		return "Exam [id=" + id + ", place=" + place + ", all_people=" + all_people + ", exam_date=" + exam_date
				+ ", item=" + item + ", public_date=" + public_date + ", teacher=" + teacher + ", current_people="
				+ current_people + "]";
	}
	
	
}
