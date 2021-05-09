package com.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Admin;
import com.entity.Student;
import com.entity.StudentExam;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.RETURN;

@Repository
public class LoginDao {
	@Resource
	private SessionFactory sessionFactory;
	
	public String login(String stu_number,String stu_password,String role) {
		
		//Query query=session.createQuery("from Student where stu_number=?");
		//query.setParameter(0, name);
		//System.out.println(account);
//		if((Account) query.uniqueResult()!=null) {
//			System.out.println("添加用户失败,用户已存在");
//			return "0";
//		}else {
//			try {
		if(role.equals("student")) {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Student where sno=?");
			query.setParameter(0, stu_number);
			Student student=(Student) query.uniqueResult();
			List<Map> students=(List<Map>) query.list();
			if(student!=null) {
				if (stu_password.equals(student.getPassword())) {
					System.out.println("密码一致");
					System.out.println();
					return new Gson().toJson(students);
				}else {
					System.out.println("密码不一致");
					return "fail";

				}
			}else {
				System.out.println("没有此用户");
				return "fail";
			}
		}else if(role.equals("admin")) {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Admin where ano=?");
			query.setParameter(0, stu_number);
			Admin admin=(Admin) query.uniqueResult();
			List<Map> admins=(List<Map>)query.list();
			if(admin!=null) {
				if (stu_password.equals(admin.getPassword())) {
					System.out.println("密码一致");
					System.out.println();
					return new Gson().toJson(admins);
				}else {
					System.out.println("密码不一致");
					return "fail";

				}
			}else {
				System.out.println("没有此用户");
				return "fail";
			}
		}
		return "";
		
		}
	public String editpassword(String stu_number,String stu_password) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Student where password=?");
		query.setParameter(0, stu_number);
		Student student=(Student) query.uniqueResult();
		student.setPassword(stu_password);
		session.save(student);
		try {
			System.out.println("用户："+stu_number+"修改密码成功,修改后的密码为"+stu_password);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			return "fail";
		}
		
	}
	
	public String matchpassword(String stu_number,String stu_password) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Student where password=?");
		query.setParameter(0, stu_number);
		Student student=(Student) query.uniqueResult();
		if(student.getPassword().equals(stu_password)) {
			System.out.println("账号"+stu_number+"输入的密码一致");
			return "success";
		}else {
			System.out.println("账号"+stu_number+"输入的密码不一致");
			return "fail";
		}
		
		
	}
}
