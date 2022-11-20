package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.consolemenu.ConsoleMenu;

@Service
public class ConsoleMenuService {

	private ConsoleMenu consoleMenu;

	public ConsoleMenuService(ConsoleMenu consoleMenu) {

		this.consoleMenu = consoleMenu;

	}

	@PostConstruct
	public void start() {

		consoleMenu.runMenu();

	}

}