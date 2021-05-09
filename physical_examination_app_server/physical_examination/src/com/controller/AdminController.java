package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.AdminService;
import com.service.ExamService;
import com.service.ProverbService;

@RestController
@RequestMapping(value="/adminController")
public class AdminController {

	
	@Resource
	private AdminService adminService;
	@Resource 
	private ProverbService proverbService;
	
	@RequestMapping(value="/editAdmin",produces="text/html;charset=UTF-8")
	public String editAdmin(@RequestParam("id") Integer id,@RequestParam("ano") String ano,
			@RequestParam("nickname") String nickname,@RequestParam("introduction") String introduction) {
		System.out.println("修改管理员id："+id+"，账号："+ano+"，昵称："+nickname+",简介："+introduction);
		return this.adminService.editAdmin(id, ano, nickname, introduction);
	}
	
	@RequestMapping(value="/getProverb",produces="text/html;charset=UTF-8")
	public String findGrade() {
		System.out.println("获取随机一个谚语");
		return this.proverbService.randomProverb();
	}
}
