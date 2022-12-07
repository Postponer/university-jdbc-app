package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ua.com.foxminded.springbootjdbcapi.task22.Application;
import ua.com.foxminded.springbootjdbcapi.task22.Config;
import ua.com.foxminded.springbootjdbcapi.task22.models.Course;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;

@ActiveProfiles("test")
@SpringBootTest(classes = { Config.class, Application.class })
class JdbcStudentDaoTest {

	private JdbcStudentDao studentDao;
	private JdbcCourseDao courseDao;
	private EntityManager entityManager;

	@Autowired
	public JdbcStudentDaoTest(JdbcStudentDao studentDao, JdbcCourseDao courseDao, EntityManager entityManager) {

		this.studentDao = studentDao;
		this.courseDao = courseDao;
		this.entityManager = entityManager;

	}

	@BeforeEach
	void reset() {

		entityManager.createNativeQuery("truncate table students_courses, students, courses").executeUpdate();
		entityManager.createNativeQuery("ALTER SEQUENCE students_student_id_seq RESTART WITH 1").executeUpdate();


	}

	@Test
	@Transactional
	void testGetAllForStudents_shouldCheckIfTableIsEmpty_whenMethodIsExecuted() {
		
		assertThat(studentDao.getAll()).hasSize(0);

	}

	@Test
	@Transactional
	void testSaveForStudents_shouldCheckIfStudentHasBeenSaved_whenMethodIsExecuted() {

		Student student = new Student();
		student.setGroupId(1);
		student.setFirstName("John");
		student.setLastName("Doe");
		studentDao.save(student);
		assertThat(studentDao.getAll()).hasSize(1);

	}

	@Test
	@Transactional
	void testDeleteForStudents_shouldCheckIfStudentHasBeenDeleted_whenMethodIsExecuted() {

		Student student = new Student();
		student.setGroupId(2);
		student.setFirstName("John");
		student.setLastName("Doe");
		studentDao.save(student);
		studentDao.delete(1);
		assertThat(studentDao.getAll()).hasSize(0);

	}

	@Test
	@Transactional
	void testGetByIdForStudents_shouldCheckIfStudentHasBeenFoundById_whenMethodIsExecuted() {

		Student student = new Student();
		student.setGroupId(3);
		student.setFirstName("John");
		student.setLastName("Doe");
		studentDao.save(student);
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
		studentDao.save(student);
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
	@Transactional
	void testGetByFirstNameForStudents_shouldCheckIfStudentHasBeenFoundByFirstName_whenMethodIsExecuted() {

		Student student = new Student();
		student.setGroupId(5);
		student.setFirstName("John");
		student.setLastName("Doe");
		studentDao.save(student);
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
		studentDao.save(student);
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
		studentDao.save(student);
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
		courseDao.save(course);
		Student student1 = new Student();
		student1.setGroupId(8);
		student1.setFirstName("John");
		student1.setLastName("Doe");
		studentDao.save(student1);
		Student student2 = new Student();
		student2.setGroupId(9);
		student2.setFirstName("Jane");
		student2.setLastName("Miller");
		studentDao.save(student2);
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