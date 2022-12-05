package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.dbinit.DbInit;
import ua.com.foxminded.springbootjdbcapi.task22.generator.Generator;

@Service
public class DbInitService {

	private DbInit dbInit;
	Logger logger = LoggerFactory.getLogger(DbInitService.class);

	public DbInitService(DbInit dbInit) {

		this.dbInit = dbInit;

	}

	public void initDB() {

		logger.debug("Entering database initiation endpoint");

		if (!dbInit.checkIfDatabaseIsEmpty()) {

			dbInit.clearDatabaseFacade();

		}

		dbInit.createNCourses(10);
		dbInit.createNGroups(10);
		dbInit.createNStudents(200);
		dbInit.removeExcessiveStudentsFromGroups();
		dbInit.assignCoursesRandomlyToStudents(Generator.getRandomStudentCoursesMap(200));
		
		logger.info("Database has been initiated");

	}

}