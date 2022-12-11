package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.com.foxminded.springbootjdbcapi.task22.Application;
import ua.com.foxminded.springbootjdbcapi.task22.Config;
import ua.com.foxminded.springbootjdbcapi.task22.consolemenu.ConsoleMenu;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcCourseDao;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcGroupDao;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcStudentDao;

@SpringBootTest(classes = { Config.class, Application.class })
class DbInitServiceTest {

	private JdbcCourseDao courseDao;
	private JdbcStudentDao studentDao;
	private JdbcGroupDao groupDao;

	@MockBean
	private ConsoleMenu consoleMenu;

	@Autowired
	public DbInitServiceTest(JdbcCourseDao courseDao, JdbcStudentDao studentDao, JdbcGroupDao groupDao) {

		this.courseDao = courseDao;
		this.studentDao = studentDao;
		this.groupDao = groupDao;

	}

	@Test
	@Transactional
	void testInitDB() {

		assertThat(courseDao.getAll()).hasSize(10);
		assertThat(studentDao.getAll()).hasSize(200);
		assertThat(groupDao.getAll()).hasSize(10);

	}

}
