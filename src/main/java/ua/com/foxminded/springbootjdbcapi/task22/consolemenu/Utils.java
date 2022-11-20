package ua.com.foxminded.springbootjdbcapi.task22.consolemenu;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcGroupDao;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcStudentDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;

@Component
public class Utils {

	private Scanner scanner = new Scanner(System.in);
	private JdbcGroupDao groupDao;
	private JdbcStudentDao studentDao;

	public Utils(JdbcGroupDao groupDao, JdbcStudentDao studentDao) {

		this.groupDao = groupDao;
		this.studentDao = studentDao;

	}

	public void findGroupsByStudentNumber() {

		try {

			System.out.println("Please enter a number of students: ");
			int studentNumber = Integer.parseInt(scanner.nextLine());
			System.out.println(groupDao.findGroupsByStudentNumber(studentNumber));

		} catch (NumberFormatException e) {

			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void findStudentsByCourse() {

		String courseName;

		System.out.println("Please enter name of the course: ");
		courseName = scanner.nextLine();
		System.out.println(studentDao.findStudentsByCourse(courseName));

	}

	public void addNewStudent() {

		try {

			System.out.println("Please enter group_id: ");
			int groupId = Integer.parseInt(scanner.nextLine());

			System.out.println("Please enter first name: ");
			String firstName = scanner.nextLine();

			System.out.println("Please enter last name: ");
			String lastName = scanner.nextLine();

			studentDao.save(new Student(0, groupId, firstName, lastName));

		} catch (NumberFormatException e) {

			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void deleteStudent() {

		try {

			System.out.println("Please enter student_id: ");
			int studentId = Integer.parseInt(scanner.nextLine());

			studentDao.delete(studentId);

		} catch (NumberFormatException e) {

			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void addStudentToCourse() {

		try {

			System.out.println("Please enter student_id: ");
			int studentId = Integer.parseInt(scanner.nextLine());

			System.out.println("Please enter course_id: ");
			int courseId = Integer.parseInt(scanner.nextLine());

			studentDao.addStudentToCourse(studentId, courseId);

		} catch (NumberFormatException e) {

			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void removeStudentFromCourse() {

		try {

			System.out.println("Please enter student_id: ");
			int studentId = Integer.parseInt(scanner.nextLine());

			System.out.println("Please enter course_id: ");
			int courseId = Integer.parseInt(scanner.nextLine());

			studentDao.removeStudentFromCourse(studentId, courseId);

		} catch (NumberFormatException e) {

			System.out.println("Invalid input. Please use numbers.");

		}

	}

}