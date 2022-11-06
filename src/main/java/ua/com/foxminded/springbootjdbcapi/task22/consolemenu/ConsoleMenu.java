package ua.com.foxminded.springbootjdbcapi.task22.consolemenu;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcGroupDao;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcStudentDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;

@Component
public class ConsoleMenu {
	
	private volatile boolean exit = false;
	private static final int NUMBER_OF_CHOICES = 6;
	private Scanner scanner = new Scanner(System.in);
	private JdbcGroupDao groupDao;
	private JdbcStudentDao studentDao;

	public ConsoleMenu(JdbcGroupDao groupDao, JdbcStudentDao studentDao) {
		
		this.groupDao = groupDao;
		this.studentDao = studentDao;
		
	}

	public void runMenu() {

		while (!exit) {

			printMenu();
			int choice = getInput();
			performAction(choice);

		}

		scanner.close();

	}

	private void printMenu() {

		System.out.println("\nWhat do you whant to do?");
		System.out.println("1) Find all groups with less or equal students’ number;");
		System.out.println("2) Find all students related to the course with the given name;");
		System.out.println("3) Add a new student;");
		System.out.println("4) Delete a student by the STUDENT_ID;");
		System.out.println("5) Add a student to the course (from a list);");
		System.out.println("6) Remove the student from one of their courses;");
		System.out.println("0) Exit.");

	}

	private int getInput() {

		int choice = -1;

		while (choice < 0 || choice > NUMBER_OF_CHOICES) {

			try {

				System.out.print("\nEnter your choice: ");
				choice = Integer.parseInt(scanner.nextLine());

			} catch (NumberFormatException e) {

				System.out.println("Invalid choice. Please try again.");

			}

		}

		return choice;

	}

	private void performAction(int choice) {

		int studentId = 0;
		int courseId = 0;
		int groupId = 0;
		String courseName;
		int studentNumber = 0;
		String firstName;
		String lastName;
		

		switch (choice) {

		case 0:

			exit = true;
			System.out.println("Well done! See ya later!");
			break;

		case 1:

			try {

				System.out.println("Please enter a number of students: ");
				studentNumber = Integer.parseInt(scanner.nextLine());

			} catch (NumberFormatException e) {

				System.out.println("Invalid input. Please use numbers.");

			}

			System.out.println(groupDao.findGroupsByStudentNumber(studentNumber));
			break;

		case 2:

			System.out.println("Please enter name of the course: ");
			courseName = scanner.nextLine();
			System.out.println(studentDao.findStudentsByCourse(courseName));
			break;

		case 3:

			try {

				System.out.println("Please enter group_id: ");
				groupId = Integer.parseInt(scanner.nextLine());

			} catch (NumberFormatException e) {

				System.out.println("Invalid input. Please use numbers.");

			}

			System.out.println("Please enter first name: ");
		    firstName = scanner.nextLine();

			System.out.println("Please enter last name: ");
			lastName = scanner.nextLine();
			
			studentDao.save(new Student(groupId, firstName, lastName));
			break;

		case 4:

			try {

				System.out.println("Please enter student_id: ");
				studentId = Integer.parseInt(scanner.nextLine());

			} catch (NumberFormatException e) {

				System.out.println("Invalid input. Please use numbers.");

			}

			studentDao.delete(studentId);
			break;

		case 5:

			try {

				System.out.println("Please enter student_id: ");
				studentId = Integer.parseInt(scanner.nextLine());

				System.out.println("Please enter course_id: ");
				courseId = Integer.parseInt(scanner.nextLine());

			} catch (NumberFormatException e) {

				System.out.println("Invalid input. Please use numbers.");

			}

			studentDao.addStudentToCourse(studentId, courseId);
			break;

		case 6:

			try {

				System.out.println("Please enter student_id: ");
				studentId = Integer.parseInt(scanner.nextLine());

				System.out.println("Please enter course_id: ");
				courseId = Integer.parseInt(scanner.nextLine());

			} catch (NumberFormatException e) {

				System.out.println("Invalid input. Please use numbers.");

			}

			studentDao.removeStudentFromCourse(studentId, courseId);
			break;

		default:

			System.out.println("An unknown error has occurred.");

		}

	}

}