package com.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Exam;
import com.entity.News;
import com.entity.StudentExam;
import com.entity.StudentNews;
import com.google.gson.Gson;

@Repository
public class NewsDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String look() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from News");
		List<Map> news= (List<Map>)query.list();
		return new Gson().toJson(news);
	}
	public String insertNews(String news_content,String news_date) {
		Session session=sessionFactory.getCurrentSession();
		News news=new News();
		news.setNews_content(news_content);
		news.setNews_date(news_date);
		try {
			session.save(news);
			System.out.println("插入消息成功");
			return "success";
		} catch (Exception e) {
			System.out.println("插入消息失败");
			return "fail";
		}	
	}
	public String insertStudentNews(Integer student_id,Integer news_id) {
		Session session=sessionFactory.getCurrentSession();
		StudentNews studentNews=new StudentNews();
		studentNews.setNews_id(news_id);
		studentNews.setStudent_id(student_id);
		try {
			session.save(studentNews);
			System.out.println("插入学生消息成功");
			return "success";
		} catch (Exception e) {
			System.out.println("插入学生消息失败");
			return "fail";
		}	
	}
	public String updateNews(Integer id,String news_content) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from News where id="+id);
		News news=(News) query.uniqueResult();
		news.setNews_content(news_content);
		try {
			session.save(news);
			System.out.println("更新消息成功");
			return "success";
		} catch (Exception e) {
			System.out.println("更新消息失败");
			return "fail";
		}	
	}
	public String deleteNews(Integer id) {
		Session session=sessionFactory.getCurrentSession();
		Query query2=session.createQuery("from News where id="+id);
		News news=(News) query2.uniqueResult();
		if(news!=null) {
			Query query=session.createQuery("delete from News where id=?");
			query.setParameter(0, id);
			int n=query.executeUpdate();
			if(n>0) {
				System.out.println("删除消息成功");
				Session session1=sessionFactory.getCurrentSession();
				Query query3=session1.createQuery("From StudentNews where news_id="+id);
				List<StudentExam> studentExams=query3.list();
				System.out.println(studentExams);
				if(studentExams.size()!=0) {
					Query query1=session1.createQuery("delete from StudentNews where news_id=?");
					query1.setParameter(0, id);
					int n1=query1.executeUpdate();
					if(n1>0) {
						System.out.println("删除消息关联学生信息成功");
						return "success";
					}else {
						System.out.println("删除消息关联学生信息失败");
						return "success";
					}
				}else {
					System.out.println("没有找到消息的关联学生信息");
					return "success";
				}
			}else {
				System.out.println("删除消息失败");
				return "fail";
			}
		}else {
			System.out.println("没有找到要删除的消息id");
			return "fail";
		}
	}
	public String a(Integer student_id) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("update News set if_view=0 where id not in(select sn.news_id from StudentNews sn where sn.student_id=?)");
		query.setParameter(0, student_id);
		Query query2=session.createQuery("update News set if_view=1 where id in(select sn.news_id from StudentNews sn where sn.student_id=?)");
		query2.setParameter(0, student_id);
		int m=query.executeUpdate();
		int n=query2.executeUpdate();
		if(n>0||m>0) {
			System.out.println("更新成功");
		}
		return look();
	}
}
