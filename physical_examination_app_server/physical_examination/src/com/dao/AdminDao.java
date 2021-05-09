package com.dao;



import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Admin;

@Repository
public class AdminDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String editAdmin(Integer id,String ano,String nickname,String introduction) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Admin where id=?");
		query.setParameter(0, id);
		Admin admin=(Admin) query.uniqueResult();
		if(ano!=null) {admin.setAno(ano);}
		if(nickname!=null) {admin.setNickname(nickname);}
		if(introduction!=null) {admin.setIntroduction(introduction);}
		try {
			session.save(admin);
			System.out.println("修改管理员信息成功");
			return "success";
		} catch (Exception e) {
			System.out.println("修改管理员信息失败");
			return "fail";
		}
	}
}
