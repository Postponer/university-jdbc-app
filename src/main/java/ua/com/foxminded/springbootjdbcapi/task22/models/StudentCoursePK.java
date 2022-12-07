package ua.com.foxminded.springbootjdbcapi.task22.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StudentCoursePK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Column(name="STUDENT_ID")
    private int studentId;
    
    @Column(name="COURSE_ID")
    private int courseId;
    
    public StudentCoursePK() {}

	public StudentCoursePK(int studentId, int courseId) {

		this.studentId = studentId;
		this.courseId = courseId;
		
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

}