package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcStudentDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;
import ua.com.foxminded.springbootjdbcapi.task22.models.StudentCourse;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

	@InjectMocks
	private StudentService studentService;

	@Mock
	private JdbcStudentDao studentDao;

	@Test
	void testGetByGroupId() {

		Student student = new Student(1, 1, "John", "Doe");
		Optional<Student> optionalStudent = Optional.of(student);
		Mockito.when(studentDao.getByGroupId(Mockito.any(Integer.class))).thenReturn(optionalStudent);
		Student result = studentService.getByGroupId(1);
		Mockito.verify(studentDao).getByGroupId(Mockito.any(Integer.class));
		assertEquals(1, result.getStudentId());
		assertEquals(1, result.getGroupId());
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		assertEquals(student, result);

	}

	@Test
	void testGetByFirstName() {

		Student student = new Student(1, 2, "John", "Doe");
		Optional<Student> optionalStudent = Optional.of(student);
		Mockito.when(studentDao.getByFirstName(Mockito.any(String.class))).thenReturn(optionalStudent);
		Student result = studentService.getByFirstName("John");
		Mockito.verify(studentDao).getByFirstName(Mockito.any(String.class));
		assertEquals(1, result.getStudentId());
		assertEquals(2, result.getGroupId());
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		assertEquals(student, result);

	}

	@Test
	void testGetByLastName() {

		Student student = new Student(1, 3, "John", "Doe");
		Optional<Student> optionalStudent = Optional.of(student);
		Mockito.when(studentDao.getByLastName(Mockito.any(String.class))).thenReturn(optionalStudent);
		Student result = studentService.getByLastName("Doe");
		Mockito.verify(studentDao).getByLastName(Mockito.any(String.class));
		assertEquals(1, result.getStudentId());
		assertEquals(3, result.getGroupId());
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		assertEquals(student, result);

	}

	@Test
	void testGetById() {

		Student student = new Student(1, 4, "John", "Doe");
		Optional<Student> optionalStudent = Optional.of(student);
		Mockito.when(studentDao.getById(Mockito.any(Integer.class))).thenReturn(optionalStudent);
		Student result = studentService.getById(1);
		Mockito.verify(studentDao).getById(Mockito.any(Integer.class));
		assertEquals(1, result.getStudentId());
		assertEquals(4, result.getGroupId());
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		assertEquals(student, result);

	}

	@Test
	void testGetAll() {

		Student student = new Student(1, 5, "John", "Doe");
		List<Student> studentList = new ArrayList<>();
		studentList.add(student);
		Mockito.when(studentDao.getAll()).thenReturn(studentList);
		List<Student> results = studentService.getAll();
		Mockito.verify(studentDao).getAll();
		assertThat(results).hasSize(1);
		Student result = results.get(0);
		assertEquals(1, result.getStudentId());
		assertEquals(5, result.getGroupId());
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		assertEquals(studentList, results);

	}

	@Test
	void testSave() {

		Student student = new Student(1, 7, "John", "Doe");
		Mockito.when(studentDao.save(Mockito.any(Student.class))).thenReturn(student);
		Student result = studentService.save(student);
		Mockito.verify(studentDao).save(Mockito.any(Student.class));
		assertEquals(1, result.getStudentId());
		assertEquals(7, result.getGroupId());
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		assertEquals(student, result);

	}

	@Test
	void testUpdate() {

		String[] params = { "99", "UPDATED", "UPDATED" };
		Student updatedStudent = new Student(1, 99, "UPDATED", "UPDATED");
		Mockito.when(studentDao.update(Mockito.any(Integer.class), Mockito.any(String[].class)))
				.thenReturn(updatedStudent);
		assertThat(studentService.update(1, params)).isEqualTo(updatedStudent);
		Mockito.verify(studentDao).update(Mockito.any(Integer.class), Mockito.any(String[].class));

	}

	@Test
	void testDeleteWhenOptionalIsEmpty() {

		Mockito.when(studentDao.getById(Mockito.any(Integer.class))).thenReturn(Optional.empty());
		assertEquals(true, studentService.delete(1));
		Mockito.verify(studentDao).getById(Mockito.any(Integer.class));

	}

	@Test
	void testDeleteWhenOptionalIsNotEmpty() {

		Student student = new Student(1, 8, "John", "Doe");
		Optional<Student> optionalStudent = Optional.of(student);
		Mockito.when(studentDao.getById(Mockito.any(Integer.class))).thenReturn(optionalStudent);
		assertEquals(false, studentService.delete(1));
		Mockito.verify(studentDao).getById(Mockito.any(Integer.class));

	}

	@Test
	void testFindStudentsByCourse() {

		Student student1 = new Student(1, 9, "John", "Doe");
		Student student2 = new Student(2, 10, "Jane", "Miller");
		List<Student> studentList = new ArrayList<>();
		studentList.add(student1);
		studentList.add(student2);
		Mockito.when(studentDao.findStudentsByCourse(Mockito.any(String.class))).thenReturn(studentList);
		assertThat(studentService.findStudentsByCourse("Math")).isEqualTo(studentList);
		Mockito.verify(studentDao).findStudentsByCourse(Mockito.any(String.class));

	}

	@Test
	void testAddStudentToCourse() {

		StudentCourse studentCourse = new StudentCourse(1, 1);
		Mockito.when(studentDao.addStudentToCourse(Mockito.any(Integer.class), Mockito.any(Integer.class)))
				.thenReturn(studentCourse);
		assertThat(studentService.addStudentToCourse(1, 1)).isEqualTo(studentCourse);
		Mockito.verify(studentDao).addStudentToCourse(Mockito.any(Integer.class), Mockito.any(Integer.class));

	}

	@Test
	void testRemoveStudentFromCourse() {

		assertEquals(true, studentService.removeStudentFromCourse(1, 1));

	}

}