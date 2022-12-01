package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcStudentDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;
import ua.com.foxminded.springbootjdbcapi.task22.models.StudentCourse;
import ua.com.foxminded.springbootjdbcapi.task22.rowmappers.StudentCourseRowMapper;

@Service
public class StudentService {

	private JdbcStudentDao studentDao;
	private JdbcTemplate jdbcTemplate;
	private StudentCourseRowMapper studentCourseRowMapper;
	private Scanner scanner = new Scanner(System.in);
	Logger logger = LoggerFactory.getLogger(StudentService.class);

	public StudentService(JdbcStudentDao studentDao, JdbcTemplate jdbcTemplate,
			StudentCourseRowMapper studentCourseRowMapper) {

		this.studentDao = studentDao;
		this.jdbcTemplate = jdbcTemplate;
		this.studentCourseRowMapper = studentCourseRowMapper;

	}

	public Student getByGroupId(int groupId) {

		logger.debug("Entering get student by group id endpoint");
		Student student = studentDao.getByGroupId(groupId).get();
		logger.info(student.toString() + " has been gotten by group id: " + groupId);

		return student;

	}

	public Student getByFirstName(String firstName) {

		logger.debug("Entering get student by first name endpoint");
		Student student = studentDao.getByFirstName(firstName).get();
		logger.info(student.toString() + " has been gotten by first name: " + firstName);

		return student;

	}

	public Student getByLastName(String lastName) {

		logger.debug("Entering get student by last name endpoint");
		Student student = studentDao.getByLastName(lastName).get();
		logger.info(student.toString() + " has been gotten by last name: " + lastName);

		return student;

	}

	public Student getById(int studentId) {

		logger.debug("Entering get student by id endpoint");
		Student student = studentDao.getById(studentId).get();
		logger.info(student.toString() + " has been gotten by id: " + studentId);

		return student;

	}

	public List<Student> getAll() {

		logger.debug("Entering get all students endpoint");
		List<Student> studentList = studentDao.getAll();
		logger.info("All courses have been gotten");

		return studentList;

	}

	public Student save(Student student) {

		logger.debug("Entering save student endpoint");
		Student savedStudent = studentDao.save(student);
		logger.info(student.toString() + " has been saved");

		return savedStudent;

	}

	public Student update(int studentId, String[] params) {

		logger.debug("Entering update student endpoint");
		Student student = studentDao.update(studentId, params);
		logger.info("Student with id: " + studentId + " has been updated with this parameters: " + params);

		return student;

	}

	public boolean delete(int studentId) {

		logger.debug("Entering delete student endpoint");
		studentDao.delete(studentId);

		try {

			getById(studentId);
			logger.info("Student with id: " + studentId + " has been deleted");

		} catch (Exception e) {

			return true;

		}
		
		logger.error("Unable to delete student with id: " + studentId);
		return false;

	}

	public List<Student> findStudentsByCourse(String courseName) {

		logger.debug("Entering find students by course endpoint");
		List<Student> studentList = studentDao.findStudentsByCourse(courseName);
		logger.info("Students have been found by course: " + courseName);

		return studentList;

	}

	public StudentCourse addStudentToCourse(int studentId, int courseId) {

		logger.debug("Entering add student to course endpoint");
		StudentCourse studentCourse = studentDao.addStudentToCourse(studentId, courseId);
		logger.info("Student with id: " + studentId + " has been added to course with id: " + courseId);

		return studentCourse;

	}

	public boolean removeStudentFromCourse(int studentId, int courseId) {

		logger.debug("Entering remove student from course endpoint");
		studentDao.removeStudentFromCourse(studentId, courseId);

		try {

			jdbcTemplate.queryForObject("select * from students_courses where student_id = ? and course_id = ?",
					studentCourseRowMapper, studentId, courseId);
			logger.info("Student with id: " + studentId + " has been removed from course with id: " + courseId);

		} catch (Exception e) {

			return true;

		}

		logger.error("Unable to remove student with id: " + studentId + "from course with id: " + courseId);
		return false;

	}

	public void findStudentsByCourse() {

		logger.debug("Entering find students by course in console menu endpoint");
		String courseName;

		System.out.println("Please enter name of the course: ");
		courseName = scanner.nextLine();
		System.out.println(findStudentsByCourse(courseName));
		logger.info("Students have been found by course: " + courseName + " in console menu");

	}

	public void addNewStudent() {

		logger.debug("Entering add new student in console menu endpoint");

		try {

			System.out.println("Please enter group_id: ");
			int groupId = Integer.parseInt(scanner.nextLine());

			System.out.println("Please enter first name: ");
			String firstName = scanner.nextLine();

			System.out.println("Please enter last name: ");
			String lastName = scanner.nextLine();

			save(new Student(0, groupId, firstName, lastName));
			logger.info("New student has been added in console menu");

		} catch (NumberFormatException e) {

			logger.error("Unable to add new student in console menu, message: " + e.getMessage(), e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void deleteStudent() {

		logger.debug("Entering delete student in console menu endpoint");
		try {

			System.out.println("Please enter student_id: ");
			int studentId = Integer.parseInt(scanner.nextLine());

			delete(studentId);
			logger.info("Student has been deleted in console menu");

		} catch (NumberFormatException e) {

			logger.error("Unable to delete student in console menu, message: " + e.getMessage(), e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void addStudentToCourse() {

		logger.debug("Entering add student to course in console menu endpoint");

		try {

			System.out.println("Please enter student_id: ");
			int studentId = Integer.parseInt(scanner.nextLine());

			System.out.println("Please enter course_id: ");
			int courseId = Integer.parseInt(scanner.nextLine());

			addStudentToCourse(studentId, courseId);
			logger.info("Student with id: " + studentId + " has been added to course with id: " + courseId
					+ " in console menu");

		} catch (NumberFormatException e) {

			logger.error("Unable to add student to course in console menu, message: " + e.getMessage(), e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void removeStudentFromCourse() {

		logger.debug("Entering remove student from course in console menu endpoint");

		try {

			System.out.println("Please enter student_id: ");
			int studentId = Integer.parseInt(scanner.nextLine());

			System.out.println("Please enter course_id: ");
			int courseId = Integer.parseInt(scanner.nextLine());

			removeStudentFromCourse(studentId, courseId);
			logger.info("Student with id: " + studentId + " has been removed from course with id: " + courseId
					+ " in console menu");

		} catch (NumberFormatException e) {

			logger.error("Unable remove student from course in console menu, message: " + e.getMessage(), e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

}