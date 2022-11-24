package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.com.foxminded.springbootjdbcapi.task22.Application;
import ua.com.foxminded.springbootjdbcapi.task22.consolemenu.ConsoleMenu;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcStudentDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;
import ua.com.foxminded.springbootjdbcapi.task22.models.StudentCourse;

@SpringBootTest(classes = { Application.class })
class StudentServiceTest {

	@Autowired
	private StudentService studentService;

	@MockBean
	private ConsoleMenu consoleMenu;

	@MockBean
	private JdbcStudentDao studentDao;

	@MockBean
	private DbInitService dbInitService;

	@Test
	void testGetByGroupId() {

		Student student = new Student(1, 1, "John", "Doe");
		Optional<Student> optionalStudent = Optional.of(student);
		Mockito.when(studentDao.getByGroupId(1)).thenReturn(optionalStudent);
		Optional<Student> result = studentService.getByGroupId(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(1, result.get().getGroupId());
		assertEquals("John", result.get().getFirstName());
		assertEquals("Doe", result.get().getLastName());
		assertThat(studentService.getByGroupId(1)).isEqualTo(optionalStudent);

	}

	@Test
	void testGetByFirstName() {

		Student student = new Student(1, 2, "John", "Doe");
		Optional<Student> optionalStudent = Optional.of(student);
		Mockito.when(studentDao.getByFirstName("John")).thenReturn(optionalStudent);
		Optional<Student> result = studentService.getByFirstName("John");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(2, result.get().getGroupId());
		assertEquals("John", result.get().getFirstName());
		assertEquals("Doe", result.get().getLastName());
		assertThat(studentService.getByFirstName("John")).isEqualTo(optionalStudent);

	}

	@Test
	void testGetByLastName() {

		Student student = new Student(1, 3, "John", "Doe");
		Optional<Student> optionalStudent = Optional.of(student);
		Mockito.when(studentDao.getByLastName("Doe")).thenReturn(optionalStudent);
		Optional<Student> result = studentService.getByLastName("Doe");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(3, result.get().getGroupId());
		assertEquals("John", result.get().getFirstName());
		assertEquals("Doe", result.get().getLastName());
		assertThat(studentService.getByLastName("Doe")).isEqualTo(optionalStudent);

	}

	@Test
	void testGetById() {

		Student student = new Student(1, 4, "John", "Doe");
		Optional<Student> optionalStudent = Optional.of(student);
		Mockito.when(studentDao.getById(1)).thenReturn(optionalStudent);
		Optional<Student> result = studentService.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getStudentId());
		assertEquals(4, result.get().getGroupId());
		assertEquals("John", result.get().getFirstName());
		assertEquals("Doe", result.get().getLastName());
		assertThat(studentService.getById(1)).isEqualTo(optionalStudent);

	}

	@Test
	void testGetAll() {

		Student student1 = new Student(1, 5, "John", "Doe");
		Student student2 = new Student(2, 6, "Jane", "Miller");
		List<Student> studentList = new ArrayList<>();
		studentList.add(student1);
		studentList.add(student2);
		Mockito.when(studentDao.getAll()).thenReturn(studentList);
		List<Student> results = studentService.getAll();
		assertThat(results).hasSize(2);
		Student result1 = results.get(0);
		assertEquals(1, result1.getStudentId());
		assertEquals(5, result1.getGroupId());
		assertEquals("John", result1.getFirstName());
		assertEquals("Doe", result1.getLastName());
		Student result2 = results.get(1);
		assertEquals(2, result2.getStudentId());
		assertEquals(6, result2.getGroupId());
		assertEquals("Jane", result2.getFirstName());
		assertEquals("Miller", result2.getLastName());
		assertThat(studentService.getAll()).isEqualTo(studentList);

	}

	@Test
	void testSave() {

		Student student = new Student(1, 7, "John", "Doe");
		Mockito.when(studentDao.save(student)).thenReturn(student);
		Student result = studentService.save(student);
		assertEquals(1, result.getStudentId());
		assertEquals(7, result.getGroupId());
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		assertThat(studentService.save(student)).isEqualTo(student);

	}

	@Test
	void testUpdate() {

		String[] params = { "99", "UPDATED", "UPDATED" };
		Student updatedStudent = new Student(1, 99, "UPDATED", "UPDATED");
		Mockito.when(studentDao.update(1, params)).thenReturn(updatedStudent);
		assertThat(studentService.update(1, params)).isEqualTo(updatedStudent);

	}

	@Test
	void testDeleteWhenOptionalIsEmpty() {

		Mockito.when(studentDao.getById(1)).thenReturn(Optional.empty());
		assertEquals(true, studentService.delete(1));

	}

	@Test
	void testDeleteWhenOptionalIsNotEmpty() {

		Student student = new Student(1, 8, "John", "Doe");
		Optional<Student> optionalStudent = Optional.of(student);
		Mockito.when(studentDao.getById(1)).thenReturn(optionalStudent);
		assertEquals(false, studentService.delete(1));

	}

	@Test
	void testFindStudentsByCourse() {

		Student student1 = new Student(1, 9, "John", "Doe");
		Student student2 = new Student(2, 10, "Jane", "Miller");
		List<Student> studentList = new ArrayList<>();
		studentList.add(student1);
		studentList.add(student2);
		Mockito.when(studentDao.findStudentsByCourse("Math")).thenReturn(studentList);
		assertThat(studentService.findStudentsByCourse("Math")).isEqualTo(studentList);

	}

	@Test
	void testAddStudentToCourse() {

		StudentCourse studentCourse = new StudentCourse(1, 1);
		Mockito.when(studentDao.addStudentToCourse(1, 1)).thenReturn(studentCourse);
		assertThat(studentService.addStudentToCourse(1, 1)).isEqualTo(studentCourse);

	}

	@Test
	void testRemoveStudentFromCourse() {

		Mockito.when(studentDao.removeStudentFromCourse(1, 1)).thenReturn(true);
		assertEquals(true, studentService.removeStudentFromCourse(1, 1));

	}

}