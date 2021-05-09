package com.service;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.NewsDao;
import com.entity.News;
import com.entity.StudentNews;

@Service
@Transactional(readOnly=false)
public class NewsService {

	@Resource
	private NewsDao newsDao;
	
	public String look() {
		return this.newsDao.look();
	}
	
	public String insertNews(String news_content,String news_date) {
		return this.newsDao.insertNews(news_content, news_date);
	}
	
	public String insertStudentNews(Integer student_id,Integer news_id) {
		return this.newsDao.insertStudentNews(student_id, news_id);
	}
	public String updateNews(Integer id,String news_content) {
		return this.newsDao.updateNews(id, news_content);
	}
	public String deleteNews(Integer id) {
		return this.newsDao.deleteNews(id);
	}
	public String a(Integer student_id) {
		return this.newsDao.a(student_id);
	}
}
