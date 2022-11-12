package ua.com.foxminded.springbootjdbcapi.task22.databasefacade;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.com.foxminded.springbootjdbcapi.task22.Application;
import ua.com.foxminded.springbootjdbcapi.task22.Config;
import ua.com.foxminded.springbootjdbcapi.task22.controller.Controller;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcCourseDao;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcGroupDao;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcStudentDao;

@SpringBootTest(classes = { Config.class, Application.class })
class DatabaseFacadeTest {

	private DatabaseFacade databaseFacade;
	private JdbcGroupDao groupDao;
	private JdbcStudentDao studentDao;
	private JdbcCourseDao courseDao;

	@Autowired
	public DatabaseFacadeTest(DatabaseFacade databaseFacade, JdbcGroupDao groupDao, JdbcStudentDao studentDao,
			JdbcCourseDao courseDao) {

		this.databaseFacade = databaseFacade;
		this.groupDao = groupDao;
		this.studentDao = studentDao;
		this.courseDao = courseDao;

	}

	@MockBean
	private Controller controller;

	@Test
	void testCreateNCourses_shouldCheckIfCoursesHaveBeenCreated_whenMethodIsExecuted() {

		databaseFacade.createNCourses(10);
		assertThat(courseDao.getAll()).hasSize(10);

	}
	
	@Test
	void testCreateNGroups_shouldCheckIfGroupsHaveBeenCreated_whenMethodIsExecuted() {
		
		databaseFacade.createNGroups(10);
		assertThat(groupDao.getAll()).hasSize(10);
		
	}
	
	@Test
	void testCreateNStudents_shouldCheckIfStudentsHaveBeenCreated_whenMethodIsExecuted() {
		
		databaseFacade.createNStudents(200);
		assertThat(studentDao.getAll()).hasSize(200);
		
	}

}