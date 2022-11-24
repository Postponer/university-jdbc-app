package ua.com.foxminded.springbootjdbcapi.task22.applicationrunnertaskexecutor;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ua.com.foxminded.springbootjdbcapi.task22.consolemenu.ConsoleMenu;
import ua.com.foxminded.springbootjdbcapi.task22.servicelayer.DbInitService;

@Profile("!test")
@Component
public class ApplicationRunnerTaskExecutor implements ApplicationRunner {
	
	private DbInitService dbInitService;
	private ConsoleMenu consoleMenu;
	
	public ApplicationRunnerTaskExecutor(DbInitService dbInitService, ConsoleMenu consoleMenu) {

		this.dbInitService = dbInitService;
		this.consoleMenu = consoleMenu;
		
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		dbInitService.initDB();
		consoleMenu.runMenu();
		
	}

}