package com.service;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ExamDao;
import com.entity.Exam;

@Service
@Transactional(readOnly=false)
public class ExamService {

	@Resource
	private ExamDao examinationDao;
	
	public String p(Integer student_id) {
		
		return this.examinationDao.p(student_id);

	}
	public String returnAllExam() {
		return this.examinationDao.returnAllExam();
	}
	public String returnExamByType(String type,String value) {
		return this.examinationDao.returnExamByType(type, value);
	}
	public String insertExam(String place,String all_people,String exam_date,String public_date,String teacher) {
		return this.examinationDao.insertExam(place, all_people, exam_date, public_date, teacher);
		}
	
	
	public String deleteExam(Integer id) {
		return this.examinationDao.deleteExam(id);
	}
	public String updateExam(Integer id,String place,String all_people,String exam_date,String teacher) {
		return this.examinationDao.updateExam(id, place, all_people, exam_date, teacher);
	}
//	
//	public String insertGrade(String stu_number,Double test_one,Double test_two,Double test_three,Double test_four,Double test_five,Integer test_six,Integer test_seven,Double grade) {
//		return this.examinationDao.insertGrade(stu_number, test_one, test_two, test_three, test_four, test_five, test_six, test_seven, grade);
//	}
//	
//	public String updateGrade(String stu_number,Double test_one,Double test_two,Double test_three,Double test_four,Double test_five,Integer test_six,Integer test_seven,Double grade) {
//		return this.examinationDao.updateGrade(stu_number, test_one, test_two, test_three, test_four, test_five, test_six, test_seven, grade);
//	}
}
