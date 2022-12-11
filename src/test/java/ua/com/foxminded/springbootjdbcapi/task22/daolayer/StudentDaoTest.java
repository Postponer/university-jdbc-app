package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;
import ua.com.foxminded.springbootjdbcapi.task22.rowmappers.StudentRowMapper;

@ActiveProfiles("test")
@SpringBootTest(classes = { Config.class, Application.class })
class StudentDaoTest {

	private StudentDao studentDao;
	private final JdbcTemplate jdbcTemplate;
	private final StudentRowMapper studentRowMapper;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public StudentDaoTest(JdbcTemplate jdbcTemplate, StudentRowMapper studentRowMapper, StudentDao studentDao) {

		this.jdbcTemplate = jdbcTemplate;
		this.studentRowMapper = studentRowMapper;
		this.studentDao = studentDao;

	}

	@BeforeEach
	void reset() {

		entityManager.createNativeQuery("truncate table students_courses, students, courses").executeUpdate();
		entityManager.createNativeQuery("ALTER SEQUENCE students_student_id_seq RESTART WITH 1").executeUpdate();

	}

	@Test
	@Transactional
	void testGetAllForStudents_shouldCheckIfTableIsEmpty_whenMethodIsExecuted() {

		assertThat(studentDao.getAll()).isEmpty();

	}

	@Test
	@Transactional
	void testSaveForStudents_shouldCheckIfStudentHasBeenSaved_whenMethodIsExecuted() {

		Student student = new Student();
		student.setGroupId(1);
		student.setFirstName("John");
		student.setLastName("Doe");
		studentDao.save(student);
		assertThat(jdbcTemplate.query("select * from students", studentRowMapper)).hasSize(1);

	}

	@Test
	@Transactional
	void testDeleteForStudents_shouldCheckIfStudentHasBeenDeleted_whenMethodIsExecuted() {

		Student student = new Student();
		student.setGroupId(2);
		student.setFirstName("John");
		student.setLastName("Doe");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)",
				student.getGroupId(), student.getFirstName(), student.getLastName());
		studentDao.delete(1);
		assertThat(jdbcTemplate.query("select * from students", studentRowMapper)).isEmpty();

	}

	@Test
	@Transactional
	void testGetByIdForStudents_shouldCheckIfStudentHasBeenFoundById_whenMethodIsExecuted() {

		Student student = new Student();
		student.setGroupId(3);
		student.setFirstName("John");
		student.setLastName("Doe");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)",
				student.getGroupId(), student.getFirstName(), student.getLastName());
		Optional<Student> result = studentDao.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(3, result.get().getGroupId());
		assertEquals("John", result.get().getFirstName());
		assertEquals("Doe", result.get().getLastName());

	}

	@Test
	@Transactional
	void testUpdateForStudents_shouldCheckIfStudentHasBeenUpdated_whenMethodIsExecuted() {

		Student student = new Student();
		student.setGroupId(4);
		student.setFirstName("John");
		student.setLastName("Doe");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)",
				student.getGroupId(), student.getFirstName(), student.getLastName());
		studentDao.update(1, 99, "UPDATED", "UPDATED");
		Optional<Student> result = Optional.ofNullable(
				jdbcTemplate.queryForObject("select * from students where student_id = ?", studentRowMapper, 1));
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(99, result.get().getGroupId());
		assertEquals("UPDATED", result.get().getFirstName());
		assertEquals("UPDATED", result.get().getLastName());

	}

	@Test
	@Transactional
	void testGetByFirstNameForStudents_shouldCheckIfStudentHasBeenFoundByFirstName_whenMethodIsExecuted() {

		Student student = new Student();
		student.setGroupId(5);
		student.setFirstName("John");
		student.setLastName("Doe");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)",
				student.getGroupId(), student.getFirstName(), student.getLastName());
		Optional<Student> result = studentDao.getByFirstName("John");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(5, result.get().getGroupId());
		assertEquals("John", result.get().getFirstName());
		assertEquals("Doe", result.get().getLastName());

	}

	@Test
	@Transactional
	void testGetByLastNameForStudents_shouldCheckIfStudentHasBeenFoundByLastName_whenMethodIsExecuted() {

		Student student = new Student();
		student.setGroupId(6);
		student.setFirstName("John");
		student.setLastName("Doe");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)",
				student.getGroupId(), student.getFirstName(), student.getLastName());
		Optional<Student> result = studentDao.getByLastName("Doe");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(6, result.get().getGroupId());
		assertEquals("John", result.get().getFirstName());
		assertEquals("Doe", result.get().getLastName());

	}

	@Test
	@Transactional
	void testGetByGroupIdForStudents_shouldCheckIfStudentHasBeenFoundByGroupId_whenMethodIsExecuted() {

		Student student = new Student();
		student.setGroupId(7);
		student.setFirstName("John");
		student.setLastName("Doe");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)",
				student.getGroupId(), student.getFirstName(), student.getLastName());
		Optional<Student> result = studentDao.getByGroupId(7);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(7, result.get().getGroupId());
		assertEquals("John", result.get().getFirstName());
		assertEquals("Doe", result.get().getLastName());

	}

	@Test
	@Transactional
	void testFindStudentsByCourse_shouldReturnAllStudentsRelatedToCourse_whenArgumentIsCourseName() {

		Course course = new Course();
		course.setCourseName("Math");
		course.setCourseDescription("Math Course");
		jdbcTemplate.update("insert into courses (course_name, course_description) values (?, ?)",
				course.getCourseName(), course.getCourseDescription());
		Student student1 = new Student();
		student1.setGroupId(8);
		student1.setFirstName("John");
		student1.setLastName("Doe");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)",
				student1.getGroupId(), student1.getFirstName(), student1.getLastName());
		Student student2 = new Student();
		student2.setGroupId(9);
		student2.setFirstName("Jane");
		student2.setLastName("Miller");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)",
				student2.getGroupId(), student2.getFirstName(), student2.getLastName());
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