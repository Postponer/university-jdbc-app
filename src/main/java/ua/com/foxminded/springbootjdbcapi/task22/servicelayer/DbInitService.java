package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.dbinit.DbInit;
import ua.com.foxminded.springbootjdbcapi.task22.generator.Generator;

@Service
public class DbInitService {

	private DbInit dbInit;
	private JdbcTemplate jdbcTemplate;

	public DbInitService(DbInit dbInit, JdbcTemplate jdbcTemplate) {

		this.dbInit = dbInit;
		this.jdbcTemplate = jdbcTemplate;

	}

	public void initDB() {

		if (!checkIfDatabseIsEmpty()) {

			dbInit.clearDatabaseFacade();

		}

		dbInit.createNCourses(10);
		dbInit.createNGroups(10);
		dbInit.createNStudents(200);
		dbInit.removeExcessiveStudentsFromGroups();
		dbInit.assignCoursesRandomlyToStudents(Generator.getRandomStudentCoursesMap(200));

	}

	private boolean checkIfDatabseIsEmpty() {

		int rowCount = jdbcTemplate.queryForObject("select sum(n_live_tup) from pg_stat_user_tables", Integer.class);

		if (rowCount == 0) {

			return true;

		}

		return false;

	}

}