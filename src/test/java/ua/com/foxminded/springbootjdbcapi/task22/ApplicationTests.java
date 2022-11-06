package ua.com.foxminded.springbootjdbcapi.task22;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.springframework.test.context.transaction.TestTransaction;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcCourseDao;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcGroupDao;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcStudentDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Group;

@SpringBootTest
@Testcontainers
class ApplicationTests {

	private static int NUMBER_OF_GROUPS = 10;

	private JdbcGroupDao groupDao;
	private JdbcStudentDao studentDao;
	private JdbcCourseDao courseDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public ApplicationTests(JdbcGroupDao groupDao, JdbcStudentDao studentDao, JdbcCourseDao courseDao,
			JdbcTemplate jdbcTemplate) {

		this.groupDao = groupDao;
		this.studentDao = studentDao;
		this.courseDao = courseDao;
		this.jdbcTemplate = jdbcTemplate;

	}

	@Container
	public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.4");

	@DynamicPropertySource
	public static void overrideProps(DynamicPropertyRegistry registry) {

		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);

	}

	@BeforeAll
	public static void setup() {

		container.start();

	}

//	@PostConstruct
//	public void backupDatabase() throws IOException, InterruptedException {
//
//		container.execInContainer("pg_dump", "-U", "test", "-w", "--data-only", "-Fc", "-d", "test", "-f",
//				"/tmp/backup");
//
//	}
//	
//	@AfterEach
//	void restoreDatabase() throws IOException, InterruptedException {
//
//		jdbcTemplate.queryForList(
//				"select 'truncate table \"' || tablename || '\" cascade;' from pg_tables where schemaname='public';\n",
//				String.class).forEach(jdbcTemplate::update);
//
//		TestTransaction.flagForCommit();
//		TestTransaction.end();
//
//		container.execInContainer("pg_restore", "-U", "test", "-w", "--data-only", "--disable-triggers",
//				"--dbname=test", "/tmp/backup");
//
//	}

	@Test
	void testGetAllForGroups_shouldCheckIfTableHasAllGroups_whenMethodIsExecuted() {

		assertThat(groupDao.getAll()).hasSize(NUMBER_OF_GROUPS);

	}

	@Test
	void testSaveForGroups_shouldCheckIfTableHasAllGroups_whenMethodIsExecuted() {

		groupDao.save(new Group("AA-11"));
		assertThat(groupDao.getAll()).hasSize(NUMBER_OF_GROUPS + 1);

	}

	@Test
	void testDeleteForGroups_shouldCheckIfTableHasAllGroups_whenMethodIsExecuted() {

		groupDao.save(new Group("AA-11"));
		groupDao.delete(NUMBER_OF_GROUPS + 1);
		assertThat(groupDao.getAll()).hasSize(NUMBER_OF_GROUPS);

	}

	@Test
	void testGetByIdForGroups_shouldCheckIfTableHasAllGroups_whenMethodIsExecuted() {

		assertEquals(
				"Optional[Group [groupName=AA-01                                                                                                                                                                                                                                                          ]]",
				groupDao.getById(1).toString());

	}

	@Test
	void testUpdateForGroups_shouldCheckIfTableHasAllGroups_whenMethodIsExecuted() {

		String[] params = { "UPDATED" };
		groupDao.update(1, params);
		assertEquals(
				"Optional[Group [groupName=UPDATED                                                                                                                                                                                                                                                        ]]",
				groupDao.getById(1).toString());

	}

}