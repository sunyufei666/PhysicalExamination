package com.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Examination;
import com.google.gson.Gson;

@Repository
public class ExaminationDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String findGrade(String user_number) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Examination where stu_number=?");
		query.setParameter(0, user_number);
		List<Map> examinations=(List<Map>) query.list();
//		String a="";
//		List<Examination> examinations=(List<Examination>) query.list();
//		for(Examination examination:examinations) {
//			a+=examination.getTest_one()+","+examination.getTest_two()+","+examination.getTest_three()+","+examination.getTest_four()+","+examination.getTest_five()+","+examination.getTest_six()+","+examination.getTest_seven()+","+examination.getGrade();
//		}
		if(examinations.isEmpty())return "";
		else return new Gson().toJson(examinations);
		
	}
	public String insertGrade(String user_number) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Examination where stu_number=?");
		query.setParameter(0, user_number);
		Examination examination=(Examination) query.uniqueResult();
		//examination;
		return "";
	}
}
