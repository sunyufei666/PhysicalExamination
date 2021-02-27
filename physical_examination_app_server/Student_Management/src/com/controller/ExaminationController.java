package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.ExaminationService;
import com.service.LoginService;

@RestController
@RequestMapping(value="/ExaminationServlet")
public class ExaminationController {

	@Resource
	private ExaminationService examinationService;
	
	
}
