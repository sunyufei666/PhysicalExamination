package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="examination")
public class Examination {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String stu_number;
	private String stu_name;
	private float test_one;
	private float test_two;
	private float test_three;
	private float test_four;
	private float test_five;
	private int test_six;
	private int test_seven;
	private float grade;
	private short result;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStu_number() {
		return stu_number;
	}
	public void setStu_number(String stu_number) {
		this.stu_number = stu_number;
	}
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public float getTest_one() {
		return test_one;
	}
	public void setTest_one(float test_one) {
		this.test_one = test_one;
	}
	public float getTest_two() {
		return test_two;
	}
	public void setTest_two(float test_two) {
		this.test_two = test_two;
	}
	public float getTest_three() {
		return test_three;
	}
	public void setTest_three(float test_three) {
		this.test_three = test_three;
	}
	public float getTest_four() {
		return test_four;
	}
	public void setTest_four(float test_four) {
		this.test_four = test_four;
	}
	public float getTest_five() {
		return test_five;
	}
	public void setTest_five(float test_five) {
		this.test_five = test_five;
	}
	public int getTest_six() {
		return test_six;
	}
	public void setTest_six(int test_six) {
		this.test_six = test_six;
	}
	public int getTest_seven() {
		return test_seven;
	}
	public void setTest_seven(int test_seven) {
		this.test_seven = test_seven;
	}
	public float getGrade() {
		return grade;
	}
	public void setGrade(float grade) {
		this.grade = grade;
	}
	public short getResult() {
		return result;
	}
	public void setResult(short result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "Examination [id=" + id + ", stu_number=" + stu_number + ", stu_name=" + stu_name + ", test_one="
				+ test_one + ", test_two=" + test_two + ", test_three=" + test_three + ", test_four=" + test_four
				+ ", test_five=" + test_five + ", test_six=" + test_six + ", test_seven=" + test_seven + ", grade="
				+ grade + ", result=" + result + "]";
	}
	
}
