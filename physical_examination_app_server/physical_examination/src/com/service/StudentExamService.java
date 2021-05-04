package com.service;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.StudentExamDao;
import com.entity.StudentExam;

@Service
@Transactional(readOnly=false)
public class StudentExamService {

	@Resource
	private StudentExamDao studentExamDao;
	
	public String examDetail(Integer id ) {
		return this.studentExamDao.examDetail(id);
	}
	
	public String examDetails() {
		return this.studentExamDao.examDetails();
	}
	
	public String returnFor(Integer id,String type,String value) {
		return this.studentExamDao.returnFor(id, type, value);
	}
	
	public String a(Integer student_id,Integer exam_id) {
		return this.studentExamDao.a(student_id, exam_id);
	}
	
	public String update(Integer id,String if_pass,String stand_jump,String fifty_meter,String eighthundred_meter,String onethousand_meter,
			String pull_up,String sitting_forward,String sit_up,String vital_capacity,String sex) {
		return this.studentExamDao.update(id, if_pass, stand_jump, fifty_meter, eighthundred_meter, onethousand_meter, pull_up, sitting_forward, sit_up, vital_capacity, sex);
	}
}
