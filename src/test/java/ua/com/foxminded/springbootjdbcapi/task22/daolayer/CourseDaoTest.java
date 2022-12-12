package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import ua.com.foxminded.springbootjdbcapi.task22.Application;
import ua.com.foxminded.springbootjdbcapi.task22.Config;
import ua.com.foxminded.springbootjdbcapi.task22.models.Course;
import ua.com.foxminded.springbootjdbcapi.task22.rowmappers.CourseRowMapper;

@ActiveProfiles("test")
@SpringBootTest(classes = { Config.class, Application.class })
class CourseDaoTest {

	private CourseDao courseDao;
	private final JdbcTemplate jdbcTemplate;
	private final CourseRowMapper courseRowMapper;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public CourseDaoTest(JdbcTemplate jdbcTemplate, CourseRowMapper courseRowMapper, CourseDao courseDao) {

		this.jdbcTemplate = jdbcTemplate;
		this.courseRowMapper = courseRowMapper;
		this.courseDao = courseDao;

	}

	@BeforeEach
	void reset() {

		entityManager.createNativeQuery("truncate table students_courses, courses").executeUpdate();
		entityManager.createNativeQuery("ALTER SEQUENCE courses_course_id_seq RESTART WITH 1").executeUpdate();

	}

	@Test
	@Transactional
	void testGetAllForCourses_shouldCheckIfTableIsEmpty_whenMethodIsExecuted() {

		assertThat(courseDao.getAll()).isEmpty();

	}

	@Test
	@Transactional
	void testSaveForCourses_shouldCheckIfCourseHasBeenSaved_whenMethodIsExecuted() {

		Course course = new Course();
		course.setCourseName("Math");
		course.setCourseDescription("Math Course");
		courseDao.save(course);
		assertThat(jdbcTemplate.query("select * from courses", courseRowMapper)).hasSize(1);

	}

	@Test
	@Transactional
	void testDeleteForCourses_shouldCheckIfCourseHasBeenDeleted_whenMethodIsExecuted() {

		Course course = new Course();
		course.setCourseName("Math");
		course.setCourseDescription("Math Course");
		jdbcTemplate.update("insert into courses (course_name, course_description) values (?, ?)",
				course.getCourseName(), course.getCourseDescription());
		courseDao.delete(1);
		assertThat(jdbcTemplate.query("select * from courses", courseRowMapper)).isEmpty();

	}

	@Test
	@Transactional
	void testGetByIdForCourses_shouldCheckIfCourseHasBeenFoundById_whenMethodIsExecuted() {

		Course course = new Course();
		course.setCourseName("Math");
		course.setCourseDescription("Math Course");
		jdbcTemplate.update("insert into courses (course_name, course_description) values (?, ?)",
				course.getCourseName(), course.getCourseDescription());
		Optional<Course> result = courseDao.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getCourseId());
		assertEquals("Math", result.get().getCourseName());
		assertEquals("Math Course", result.get().getCourseDescription());

	}

	@Test
	@Transactional
	void testGetByNameForCourses_shouldCheckIfCourseHasBeenFoundByName_whenMethodIsExecuted() {

		Course course = new Course();
		course.setCourseName("Math");
		course.setCourseDescription("Math Course");
		jdbcTemplate.update("insert into courses (course_name, course_description) values (?, ?)",
				course.getCourseName(), course.getCourseDescription());
		Optional<Course> result = courseDao.getByName("Math");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getCourseId());
		assertEquals("Math", result.get().getCourseName());
		assertEquals("Math Course", result.get().getCourseDescription());

	}

	@Test
	@Transactional
	void testGetByDescriptionForCourses_shouldCheckIfCourseHasBeenFoundByDescription_whenMethodIsExecuted() {

		Course course = new Course();
		course.setCourseName("Math");
		course.setCourseDescription("Math Course");
		jdbcTemplate.update("insert into courses (course_name, course_description) values (?, ?)",
				course.getCourseName(), course.getCourseDescription());
		Optional<Course> result = courseDao.getByDescription("Math Course");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getCourseId());
		assertEquals("Math", result.get().getCourseName());
		assertEquals("Math Course", result.get().getCourseDescription());

	}

	@Test
	@Transactional
	void testUpdateForCourses_shouldCheckIfCourseHasBeenUpdated_whenMethodIsExecuted() {

		Course course = new Course();
		course.setCourseName("Math");
		course.setCourseDescription("Math Course");
		jdbcTemplate.update("insert into courses (course_name, course_description) values (?, ?)",
				course.getCourseName(), course.getCourseDescription());
		courseDao.update(1, "UPDATED", "UPDATED");
		Optional<Course> result = Optional.ofNullable(
				jdbcTemplate.queryForObject("select * from courses where course_id = ?", courseRowMapper, 1));
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getCourseId());
		assertEquals("UPDATED", result.get().getCourseName());
		assertEquals("UPDATED", result.get().getCourseDescription());

	}

}