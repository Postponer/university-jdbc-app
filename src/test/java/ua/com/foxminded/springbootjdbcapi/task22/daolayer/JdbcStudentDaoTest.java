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
	void emptyTables() {

		jdbcTemplate.update("truncate table students_courses, students, courses");

	}

	@BeforeEach
	void resetStudentsSerial() {

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
		assertEquals(
				"Optional[Student [studentId=1, groupId=3, firstName=John                                                                                                                                                                                                                                                           , lastName=Doe                                                                                                                                                                                                                                                            ]]",
				studentDao.getById(1).toString());

	}

	@Test
	void testUpdateForStudents_shouldCheckIfStudentHasBeenUpdated_whenMethodIsExecuted() {

		studentDao.save(new Student(1, 4, "John", "Doe"));
		String[] params = { "99", "UPDATED", "UPDATED" };
		studentDao.update(1, params);
		assertEquals(
				"Optional[Student [studentId=1, groupId=99, firstName=UPDATED                                                                                                                                                                                                                                                        , lastName=UPDATED                                                                                                                                                                                                                                                        ]]",
				studentDao.getById(1).toString());

	}

	@Test
	void testGetByFirstNameForStudents_shouldCheckIfStudentHasBeenFoundByFirstName_whenMethodIsExecuted() {

		studentDao.save(new Student(1, 5, "John", "Doe"));
		assertEquals(
				"Optional[Student [studentId=1, groupId=5, firstName=John                                                                                                                                                                                                                                                           , lastName=Doe                                                                                                                                                                                                                                                            ]]",
				studentDao.getByFirstName("John").toString());

	}

	@Test
	void testGetByLastNameForStudents_shouldCheckIfStudentHasBeenFoundByLastName_whenMethodIsExecuted() {

		studentDao.save(new Student(1, 6, "John", "Doe"));
		assertEquals(
				"Optional[Student [studentId=1, groupId=6, firstName=John                                                                                                                                                                                                                                                           , lastName=Doe                                                                                                                                                                                                                                                            ]]",
				studentDao.getByLastName("Doe").toString());

	}

	@Test
	void testGetByGroupIdForStudents_shouldCheckIfStudentHasBeenFoundByGroupId_whenMethodIsExecuted() {

		studentDao.save(new Student(1, 7, "John", "Doe"));
		assertEquals(
				"Optional[Student [studentId=1, groupId=7, firstName=John                                                                                                                                                                                                                                                           , lastName=Doe                                                                                                                                                                                                                                                            ]]",
				studentDao.getByGroupId(7).toString());

	}

	@Test
	void testFindStudentsByCourse_shouldReturnAllStudentsRelatedToCourse_whenArgumentIsCourseName() {

		courseDao.save(new Course(1, "Math", "Math Course"));
		studentDao.save(new Student(1, 8, "John", "Doe"));
		studentDao.save(new Student(2, 9, "Jane", "Doe"));
		studentDao.addStudentToCourse(1, 1);
		studentDao.addStudentToCourse(2, 1);
		assertEquals(
				"[Student [studentId=1, groupId=8, firstName=John                                                                                                                                                                                                                                                           , lastName=Doe                                                                                                                                                                                                                                                            ], Student [studentId=2, groupId=9, firstName=Jane                                                                                                                                                                                                                                                           , lastName=Doe                                                                                                                                                                                                                                                            ]]",
				studentDao.findStudentsByCourse("Math").toString());

	}

}