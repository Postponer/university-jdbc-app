package ua.com.foxminded.springbootjdbcapi.task22.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentId;

	@Column(name = "GROUP_ID")
	private int groupId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "students_courses", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses = new ArrayList<>();

	public Student() {
	}

	public Student(int studentId, int groupId, String firstName, String lastName) {

		this.studentId = studentId;
		this.groupId = groupId;
		this.firstName = firstName;
		this.lastName = lastName;

	}

	public int getStudentId() {

		return studentId;

	}

	public void setStudentId(int studentId) {

		this.studentId = studentId;

	}

	public int getGroupId() {

		return groupId;

	}

	public void setGroupId(int groupId) {

		this.groupId = groupId;

	}

	public String getFirstName() {

		return firstName;

	}

	public void setFirstName(String firstName) {

		this.firstName = firstName;

	}

	public String getLastName() {

		return lastName;

	}

	public void setLastName(String lastName) {

		this.lastName = lastName;

	}

	public void addCourse(Course course) {

		courses.add(course);
		course.getStudents().add(this);

	}

	public void removeCourse(Course course) {

		courses.remove(course);
		course.getStudents().remove(this);

	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", groupId=" + groupId + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}

}