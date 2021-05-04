package com.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.From;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Exam;
import com.entity.StudentExam;
import com.google.gson.Gson;

@Repository
public class ExamDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String p(Integer student_id) {
		String TimeNow = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		System.out.println(TimeNow);
		Session session=sessionFactory.getCurrentSession();
		String hql="from Exam where id not in (select exam_id from StudentExam where student_id =?) and current_people <80 and exam_date > "+"'"+TimeNow+"'";
	    Query query=session.createQuery(hql);
	    query.setParameter(0, student_id);
	    List<Map> exams=(List<Map>) query.list();
	    System.out.println(exams);
		return new Gson().toJson(exams);
	}
	
	public String returnAllExam() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Exam");
		List<Map> exams=(List<Map>) query.list();
		if(exams.isEmpty())return "fail";
		else {
			System.out.println("返回所有考试成功");
			return new Gson().toJson(exams);
		} 	
	}
	public String returnExamByType(String type,String value) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Exam where "+type+" like ?");
		query.setParameter(0, "%"+value+"%");
		List<Map> exams=(List<Map>) query.list();
		try {
			System.out.println("模糊查询"+type+"返回成绩数据成功");
			System.out.println();
			return new Gson().toJson(exams);
		} catch (Exception e) {
			System.out.println("返回成绩数据失败");
			System.out.println();
			return "";
		
		}
	}
	
	public String insertExam(String place,String all_people,String exam_date,String public_date,String teacher) {
		Session session=sessionFactory.getCurrentSession();
		Exam exam=new Exam();
		exam.setPlace(place);
		exam.setAll_people(all_people);
		exam.setExam_date(exam_date);
		exam.setPublic_date(public_date);
		exam.setTeacher(teacher);
		try {
			session.save(exam);
			System.out.println("插入考试信息成功");
			return "success";
		} catch (Exception e) {
			System.out.println("插入考试信息失败");
			return "fail";
		}
	}
	
	public String deleteExam(Integer id) {
		Session session=sessionFactory.getCurrentSession();
		Query query2=session.createQuery("from Exam where id="+id);
		Exam exam=(Exam) query2.uniqueResult();
		if(exam!=null) {
			Query query=session.createQuery("delete from Exam where id=?");
			query.setParameter(0, id);
			int n=query.executeUpdate();
			if(n>0) {
				System.out.println("删除考试信息成功");
				Session session1=sessionFactory.getCurrentSession();
				Query query3=session1.createQuery("From StudentExam where exam_id="+id);
				List<StudentExam> studentExams=query3.list();
				System.out.println(studentExams);
				if(studentExams.size()!=0) {
					Query query1=session1.createQuery("delete from StudentExam where exam_id=?");
					query1.setParameter(0, id);
					int n1=query1.executeUpdate();
					if(n1>0) {
						System.out.println("删除考试关联学生信息成功");
						return "success";
					}else {
						System.out.println("删除考试关联学生信息失败");
						return "success";
					}
				}else {
					System.out.println("没有找到考试的关联学生信息");
					return "success";
				}
			}else {
				System.out.println("删除考试信息失败");
				return "fail";
			}
		}else {
			System.out.println("没有找到要删除的考试信息id");
			return "fail";
		}
		
	}
	
	public String updateExam(Integer id,String place,String all_people,String exam_date,String teacher) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Exam where id=?");
		query.setParameter(0, id);
		Exam exam=(Exam) query.uniqueResult();
		if(place!=null) {exam.setPlace(place);}
		if(all_people!=null) {exam.setAll_people(all_people);}
		if(exam_date!=null) {exam.setExam_date(exam_date);}
		if(teacher!=null) {exam.setTeacher(teacher);}
		try {
			session.save(exam);
			System.out.println("更新考试信息成功");
			return "success";
		} catch (Exception e) {
			System.out.println("更新考试信息失败");
			return "fail";
		}
	}
	
	
//	public String insertGrade(String stu_number,Double test_one,Double test_two,Double test_three,Double test_four,Double test_five,Integer test_six,Integer test_seven,Double grade) {
//		Session session=sessionFactory.getCurrentSession();
//		Query query=session.createQuery("from Examination where stu_number=?");
//		query.setParameter(0, stu_number);
//		Exam examination=(Exam) query.uniqueResult();
//		examination.setTest_one(test_one);
//		examination.setTest_two(test_two);
//		examination.setTest_three(test_three);
//		examination.setTest_four(test_four);
//		examination.setTest_five(test_five);
//		examination.setTest_six(test_six);
//		examination.setTest_seven(test_seven);
//		examination.setGrade(grade);
//		session.save(examination);
//		try {
//			System.out.println("插入账号"+stu_number+"成绩成功");
//			return "success";
//		} catch (Exception e) {
//			// TODO: handle exception
//			return "fail";
//		}
//		
//	}
//	
//	public String updateGrade(String stu_number,Double test_one,Double test_two,Double test_three,Double test_four,Double test_five,Integer test_six,Integer test_seven,Double grade) {
//		Session session=sessionFactory.getCurrentSession();
//		Query query=session.createQuery("from Examination where stu_number=?");
//		query.setParameter(0, stu_number);
//		Exam examination=(Exam) query.uniqueResult();
//		if(test_one!=0) {examination.setTest_one(test_one);}
//		if(test_two!=0) {examination.setTest_two(test_two);}
//		if(test_three!=0) {examination.setTest_three(test_three);}
//		if(test_four!=0) {examination.setTest_four(test_four);}
//		if(test_five!=0) {examination.setTest_five(test_five);}
//		if(test_six!=0) {examination.setTest_six(test_six);}
//		if(test_seven!=0) {examination.setTest_seven(test_seven);}
//		if(grade!=0) {examination.setGrade(grade);}
//		session.save(examination);
//		try {
//			System.out.println("修改账号"+stu_number+"成绩成功");
//			return "success";
//		} catch (Exception e) {
//			// TODO: handle exception
//			return "fail";
//		}
//		
//	}
}
