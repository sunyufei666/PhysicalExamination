package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ExaminationDao;

@Service
@Transactional(readOnly=false)
public class ExaminationService {

	@Resource
	private ExaminationDao examinationDao;
	
	public String findGrade(String user_number) {
		return this.examinationDao.findGrade(user_number);
	}
}
