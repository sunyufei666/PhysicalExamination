package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.LoginDao;

@Service
@Transactional(readOnly=false)
public class LoginService {

	@Resource
	private LoginDao loginDao;
	
	public String login(String stu_number,String stu_password,String role) {
		return this.loginDao.login(stu_number, stu_password,role);
		
	}
	public String editpassword(String stu_number,String stu_password) {
		return this.loginDao.editpassword(stu_number, stu_password);
	}
	public String matchpassword(String stu_number,String stu_password) {
		return this.loginDao.matchpassword(stu_number, stu_password);
	}
}
