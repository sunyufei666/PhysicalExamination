package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.StudentExamService;

@RestController
@RequestMapping(value="/studentExamController")
public class StudentExamController {

	@Resource
	private StudentExamService studentExamService;
	
	@RequestMapping(value="/getStudentExamInfo",produces="text/html;charset=UTF-8")
	public String examDetail(@RequestParam("id")Integer id) {
		System.out.println("学生id："+id+"并返回所有考试和学生关联信息");
		String aString=this.studentExamService.examDetail(id);
		return aString;
	}
	
	@RequestMapping(value="/getAllStudentExamInfo",produces="text/html;charset=UTF-8")
	public String examDetails() {
		System.out.println("返回所有考试和学生关联信息");
		String aString=this.studentExamService.examDetails();
		return aString;
	}
	
	
	@RequestMapping(value="/getStudentExamInfoByType",produces="text/html;charset=UTF-8")
	public String returnFor(@RequestParam("id")Integer id,@RequestParam("type") String type
			,@RequestParam("value") String value) {
		System.out.println("学生id："+id+",模糊查询类型："+type+"，值："+value);
		
		return this.studentExamService.returnFor(id, type, value);
	}
	
	@RequestMapping(value="/studentReserveExam",produces="text/html;charset=UTF-8")
	public String returnFor(@RequestParam("student_id")Integer student_id,@RequestParam("exam_id") Integer exam_id) {
		System.out.println("学生id："+student_id+",考试id："+exam_id);
		
		return this.studentExamService.a(student_id, exam_id);
	}
	
	@RequestMapping(value="/updateStudentExam",produces="text/html;charset=UTF-8")
	public String update(@RequestParam("id")Integer id,@RequestParam(value="if_pass",required=false) String if_pass,@RequestParam(value="stand_jump",required=false) String stand_jump,@RequestParam(value="fifty_meter",required=false) String fifty_meter,
			@RequestParam(value="eighthundred_meter",required=false)String eighthundred_meter,@RequestParam(value="onethousand_meter",required=false) String onethousand_meter,
			@RequestParam(value="pull_up",required=false) String pull_up,@RequestParam(value="sitting_forward",required=false) String sitting_forward,@RequestParam(value="sit_up",required=false) String sit_up,
			@RequestParam(value="vital_capacity",required=false) String vital_capacity,@RequestParam(value="sex",required=false) String sex) {
		return this.studentExamService.update(id, if_pass, stand_jump, fifty_meter, eighthundred_meter, onethousand_meter, pull_up, sitting_forward, sit_up, vital_capacity, sex);
	}
}
