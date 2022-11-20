package ua.com.foxminded.springbootjdbcapi.task22.consolemenu;

import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class ConsoleMenu {

	private volatile boolean exit = false;
	private static final int NUMBER_OF_CHOICES = 6;
	private Scanner scanner = new Scanner(System.in);
	private Utils utils;

	public ConsoleMenu(Utils utils) {

		this.utils = utils;

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

		switch (choice) {

		case 0:

			exit = true;
			System.out.println("Well done! See ya later!");
			break;

		case 1:

			utils.findGroupsByStudentNumber();
			break;

		case 2:

			utils.findStudentsByCourse();
			break;

		case 3:

			utils.addNewStudent();
			break;

		case 4:

			utils.deleteStudent();
			break;

		case 5:

			utils.addStudentToCourse();
			break;

		case 6:

			utils.removeStudentFromCourse();
			break;

		default:

			System.out.println("An unknown error has occurred.");

		}

	}

}