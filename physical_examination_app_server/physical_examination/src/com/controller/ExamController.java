package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.ExamService;
import com.service.LoginService;

@RestController
@RequestMapping(value="/examController")
public class ExamController {

	@Resource
	private ExamService examinationService;
	
	@RequestMapping(value="/getReserveExam",produces="text/html;charset=UTF-8")
	public String findGrade(@RequestParam("student_id") Integer student_id) {
		System.out.println("获取当前日期之后的考试");
		return this.examinationService.p(student_id);
	}
	
	
	@RequestMapping(value="/getAllExam",produces="text/html;charset=UTF-8")
	public String returnAllExam() {
		return this.examinationService.returnAllExam();
	}
	
	@RequestMapping(value="/getExamByType",produces="text/html;charset=UTF-8")
	public String returnExamByType(@RequestParam("type") String type,@RequestParam("value")String value) {
		System.out.println("模糊查询的类型："+type+",值为："+value);
		return this.examinationService.returnExamByType(type, value);
	}

	@RequestMapping(value="/insertExam",produces="text/html;charset=UTF-8")
	public String insertExam(@RequestParam(value="place",required =false)String place,@RequestParam(value="all_people",required =false)String all_people,
			@RequestParam(value="exam_date",required =false)String exam_date,@RequestParam(value="public_date",required =false)String public_date,
			@RequestParam(value="teacher",required =false)String teacher) {
		System.out.println("插入考试信息place:"+place+",all_people："+all_people+",exam_date："+exam_date+",public_date："+public_date+",teacher："+teacher);
		return this.examinationService.insertExam(place, all_people, exam_date, public_date, teacher);
	}
	
	@RequestMapping(value="/deleteExam",produces="text/html;charset=UTF-8")
	public String insertExam(@RequestParam(value="id")Integer id) {
		System.out.println("删除考试信息id:"+id);
		return this.examinationService.deleteExam(id);
	}
	
	@RequestMapping(value="/updateExam",produces="text/html;charset=UTF-8")
	public String updateExam(@RequestParam("id")Integer id,@RequestParam(value="place",required =false)String place,@RequestParam(value="all_people",required =false)String all_people,
			@RequestParam(value="exam_date",required =false)String exam_date,@RequestParam(value="teacher",required =false)String teacher) {
		System.out.println("更新id为"+id+"的考试信息place:"+place+",all_people："+all_people+",exam_date："+exam_date+",public_date："+teacher);
		return this.examinationService.updateExam(id, place, all_people, exam_date, teacher);
	}
//	@RequestMapping(value="/insertGrade",produces="text/html;charset=UTF-8")
//	public String insertGrade(@RequestParam("username") String stu_number,@RequestParam(value="test_one",required =false)Double test_one,
//			@RequestParam(value="test_two",required =false)Double test_two,@RequestParam(value="test_three",required =false)Double test_three,
//			@RequestParam(value="test_four",required =false)Double test_four,@RequestParam(value="test_five",required =false)Double test_five,
//			@RequestParam(value="test_six",required =false)Integer test_six,@RequestParam(value="test_seven",required =false)Integer test_seven,
//			@RequestParam(value="grade",required =false)Double grade) {
//		System.out.println("插入用户"+stu_number+"，50米："+test_one+"体前屈："+test_two+"肺活量："+test_three+"一千米："+test_four+"八百米："+test_five+
//				"仰卧起坐："+test_six+"引体向上："+test_seven+"成绩："+grade);
//		return this.examinationService.insertGrade(stu_number,test_one, test_two, test_three, test_four, test_five, test_six, test_seven, grade);
//	}
//	@RequestMapping(value="/updateGrade",produces="text/html;charset=UTF-8")
//	public String updateGrade(@RequestParam("username") String stu_number,@RequestParam(value="test_one",required =false)Double test_one,
//			@RequestParam(value="test_two",required =false)Double test_two,@RequestParam(value="test_three",required =false)Double test_three,
//			@RequestParam(value="test_four",required =false)Double test_four,@RequestParam(value="test_five",required =false)Double test_five,
//			@RequestParam(value="test_six",required =false)Integer test_six,@RequestParam(value="test_seven",required =false)Integer test_seven,
//			@RequestParam(value="grade",required =false)Double grade) {
//		System.out.println("修改用户"+stu_number+"，50米："+test_one+"体前屈："+test_two+"肺活量："+test_three+"一千米："+test_four+"八百米："+test_five+
//				"仰卧起坐："+test_six+"引体向上："+test_seven+"成绩："+grade);
//		return this.examinationService.insertGrade(stu_number,test_one, test_two, test_three, test_four, test_five, test_six, test_seven, grade);
//	}
}
