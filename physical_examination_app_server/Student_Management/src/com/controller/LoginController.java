package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.service.LoginService;

@RestController
@RequestMapping(value="/LoginServlet")
public class LoginController {

	@Resource
	private LoginService loginService;
	
	@RequestMapping(value="/userLogin",produces="text/html;charset=UTF-8")
	public String account(@RequestParam("username") String stu_number,@RequestParam("password") String stu_password,
			@RequestParam(value="role",required=false) String role) {
		System.out.println(stu_number+stu_password+role);
		return this.loginService.login(stu_number, stu_password,role);
	}
}
