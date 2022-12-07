package ua.com.foxminded.springbootjdbcapi.task22.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseId;

	@Column(name = "COURSE_NAME")
	@NaturalId
	private String courseName;

	@Column(name = "COURSE_DESCRIPTION")
	private String courseDescription;

	@ManyToMany(mappedBy = "courses")
	private List<Student> students = new ArrayList<>();

	public Course() {}

	public Course(int courseId, String courseName, String courseDescription) {

		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDescription = courseDescription;

	}

	public int getCourseId() {

		return courseId;

	}

	public void setCourseId(int courseId) {

		this.courseId = courseId;

	}

	public String getCourseName() {

		return courseName;

	}

	public void setCourseName(String courseName) {

		this.courseName = courseName;

	}

	public String getCourseDescription() {

		return courseDescription;

	}

	public void setCourseDescription(String courseDescription) {

		this.courseDescription = courseDescription;

	}

	public List<Student> getStudents() {
		
		return students;
		
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", courseDescription="
				+ courseDescription + "]";
	}

}