package com.service;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.AdminDao;


@Service
@Transactional(readOnly=false)
public class AdminService {
	
	@Resource
	private AdminDao adminDao;
	
	public String editAdmin(Integer id,String ano,String nickname,String introduction) {
		return this.adminDao.editAdmin(id, ano, nickname, introduction);
	}
}
