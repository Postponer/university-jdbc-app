package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import ua.com.foxminded.springbootjdbcapi.task22.Application;
import ua.com.foxminded.springbootjdbcapi.task22.Config;
import ua.com.foxminded.springbootjdbcapi.task22.controller.Controller;
import ua.com.foxminded.springbootjdbcapi.task22.models.Course;

@SpringBootTest(classes = { Config.class, Application.class })
class JdbcCourseDaoTest {

	private JdbcCourseDao courseDao;
	private JdbcTemplate jdbcTemplate;

	@MockBean
	private Controller controller;

	@Autowired
	public JdbcCourseDaoTest(JdbcCourseDao courseDao, JdbcTemplate jdbcTemplate) {

		this.courseDao = courseDao;
		this.jdbcTemplate = jdbcTemplate;

	}

	@BeforeEach
	void emptyTables() {

		jdbcTemplate.update("truncate table students_courses, courses");

	}

	@BeforeEach
	void resetCoursesSerial() {

		jdbcTemplate.update("ALTER SEQUENCE courses_course_id_seq RESTART WITH 1");

	}

	@Test
	void testGetAllForCourses_shouldCheckIfTableIsEmpty_whenMethodIsExecuted() {

		assertThat(courseDao.getAll()).hasSize(0);

	}

	@Test
	void testSaveForCourses_shouldCheckIfCourseHasBeenSaved_whenMethodIsExecuted() {

		courseDao.save(new Course(1, "Math", "Math Course"));
		assertThat(courseDao.getAll()).hasSize(1);

	}

	@Test
	void testDeleteForCourses_shouldCheckIfCourseHasBeenDeleted_whenMethodIsExecuted() {

		courseDao.save(new Course(1, "Math", "Math Course"));
		courseDao.delete(1);
		assertThat(courseDao.getAll()).hasSize(0);

	}

	@Test
	void testGetByIdForCourses_shouldCheckIfCourseHasBeenFoundById_whenMethodIsExecuted() {

		courseDao.save(new Course(1, "Math", "Math Course"));
		assertEquals(
				"Optional[Course [courseId=1, courseName=Math                                                                                                                                                                                                                                                           , courseDescription=Math Course                                                                                                                                                                                                                                                    ]]",
				courseDao.getById(1).toString());

	}

	@Test
	void testGetByNameForCourses_shouldCheckIfCourseHasBeenFoundByName_whenMethodIsExecuted() {

		courseDao.save(new Course(1, "Math", "Math Course"));
		assertEquals(
				"Optional[Course [courseId=1, courseName=Math                                                                                                                                                                                                                                                           , courseDescription=Math Course                                                                                                                                                                                                                                                    ]]",
				courseDao.getByName("Math").toString());

	}

	@Test
	void testGetByDescriptionForCourses_shouldCheckIfCourseHasBeenFoundByDescription_whenMethodIsExecuted() {

		courseDao.save(new Course(1, "Math", "Math Course"));
		assertEquals(
				"Optional[Course [courseId=1, courseName=Math                                                                                                                                                                                                                                                           , courseDescription=Math Course                                                                                                                                                                                                                                                    ]]",
				courseDao.getByDescription("Math Course").toString());

	}

	@Test
	void testUpdateForCourses_shouldCheckIfCourseHasBeenUpdated_whenMethodIsExecuted() {

		courseDao.save(new Course(1, "Math", "Math Course"));
		String[] params = { "UPDATED", "UPDATED" };
		courseDao.update(1, params);
		assertEquals(
				"Optional[Course [courseId=1, courseName=UPDATED                                                                                                                                                                                                                                                        , courseDescription=UPDATED                                                                                                                                                                                                                                                        ]]",
				courseDao.getById(1).toString());

	}

}