package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.service.LoginService;

@RestController
@RequestMapping(value="/loginController")
public class LoginController {

	@Resource
	private LoginService loginService;
	
	@RequestMapping(value="/userLogin",produces="text/html;charset=UTF-8")
	public String account(@RequestParam("username") String stu_number,@RequestParam("password") String stu_password,
			@RequestParam(value="role",required=false) String role) {
		System.out.println("登录的账号："+stu_number+"密码："+stu_password+"身份："+role);
		return this.loginService.login(stu_number, stu_password,role);
	}
}
