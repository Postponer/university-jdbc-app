package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.databasefacade.DatabaseFacade;
import ua.com.foxminded.springbootjdbcapi.task22.generator.Generator;

@Service
public class DatabaseFacadeService implements ApplicationRunner {

	private DatabaseFacade databaseFacade;
	private JdbcTemplate jdbcTemplate;

	public DatabaseFacadeService(DatabaseFacade databaseFacade, JdbcTemplate jdbcTemplate) {

		this.databaseFacade = databaseFacade;
		this.jdbcTemplate = jdbcTemplate;

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		if (!checkIfDatabseIsEmpty()) {

			databaseFacade.clearDatabaseFacade();

		} else {

			databaseFacade.createNCourses(10);
			databaseFacade.createNGroups(10);
			databaseFacade.createNStudents(200);
			databaseFacade.removeExcessiveStudentsFromGroups();
			databaseFacade.assignCoursesRandomlyToStudents(Generator.getRandomStudentCoursesMap(200));

		}

	}

	private boolean checkIfDatabseIsEmpty() {

		int rowCount = jdbcTemplate.queryForObject("select sum(n_live_tup) from pg_stat_user_tables", Integer.class);

		if (rowCount == 0) {

			return true;

		}

		return false;

	}

}