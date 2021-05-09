package com.dao;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Exam;
import com.entity.Student;
import com.entity.StudentExam;
import com.entity.StudentNews;
import com.google.gson.Gson;


@Repository
public class StudentInformationDao {

	@Resource
	private SessionFactory sessionFactory;
	
	
	public String information(String sno,String type,String param) {
		if(type.equals("nickname")) {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Student where sno=?");
			query.setParameter(0, sno);
			Student student=(Student) query.uniqueResult();
			student.setNickname(param);
			try {
				session.save(student);
				System.out.println("账号为："+sno+"的用户修改昵称成功");
				return "success";
			} catch (Exception e) {
				System.out.println("账号为："+sno+"的用户修改昵称失败");
				return "fail";
			}
		}else if(type.equals("introduction")) {
			Session session1=sessionFactory.getCurrentSession();
			Query query1=session1.createQuery("from Student where sno=?");
			query1.setParameter(0, sno);
			Student student1=(Student) query1.uniqueResult();
			student1.setIntroduction(param);
			try {
				session1.save(student1);
				System.out.println("账号为："+sno+"的用户修改简介成功");
				return "success";
			} catch (Exception e) {
				System.out.println("账号为："+sno+"的用户修改简介失败");
				return "fail";
			}
		}else if(type.equals("password")) {
			Session session2=sessionFactory.getCurrentSession();
			Query query2=session2.createQuery("from Student where sno=?");
			query2.setParameter(0, sno);
			Student student2=(Student) query2.uniqueResult();
			student2.setPassword(param);
			try {
				session2.save(student2);
				System.out.println("账号为："+sno+"的用户修改密码成功");
				return "success";
			} catch (Exception e) {
				System.out.println("账号为："+sno+"的用户修改密码失败");
				return "fail";
			}
		}
					
		return "";
		
	}
	
	public String returnAllStudent() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Student");
		List<Map> students=(List<Map>) query.list();
		if(students.isEmpty())return "fail";
		else {
			System.out.println("返回所有学生成功");
			return new Gson().toJson(students);
		} 	
	}
	public String returnStudentByType(String type,String value) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Student where "+type+" like ?");
		query.setParameter(0, "%"+value+"%");
		List<Map> students=(List<Map>) query.list();
		try {
			System.out.println("模糊查询"+type+"返回学生数据成功");
			System.out.println();
			return new Gson().toJson(students);
		} catch (Exception e) {
			System.out.println("返回学生数据失败");
			System.out.println();
			return "";
		
		}
	}
	
	public String updateStudent(Integer id,String sno,String university,String college,String name,String grade,String classes,
			String introduction,String major,String nickname,String sex) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Student where id=?");
		query.setParameter(0, id);
		Student student=(Student) query.uniqueResult();
		if(sno!=null) {student.setSno(sno);}
		if(university!=null) {student.setUniversity(university);}
		if(college!=null) {student.setCollege(college);}
		if(name!=null) {student.setName(name);}
		if(grade!=null) {student.setGrade(grade);}
		if(classes!=null) {student.setClasses(classes);}
		if(introduction!=null) {student.setIntroduction(introduction);}
		if(major!=null) {student.setMajor(major);}
		if(nickname!=null) {student.setNickname(nickname);}
		if(sex!=null) {student.setSex(sex);}
		try {
			session.save(student);
			System.out.println("更新学生信息成功");
			return "success";
		} catch (Exception e) {
			System.out.println("更新学生信息失败");
			return "fail";
		}
	}
	
	public String insertStudent(String sno,String password,String university,String college,String name,String grade,String classes,
			String register_time,String major,String nickname,String sex) {
		Session session=sessionFactory.getCurrentSession();
		Student student=new Student();
		student.setSno(sno);
		student.setPassword(password);
		student.setUniversity(university);
		student.setCollege(college);
		student.setName(name);
		student.setGrade(grade);
		student.setClasses(classes);
		student.setRegister_time(register_time);
		student.setMajor(major);
		student.setNickname(nickname);
		student.setSex(sex);
		try {
			session.save(student);
			System.out.println("插入学生信息成功");
			return "success";
		} catch (Exception e) {
			System.out.println("插入学生信息失败");
			return "fail";
		}
	}
	
	public String deleteStudent(Integer id) {
		Session session=sessionFactory.getCurrentSession();
		Query query3=session.createQuery("from Student where id="+id);
		Student student=(Student) query3.uniqueResult();
		if(student!=null) {
			Query query=session.createQuery("delete from Student where id=?");
			query.setParameter(0, id);
			int n=query.executeUpdate();
			if(n>0) {
				System.out.println("删除学生信息成功");
				Query q4=session.createQuery("from StudentExam where student_id="+id);
				List<StudentExam> studentExams=q4.list();
				if(studentExams.size()!=0) {
				    Query query2=session.createQuery("select e from StudentExam se ,Exam e where student_id=? AND se.exam_id=e.id");
				    query2.setParameter(0, id);
				    Exam exam=(Exam) query2.uniqueResult();
				    System.out.println(exam);
				    Integer aInteger=Integer.parseInt(exam.getCurrent_people());
				    aInteger-=1;
				    exam.setCurrent_people(""+aInteger);
				    System.out.println("考试表预约人数减一");
					Query query1=session.createQuery("delete from StudentExam where student_id=?");
					query1.setParameter(0, id);
					int n1=query1.executeUpdate();
					if(n1>0) {
						System.out.println("删除学生关联考试信息成功");
						
						return "success";
					}else {
						System.out.println("删除学生关联考试信息失败");
						return "success";
					}
				}else { 
					System.out.println("没有找到学生关联的考试信息");
					Query query2=session.createQuery("from StudentNews where student_id="+id);
					List<StudentNews> studentNews =query2.list();
					if(studentNews.size()!=0) {
						Query query4=session.createQuery("delete from StudentNews where student_id=?");
						query4.setParameter(0, id);
						int n1=query4.executeUpdate();
						if(n1>0) {
							System.out.println("删除学生关联消息成功");
							return "success";
						}else {
							System.out.println("删除学生关联消息失败");
							return "success";
						}
					}else {
						System.out.println("没有找到学生关联消息");
						return "success";
					}	
				}
				
			}else {
				System.out.println("删除考试信息失败");
				return "fail";
			}
		}else {
			System.out.println("没有找到要删除的学生id");
			return "fail";
		}
		
	}
}
