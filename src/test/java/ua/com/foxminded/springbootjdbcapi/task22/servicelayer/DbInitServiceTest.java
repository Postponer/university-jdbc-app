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
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.CourseDao;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.GroupDao;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.StudentDao;

@SpringBootTest(classes = { Config.class, Application.class })
class DbInitServiceTest {

	private CourseDao courseDao;
	private StudentDao studentDao;
	private GroupDao groupDao;

	@MockBean
	private ConsoleMenu consoleMenu;

	@Autowired
	public DbInitServiceTest(CourseDao courseDao, StudentDao studentDao, GroupDao groupDao) {

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
