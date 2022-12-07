package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcStudentDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;
import ua.com.foxminded.springbootjdbcapi.task22.models.StudentCourse;

@Service
public class StudentService {

	private JdbcStudentDao studentDao;
	private Scanner scanner = new Scanner(System.in);
	Logger logger = LoggerFactory.getLogger(StudentService.class);

	public StudentService(JdbcStudentDao studentDao) {

		this.studentDao = studentDao;

	}

	public Student getByGroupId(int groupId) {

		logger.info("Getting student by group id: {}", groupId);
		Student student = studentDao.getByGroupId(groupId).orElse(null);
		logger.info("{} has been gotten by group id: {}", student, groupId);

		return student;

	}

	public Student getByFirstName(String firstName) {

		logger.info("Getting student by first name: {}", firstName);
		Student student = studentDao.getByFirstName(firstName).orElse(null);
		logger.info("{} has been gotten by first name: {}", student, firstName);

		return student;

	}

	public Student getByLastName(String lastName) {

		logger.info("Getting student by last name: {}", lastName);
		Student student = studentDao.getByLastName(lastName).orElse(null);
		logger.info("{} has been gotten by last name: {}", student, lastName);

		return student;

	}

	public Student getById(int studentId) {

		logger.info("Getting student by id: {}", studentId);
		Student student = studentDao.getById(studentId).orElse(null);
		logger.info("{} has been gotten by id: {}", student, studentId);

		return student;

	}

	public List<Student> getAll() {

		logger.info("Getting all students");
		List<Student> studentList = studentDao.getAll();
		logger.info("All courses have been gotten");

		return studentList;

	}

	public Student save(Student student) {

		logger.info("Saving {}", student);
		Student savedStudent = studentDao.save(student);
		logger.info("{} has been saved", student);

		return savedStudent;

	}

	public Student update(int studentId, String[] params) {

		logger.info("Updating student with id: {} with this parameters: {}", studentId, params);
		Student student = studentDao.update(studentId, params);
		logger.info("Student with id: {} has been updated with this parameters: {}", studentId, params);

		return student;

	}

	public boolean delete(int studentId) {

		logger.info("Deleting student with id: {}", studentId);

		try {

			studentDao.delete(studentId);
			logger.info("Student with id: {} has been deleted", studentId);
			return true;

		} catch (Exception e) {

			logger.error("Exception occurred during student deletion by id: {}, message. Exception: ", studentId, e);
			return false;

		}

	}

	public List<Student> findStudentsByCourse(String courseName) {

		logger.info("Finding students by course: {}", courseName);
		List<Student> studentList = studentDao.findStudentsByCourse(courseName);
		logger.info("Students have been found by course: {}", courseName);

		return studentList;

	}

	public StudentCourse addStudentToCourse(int studentId, int courseId) {

		logger.info("Adding student with id: {} to course with id {}", studentId, courseId);
		StudentCourse studentCourse = studentDao.addStudentToCourse(studentId, courseId);
		logger.info("Student with id: {} has been added to course with id: {}", studentId, courseId);

		return studentCourse;

	}

	public boolean removeStudentFromCourse(int studentId, int courseId) {

		logger.info("Removing student with id: {} from course with id: {}", studentId, courseId);

		try {

			studentDao.removeStudentFromCourse(studentId, courseId);
			logger.info("Student with id: {} has been removed from course with id: {}", studentId, courseId);
			return true;

		} catch (Exception e) {

			logger.error("Exception occurred during student removal with id: {} from course with id: {}, message. Exception:", studentId,
					courseId, e);
			return false;

		}

	}

	public void findStudentsByCourse() {

		logger.info("Finding students by course in console menu");
		String courseName;

		System.out.println("Please enter name of the course: ");
		courseName = scanner.nextLine();
		System.out.println(findStudentsByCourse(courseName));
		logger.info("Students have been found by course: {} in console menu", courseName);

	}

	public void addNewStudent() {

		logger.info("Adding new student in console menu");

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

			logger.error("Exception occurred during adding new student in console menu, message. Exception: ", e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void deleteStudent() {

		logger.info("Deleting student in console menu");

		try {

			System.out.println("Please enter student_id: ");
			int studentId = Integer.parseInt(scanner.nextLine());

			delete(studentId);
			logger.info("Student has been deleted in console menu");

		} catch (NumberFormatException e) {

			logger.error("Exception occurred during student deletion in console menu, message. Exception: ", e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void addStudentToCourse() {

		logger.info("Adding student to course in console menu");

		try {

			System.out.println("Please enter student_id: ");
			int studentId = Integer.parseInt(scanner.nextLine());

			System.out.println("Please enter course_id: ");
			int courseId = Integer.parseInt(scanner.nextLine());

			addStudentToCourse(studentId, courseId);
			logger.info("Student with id: {} has been added to course with id: {} in console menu", studentId,
					courseId);

		} catch (NumberFormatException e) {

			logger.error("Exception occurred during student adding to course in console menu, message. Exception: ", e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void removeStudentFromCourse() {

		logger.info("Removing student from course in console menu");

		try {

			System.out.println("Please enter student_id: ");
			int studentId = Integer.parseInt(scanner.nextLine());

			System.out.println("Please enter course_id: ");
			int courseId = Integer.parseInt(scanner.nextLine());

			removeStudentFromCourse(studentId, courseId);
			logger.info("Student with id: {} has been removed from course with id: {} in console menu", studentId,
					courseId);

		} catch (NumberFormatException e) {

			logger.error("Exception occurred during student removal from course in console menu, message. Exception: ",
					e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

}