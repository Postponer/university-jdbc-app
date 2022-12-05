package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import ua.com.foxminded.springbootjdbcapi.task22.Application;
import ua.com.foxminded.springbootjdbcapi.task22.Config;
import ua.com.foxminded.springbootjdbcapi.task22.models.Course;

@ActiveProfiles("test")
@SpringBootTest(classes = { Config.class, Application.class })
class JdbcCourseDaoTest {

	private JdbcCourseDao courseDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcCourseDaoTest(JdbcCourseDao courseDao, JdbcTemplate jdbcTemplate) {

		this.courseDao = courseDao;
		this.jdbcTemplate = jdbcTemplate;

	}

	@BeforeEach
	void reset() {

		jdbcTemplate.update("truncate table students_courses, courses");
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
		Optional<Course> result = courseDao.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getCourseId());
		assertEquals("Math", result.get().getCourseName());
		assertEquals("Math Course", result.get().getCourseDescription());

	}

	@Test
	void testGetByNameForCourses_shouldCheckIfCourseHasBeenFoundByName_whenMethodIsExecuted() {

		courseDao.save(new Course(1, "Math", "Math Course"));
		Optional<Course> result = courseDao.getByName("Math");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getCourseId());
		assertEquals("Math", result.get().getCourseName());
		assertEquals("Math Course", result.get().getCourseDescription());

	}

	@Test
	void testGetByDescriptionForCourses_shouldCheckIfCourseHasBeenFoundByDescription_whenMethodIsExecuted() {

		courseDao.save(new Course(1, "Math", "Math Course"));
		Optional<Course> result = courseDao.getByDescription("Math Course");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getCourseId());
		assertEquals("Math", result.get().getCourseName());
		assertEquals("Math Course", result.get().getCourseDescription());

	}

	@Test
	void testUpdateForCourses_shouldCheckIfCourseHasBeenUpdated_whenMethodIsExecuted() {

		courseDao.save(new Course(1, "Math", "Math Course"));
		String[] params = { "UPDATED", "UPDATED" };
		courseDao.update(1, params);
		Optional<Course> result = courseDao.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getCourseId());
		assertEquals("UPDATED", result.get().getCourseName());
		assertEquals("UPDATED", result.get().getCourseDescription());

	}

}