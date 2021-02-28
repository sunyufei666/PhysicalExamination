package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.ExaminationService;
import com.service.LoginService;

@RestController
@RequestMapping(value="/ExaminationServlet")
public class ExaminationController {

	@Resource
	private ExaminationService examinationService;
	
	@RequestMapping(value="/userGrade",produces="text/html;charset=UTF-8")
	public String findGrade(@RequestParam("username") String stu_number) {
		System.out.println("查询成绩的账户"+stu_number);
		return this.examinationService.findGrade(stu_number);
	}
	
}
