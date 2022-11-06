package ua.com.foxminded.springbootjdbcapi.task22.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ua.com.foxminded.springbootjdbcapi.task22.consolemenu.ConsoleMenu;

@Component
public class Controller implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(Controller.class);
	private ConsoleMenu consoleMenu;
	
	public Controller(ConsoleMenu consoleMenu) {
		
		this.consoleMenu = consoleMenu;
		
	}

	@Override
	public void run(String... args) throws Exception {

		logger.info("EXECUTING : command line runner");

		consoleMenu.runMenu();

	}

}
