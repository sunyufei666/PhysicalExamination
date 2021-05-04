package com.service;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dao.StudentInformationDao;
import com.entity.Exam;
import com.entity.Student;

@Service
@Transactional(readOnly=false)
public class StudentInformationService {

	
	@Resource
	private StudentInformationDao studentInformationDao;
	
	public String information(String sno,String type,String param) {
		return this.studentInformationDao.information(sno, type, param);
	}
	
	public String returnAllStudent() {
		return this.studentInformationDao.returnAllStudent();
	}
	public String returnStudentByType(String type,String value) {
		return this.studentInformationDao.returnStudentByType(type, value);
	}
	
	public String updateStudent(Integer id,String sno,String university,String college,String name,String grade,String classes,
			String introduction,String major,String nickname,String sex) {
		return this.studentInformationDao.updateStudent(id, sno, university, college, name, grade, classes, introduction, major, nickname, sex);

	}
	
	public String deleteStudent(Integer id) {
		return this.studentInformationDao.deleteStudent(id);
	}
	
	public String insertStudent(String sno,String password,String university,String college,String name,String grade,String classes,
			String register_time,String major,String nickname,String sex) {
		return this.studentInformationDao.insertStudent(sno, password, university, college, name, grade, classes, register_time, major, nickname, sex);
	}
}
