package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.ProverbService;

@RestController
public class ProverbController {

//	@Resource 
//	private ProverbService proverbService;
//	
//	@RequestMapping(value="/getProverb",produces="text/html;charset=UTF-8")
//	public String findGrade() {
//		System.out.println("获取随机一个谚语");
//		return this.proverbService.randomProverb();
//	}
}
