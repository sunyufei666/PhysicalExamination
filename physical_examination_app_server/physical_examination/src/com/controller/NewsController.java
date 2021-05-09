package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.NewsService;

@RestController
@RequestMapping(value="/newsController")
public class NewsController {

	@Resource
	private NewsService newsService;
	
	@RequestMapping(value="/getAllNews",produces="text/html;charset=UTF-8")
	public String getNews() {
		System.out.println("返回所有信息");
		return this.newsService.look();
	}
	
	@RequestMapping(value="/insertNews",produces="text/html;charset=UTF-8")
	public String insertNews(@RequestParam("news_content") String news_content,@RequestParam("news_date") String news_date) {
		System.out.println("插入消息的news_content："+news_content+",news_date:"+news_date);
		return this.newsService.insertNews(news_content, news_date);
	}
	@RequestMapping(value="/viewNews",produces="text/html;charset=UTF-8")
	public String insertStudentNews(@RequestParam("student_id") Integer student_id,@RequestParam("news_id") Integer news_id) {
		System.out.println("插入学生的student_id:"+student_id+",news_id:"+news_id);
		return this.newsService.insertStudentNews(student_id, news_id);
	}
	@RequestMapping(value="/updateNews",produces="text/html;charset=UTF-8")
	public String updateNews(@RequestParam("id") Integer id,@RequestParam("news_content") String news_content) {
		System.out.println("更新消息的id:"+id+",news_content："+news_content);
		return this.newsService.updateNews(id, news_content);
	}
	@RequestMapping(value="/deleteNews",produces="text/html;charset=UTF-8")
	public String deleteNews(@RequestParam("id") Integer id) {
		System.out.println("删除消息的id:"+id);
		return this.newsService.deleteNews(id);
	}
	@RequestMapping(value="/getNewsByStudentId",produces="text/html;charset=UTF-8")
	public String a(@RequestParam("student_id") Integer student_id) {
		System.out.println("student_id为"+student_id+"的if_view改1");
		return this.newsService.a(student_id);
	}
}
