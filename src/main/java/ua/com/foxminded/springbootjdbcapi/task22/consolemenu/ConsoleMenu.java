package ua.com.foxminded.springbootjdbcapi.task22.consolemenu;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ua.com.foxminded.springbootjdbcapi.task22.servicelayer.GroupService;
import ua.com.foxminded.springbootjdbcapi.task22.servicelayer.StudentService;

@Component
public class ConsoleMenu {

	private volatile boolean exit = false;
	private static final int NUMBER_OF_CHOICES = 6;
	private Scanner scanner = new Scanner(System.in);
	private GroupService groupService;
	private StudentService studentService;
	Logger logger = LoggerFactory.getLogger(ConsoleMenu.class);

	public ConsoleMenu(GroupService groupService, StudentService studentService) {

		this.groupService = groupService;
		this.studentService = studentService;

	}

	public void runMenu() {
		
		logger.debug("Entering console menu run endpoint");

		while (!exit) {

			printMenu();
			int choice = getInput();
			performAction(choice);

		}

		scanner.close();
		
		logger.info("Console menu has run");

	}

	private void printMenu() {
		
		logger.debug("Entering print menu endpoint");

		System.out.println("\nWhat do you whant to do?");
		System.out.println("1) Find all groups with less or equal students’ number;");
		System.out.println("2) Find all students related to the course with the given name;");
		System.out.println("3) Add a new student;");
		System.out.println("4) Delete a student by the STUDENT_ID;");
		System.out.println("5) Add a student to the course (from a list);");
		System.out.println("6) Remove the student from one of their courses;");
		System.out.println("0) Exit.");
		
		logger.info("Menu has been printed");

	}

	private int getInput() {

		logger.debug("Entering get input endpoint");
		
		int choice = -1;

		while (choice < 0 || choice > NUMBER_OF_CHOICES) {

			try {

				System.out.print("\nEnter your choice: ");
				choice = Integer.parseInt(scanner.nextLine());

			} catch (NumberFormatException e) {

				logger.error("Unable to get input, message: " + e.getMessage(), e);
				System.out.println("Invalid choice. Please try again.");

			}

		}

		logger.info("Input has been gotten");
		return choice;

	}

	private void performAction(int choice) {
		
		logger.debug("Entering action perfomance endpoint");

		switch (choice) {

		case 0:

			exit = true;
			System.out.println("Well done! See ya later!");
			break;

		case 1:

			groupService.findGroupsByStudentNumber();
			break;

		case 2:

			studentService.findStudentsByCourse();
			break;

		case 3:

			studentService.addNewStudent();
			break;

		case 4:

			studentService.deleteStudent();
			break;

		case 5:

			studentService.addStudentToCourse();
			break;

		case 6:

			studentService.removeStudentFromCourse();
			break;

		default:

			System.out.println("An unknown error has occurred.");

		}
		
		logger.info("Action has been performed");

	}

}