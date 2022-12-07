package ua.com.foxminded.springbootjdbcapi.task22.models;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students_courses")
public class StudentCourse implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StudentCoursePK id;

	StudentCourse() {
	}

	public StudentCourse(StudentCoursePK id) {

		this.id = id;

	}

	public StudentCoursePK getId() {

		return id;

	}

	public void setId(StudentCoursePK id) {

		this.id = id;

	}

	@Override
	public String toString() {
		return "StudentCourse [studentId=" + id + "]";
	}

}