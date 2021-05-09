package com.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.From;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Exam;
import com.entity.Student;
import com.entity.StudentExam;
import com.google.gson.Gson;

@Repository
public class StudentExamDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String examDetail(Integer id) {
		Session session=sessionFactory.getCurrentSession();
		String hql="from StudentExam se ,Exam e where student_id=? AND se.exam_id=e.id";
	    Query query=session.createQuery(hql);
		query.setParameter(0,id);
		//Query query=session.createQuery("from StudentExam where student_id=?");
//		Query query=session.createQuery("");
//		query.setParameter(0, id);
		List<Map> studentExams=(List<Map>) query.list();
		try {
			System.out.println("返回成绩数据成功");
			System.out.println();
			return new Gson().toJson(studentExams);
		} catch (Exception e) {
			System.out.println("返回成绩数据失败");
			System.out.println();
			return "";
		}
	}
	
	public String examDetails() {
		Session session=sessionFactory.getCurrentSession();
		String hql="from StudentExam se ,Exam e where se.exam_id=e.id";
	    Query query=session.createQuery(hql);
		//Query query=session.createQuery("from StudentExam where student_id=?");
//		Query query=session.createQuery("");
//		query.setParameter(0, id);
		List<Map> studentExams=(List<Map>) query.list();
		try {
			System.out.println("返回成绩数据成功");
			System.out.println();
			return new Gson().toJson(studentExams);
		} catch (Exception e) {
			System.out.println("返回成绩数据失败");
			System.out.println();
			return "";
		}
	}
	public String returnFor(Integer id,String type,String value) {
		Session session=sessionFactory.getCurrentSession();
		String hql="from StudentExam se ,Exam e where student_id=? AND se.exam_id=e.id AND "+type+" like ?";
	    Query query=session.createQuery(hql);
		query.setParameter(0,id);
		query.setParameter(1, "%"+value+"%");
		List<Map> studentExams=(List<Map>) query.list();
		try {
			System.out.println("模糊查询"+type+"返回成绩数据成功");
			System.out.println();
			return new Gson().toJson(studentExams);
		} catch (Exception e) {
			System.out.println("返回成绩数据失败");
			System.out.println();
			return "";
		
		}
	}
	public String a(Integer student_id,Integer exam_id) {
		Session session=sessionFactory.getCurrentSession();
//		String hql="insert into StudentExam (student_id,exam_id) values ("+student_id+","+exam_id+")";
//	    Query query=session.createQuery(hql);
		StudentExam studentExam=new StudentExam();
		studentExam.setStudent_id(student_id);
		studentExam.setExam_id(exam_id);
		
		try {
			session.save(studentExam);
			System.out.println("插入学生考试信息成功");
			Session session1=sessionFactory.getCurrentSession();
		    Query query1=session1.createQuery("from Exam where id=?");
		    query1.setParameter(0, exam_id);
		    Exam exam=(Exam) query1.uniqueResult();
		    System.out.println(exam);
		    Integer aInteger=Integer.parseInt(exam.getCurrent_people());
		    aInteger+=1;
		    exam.setCurrent_people(""+aInteger);
		    try {
		    	session1.save(exam);
		    	System.out.println("当前人数更新完毕");
		    	return "success";
			} catch (Exception e) {
				System.out.println("当前人数更新失败");
				return "fail";
			}
		} catch (Exception e) {
			System.out.println("插入学生考试信息失败");
			return "fail";
		}
	}
	public String update(Integer id,String if_pass,String stand_jump,String fifty_meter,String eighthundred_meter,String onethousand_meter,
			String pull_up,String sitting_forward,String sit_up,String vital_capacity,String sex) {
		Session session=sessionFactory.getCurrentSession();
		if(sex.equals("男")) {
			Query query=session.createQuery("from StudentExam where id="+id);
			StudentExam studentExam=(StudentExam) query.uniqueResult();
			if(if_pass!=null) {studentExam.setIf_pass(if_pass);}
			if(stand_jump!=null) {studentExam.setStand_jump(stand_jump);}
			if(fifty_meter!=null) {studentExam.setFifty_meter(fifty_meter);}
			if(onethousand_meter!=null) {studentExam.setOnethousand_meter(onethousand_meter);}
			if(pull_up!=null) {studentExam.setPull_up(pull_up);}
			if(sitting_forward!=null) {studentExam.setSitting_forward(sitting_forward);}
			if(vital_capacity!=null) {studentExam.setVital_capacity(vital_capacity);}
			try {
				session.save(studentExam);
				System.out.println("更新成绩成功");
				return "success";
				
			} catch (Exception e) {
				System.out.println("更新成绩失败");
				return "fail";
			}
		}else if(sex.equals("女")){
			Query query=session.createQuery("from StudentExam where id="+id);
			StudentExam studentExam=(StudentExam) query.uniqueResult();
			if(if_pass!=null) {studentExam.setIf_pass(if_pass);}
			if(stand_jump!=null) {studentExam.setStand_jump(stand_jump);}
			if(fifty_meter!=null) {studentExam.setFifty_meter(fifty_meter);}
			if(eighthundred_meter!=null) {studentExam.setEighthundred_meter(eighthundred_meter);}
			if(sit_up!=null) {studentExam.setSit_up(sit_up);}
			if(sitting_forward!=null) {studentExam.setSitting_forward(sitting_forward);}
			if(vital_capacity!=null) {studentExam.setVital_capacity(vital_capacity);}
			try {
				session.save(studentExam);
				System.out.println("更新成绩成功");
				return "success";
				
			} catch (Exception e) {
				System.out.println("更新成绩失败");
				return "fail";
			}
		}
		return "";
		
	}
}
