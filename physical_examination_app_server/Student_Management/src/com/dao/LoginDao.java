package com.dao;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Admin;
import com.entity.Student;
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
			Query query=session.createQuery("from Student where stu_number=?");
			query.setParameter(0, stu_number);
			Student student=(Student) query.uniqueResult();
			if(student!=null) {
				if (stu_password.equals(student.getStu_password())) {
					System.out.println("密码一致");
					System.out.println();
					return "success";
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
			Query query=session.createQuery("from Admin where admin_number=?");
			query.setParameter(0, stu_number);
			Admin admin=(Admin) query.uniqueResult();
			if(admin!=null) {
				if (stu_password.equals(admin.getAdmin_password())) {
					System.out.println("密码一致");
					System.out.println();
					return "success";
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
	
}
