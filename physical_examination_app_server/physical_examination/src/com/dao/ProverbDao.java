package com.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Proverb;
import com.google.gson.Gson;

@Repository
public class ProverbDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String randomProverb() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Proverb order by rand()");
		query.setMaxResults(1);
		System.out.println((Proverb)query.uniqueResult());
		return new Gson().toJson((Proverb)query.uniqueResult()) ;
	}
}
