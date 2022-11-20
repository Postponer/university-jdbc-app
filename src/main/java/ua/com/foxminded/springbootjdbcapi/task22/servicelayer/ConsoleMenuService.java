package ua.com.foxminded.springbootjdbcapi.task22.controller;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.consolemenu.ConsoleMenu;
import ua.com.foxminded.springbootjdbcapi.task22.databasefacade.DatabaseFacade;
import ua.com.foxminded.springbootjdbcapi.task22.generator.Generator;

@Service
public class Controller {

	private ConsoleMenu consoleMenu;
	private DatabaseFacade databaseFacade;

	public Controller(ConsoleMenu consoleMenu, DatabaseFacade databaseFacade) {
		
		this.consoleMenu = consoleMenu;
		this.databaseFacade = databaseFacade;
		
	}

	@PostConstruct
	public void start() {

		databaseFacade.clearDatabaseFacade();
		databaseFacade.createNCourses(10);
		databaseFacade.createNGroups(10);
		databaseFacade.createNStudents(200);
		databaseFacade.removeExcessiveStudentsFromGroups();
		databaseFacade.assignCoursesRandomlyToStudents(Generator.getRandomStudentCoursesMap(200));
		consoleMenu.runMenu();

	}

}