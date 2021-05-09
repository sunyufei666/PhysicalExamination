package com.controller;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.service.StudentInformationService;

@RestController
@RequestMapping(value="/studentController")
public class StudentInformationController {

	
	@Resource
	private StudentInformationService studentInformationService;
	
	@Resource
	private ServletContext servletContext;
	
	@ResponseBody
	@RequestMapping(value="/getPicture",produces="text/html;charset=UTF-8")
	public String getPicture(HttpServletRequest request) throws Exception {
		System.out.println("上传头像");
		String path = servletContext.getRealPath("")+"avatar\\";
		System.out.println(path);
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items= upload.parseRequest(request);
        FileItem item = items.get(0);
    	item.write(new File(path+item.getName()));
   		return "success";   
	}
	@RequestMapping(value="/renamePicture",produces="text/html;charset=UTF-8")
	public String information(@RequestParam("editBefore") String editBefore,@RequestParam("editAfter") String editAfter) {
		String path = servletContext.getRealPath("")+"avatar\\";
		System.out.println(editBefore+editAfter);
		File file=new File(path+editBefore);
		File file2=new File(path+editAfter);
		file.renameTo(file2);
		System.out.println("更改图片成功");
		return "success";
	}
	@RequestMapping(value="/deletePicture",produces="text/html;charset=UTF-8")
	public String delete(@RequestParam("fileName") String fileName) {
		String path = servletContext.getRealPath("")+"avatar\\";
		File file=new File(path+fileName);
		file.delete();
		System.out.println("删除图片成功");
		return "success";
	}
	@RequestMapping(value="/editInformation",produces="text/html;charset=UTF-8")
	public String information(@RequestParam("sno") String sno,@RequestParam("type") String type,
			@RequestParam("param") String param) {
		System.out.println("操作的账号："+sno+",进行更改的参数："+type+",值："+param);
		return this.studentInformationService.information(sno, type, param);
	}
	
	@RequestMapping(value="/getAllStudent",produces="text/html;charset=UTF-8")
	public String returnAllStudent() {
		return this.studentInformationService.returnAllStudent();
	}
	
	@RequestMapping(value="/getStudentByType",produces="text/html;charset=UTF-8")
	public String returnExamByType(@RequestParam("type") String type,@RequestParam("value")String value) {
		System.out.println("模糊查询的类型："+type+",值为："+value);
		return this.studentInformationService.returnStudentByType(type, value);
	}
	
	@RequestMapping(value="/updateStudent",produces="text/html;charset=UTF-8")
	public String updateExam(@RequestParam("id")Integer id,@RequestParam(value="sno",required =false)String sno,@RequestParam(value="university",required =false)String university,
			@RequestParam(value="college",required =false)String college,@RequestParam(value="name",required =false)String name,@RequestParam(value="grade",required =false)String grade
			,@RequestParam(value="classes",required =false)String classes,@RequestParam(value="introduction",required =false)String introduction,@RequestParam(value="major",required =false)String major
			,@RequestParam(value="nickname",required =false)String nickname,@RequestParam(value="sex",required =false)String sex) {
		System.out.println("更新id为"+id+"的学生信息sno:"+sno+",university："+university+",college："+college+",name："+name+",grade："+grade+",classes："+classes
				+",introduction："+introduction+",major："+major+",nickname："+nickname+",sex："+sex);
		return this.studentInformationService.updateStudent(id, sno, university, college, name, grade, classes, introduction, major, nickname, sex);
	}
	
	@RequestMapping(value="/deleteStudent",produces="text/html;charset=UTF-8")
	public String deleteStudent(@RequestParam("id")Integer id) {
		System.out.println("删除学生id："+id);
		return this.studentInformationService.deleteStudent(id);
	}
	
	@RequestMapping(value="/insertStudent",produces="text/html;charset=UTF-8")
	public String updateExam(@RequestParam(value="sno",required =false)String sno,@RequestParam(value="password",required =false)String password,@RequestParam(value="university",required =false)String university,
			@RequestParam(value="college",required =false)String college,@RequestParam(value="name",required =false)String name,@RequestParam(value="grade",required =false)String grade
			,@RequestParam(value="classes",required =false)String classes,@RequestParam(value="register_time",required =false)String register_time,@RequestParam(value="major",required =false)String major
			,@RequestParam(value="nickname",required =false)String nickname,@RequestParam(value="sex",required =false)String sex) {
		System.out.println("插入学生信息sno:"+sno+",university："+university+",college："+college+",name："+name+",grade："+grade+",classes："+classes
				+",major："+major+",nickname："+nickname+",sex："+sex);
		return this.studentInformationService.insertStudent(sno, password, university, college, name, grade, classes, register_time, major, nickname, sex);
	}
}
