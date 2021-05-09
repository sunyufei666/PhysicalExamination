package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ProverbDao;

@Service
@Transactional(readOnly=false)
public class ProverbService {

	@Resource
	private ProverbDao proverbDao;
	
	public String randomProverb() {
		return this.proverbDao.randomProverb();
	}
}
