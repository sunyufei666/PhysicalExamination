package com.entity;

import java.security.KeyStore.PrivateKeyEntry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

@Entity
@Table(name="student_exam")
public class StudentExam {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int student_id;
	private int exam_id;
	private String if_complete="未完成";
	private String if_pass="未通过";
	private String stand_jump="暂无";
	private String fifty_meter="暂无";
	private String eighthundred_meter="暂无";
	private String onethousand_meter="暂无";
	private String pull_up="暂无";
	private String sitting_forward="暂无";
	private String sit_up="暂无";
	private String vital_capacity="暂无";
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
	public int getExam_id() {
		return exam_id;
	}
	public void setExam_id(int exam_id) {
		this.exam_id = exam_id;
	}
	public String getIf_complete() {
		return if_complete;
	}
	public void setIf_complete(String if_complete) {
		this.if_complete = if_complete;
	}
	public String getIf_pass() {
		return if_pass;
	}
	public void setIf_pass(String if_pass) {
		this.if_pass = if_pass;
	}
	public String getStand_jump() {
		return stand_jump;
	}
	public void setStand_jump(String stand_jump) {
		this.stand_jump = stand_jump;
	}
	public String getFifty_meter() {
		return fifty_meter;
	}
	public void setFifty_meter(String fifty_meter) {
		this.fifty_meter = fifty_meter;
	}
	public String getEighthundred_meter() {
		return eighthundred_meter;
	}
	public void setEighthundred_meter(String eighthundred_meter) {
		this.eighthundred_meter = eighthundred_meter;
	}
	public String getOnethousand_meter() {
		return onethousand_meter;
	}
	public void setOnethousand_meter(String onethousand_meter) {
		this.onethousand_meter = onethousand_meter;
	}
	public String getPull_up() {
		return pull_up;
	}
	public void setPull_up(String pull_up) {
		this.pull_up = pull_up;
	}
	public String getSitting_forward() {
		return sitting_forward;
	}
	public void setSitting_forward(String sitting_forward) {
		this.sitting_forward = sitting_forward;
	}
	public String getSit_up() {
		return sit_up;
	}
	public void setSit_up(String sit_up) {
		this.sit_up = sit_up;
	}
	public String getVital_capacity() {
		return vital_capacity;
	}
	public void setVital_capacity(String vital_capacity) {
		this.vital_capacity = vital_capacity;
	}
	@Override
	public String toString() {
		return "Student_exam [id=" + id + ", student_id=" + student_id + ", exam_id=" + exam_id + ", if_complete="
				+ if_complete + ", if_pass=" + if_pass + ", stand_jump=" + stand_jump + ", fifty_meter=" + fifty_meter
				+ ", eighthunder_meter=" + eighthundred_meter + ", onethousand_meter=" + onethousand_meter + ", pull_up="
				+ pull_up + ", sitting_forward=" + sitting_forward + ", sit_up=" + sit_up + ", vital_capacity="
				+ vital_capacity + "]";
	}
	
}
