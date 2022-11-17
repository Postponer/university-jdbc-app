package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

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
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;

@SpringBootTest(classes = { Config.class, Application.class })
class JdbcStudentDaoTest {

	private JdbcStudentDao studentDao;
	private JdbcCourseDao courseDao;
	private JdbcTemplate jdbcTemplate;

	@MockBean
	private Controller controller;

	@Autowired
	public JdbcStudentDaoTest(JdbcStudentDao studentDao, JdbcCourseDao courseDao, JdbcTemplate jdbcTemplate) {

		this.studentDao = studentDao;
		this.courseDao = courseDao;
		this.jdbcTemplate = jdbcTemplate;

	}

	@BeforeEach
	void reset() {

		jdbcTemplate.update("truncate table students_courses, students, courses");
		jdbcTemplate.update("ALTER SEQUENCE students_student_id_seq RESTART WITH 1");


	}

	@Test
	void testGetAllForStudents_shouldCheckIfTableIsEmpty_whenMethodIsExecuted() {

		assertThat(studentDao.getAll()).hasSize(0);

	}

	@Test
	void testSaveForStudents_shouldCheckIfStudentHasBeenSaved_whenMethodIsExecuted() {

		studentDao.save(new Student(1, 1, "John", "Doe"));
		assertThat(studentDao.getAll()).hasSize(1);

	}

	@Test
	void testDeleteForStudents_shouldCheckIfStudentHasBeenDeleted_whenMethodIsExecuted() {

		studentDao.save(new Student(1, 2, "John", "Doe"));
		studentDao.delete(1);
		assertThat(studentDao.getAll()).hasSize(0);

	}

	@Test
	void testGetByIdForStudents_shouldCheckIfStudentHasBeenFoundById_whenMethodIsExecuted() {

		studentDao.save(new Student(1, 3, "John", "Doe"));
		Optional<Student> result = studentDao.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(3, result.get().getGroupId());
		assertEquals("John", result.get().getFirstName());
		assertEquals("Doe", result.get().getLastName());

	}

	@Test
	void testUpdateForStudents_shouldCheckIfStudentHasBeenUpdated_whenMethodIsExecuted() {

		studentDao.save(new Student(1, 4, "John", "Doe"));
		String[] params = { "99", "UPDATED", "UPDATED" };
		studentDao.update(1, params);
		Optional<Student> result = studentDao.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(99, result.get().getGroupId());
		assertEquals("UPDATED", result.get().getFirstName());
		assertEquals("UPDATED", result.get().getLastName());

	}

	@Test
	void testGetByFirstNameForStudents_shouldCheckIfStudentHasBeenFoundByFirstName_whenMethodIsExecuted() {

		studentDao.save(new Student(1, 5, "John", "Doe"));
		Optional<Student> result = studentDao.getByFirstName("John");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(5, result.get().getGroupId());
		assertEquals("John", result.get().getFirstName());
		assertEquals("Doe", result.get().getLastName());

	}

	@Test
	void testGetByLastNameForStudents_shouldCheckIfStudentHasBeenFoundByLastName_whenMethodIsExecuted() {

		studentDao.save(new Student(1, 6, "John", "Doe"));
		Optional<Student> result = studentDao.getByLastName("Doe");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(6, result.get().getGroupId());
		assertEquals("John", result.get().getFirstName());
		assertEquals("Doe", result.get().getLastName());

	}

	@Test
	void testGetByGroupIdForStudents_shouldCheckIfStudentHasBeenFoundByGroupId_whenMethodIsExecuted() {

		studentDao.save(new Student(1, 7, "John", "Doe"));
		Optional<Student> result = studentDao.getByGroupId(7);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(7, result.get().getGroupId());
		assertEquals("John", result.get().getFirstName());
		assertEquals("Doe", result.get().getLastName());

	}

	@Test
	void testFindStudentsByCourse_shouldReturnAllStudentsRelatedToCourse_whenArgumentIsCourseName() {

		courseDao.save(new Course(1, "Math", "Math Course"));
		studentDao.save(new Student(1, 8, "John", "Doe"));
		studentDao.save(new Student(2, 9, "Jane", "Miller"));
		studentDao.addStudentToCourse(1, 1);
		studentDao.addStudentToCourse(2, 1);
		List<Student> results = studentDao.findStudentsByCourse("Math");
		assertThat(results).hasSize(2);
		Student firstResult = results.get(0);
		assertEquals(1, firstResult.getStudentId());
		assertEquals(8, firstResult.getGroupId());
		assertEquals("John", firstResult.getFirstName());
		assertEquals("Doe", firstResult.getLastName());
		Student secondResult = results.get(1);
		assertEquals(2, secondResult.getStudentId());
		assertEquals(9, secondResult.getGroupId());
		assertEquals("Jane", secondResult.getFirstName());
		assertEquals("Miller", secondResult.getLastName());

	}

}