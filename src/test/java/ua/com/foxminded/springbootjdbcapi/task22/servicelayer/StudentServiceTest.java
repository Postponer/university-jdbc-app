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

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.StudentDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

	@InjectMocks
	private StudentService studentService;

	@Mock
	private StudentDao studentDao;

	@Test
	void testGetByGroupId() {

		Student student = new Student(1, 1, "John", "Doe");
		Optional<Student> optionalStudent = Optional.of(student);
		Mockito.when(studentDao.getByGroupId(Mockito.any(Integer.class))).thenReturn(optionalStudent);
		Student result = studentService.getByGroupId(1);
		Mockito.verify(studentDao).getByGroupId(1);
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
		Mockito.verify(studentDao).getByFirstName("John");
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
		Mockito.verify(studentDao).getByLastName("Doe");
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
		Mockito.when(studentDao.getById(Mockito.anyInt())).thenReturn(optionalStudent);
		Student result = studentService.getById(1);
		Mockito.verify(studentDao).getById(1);
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
		Optional<Student> optionalStudent = Optional.of(student);
		Mockito.when(studentDao.getById(Mockito.anyInt())).thenReturn(optionalStudent);
		Student result = studentService.save(student);
		Mockito.verify(studentDao).save(7, "John", "Doe");
		Mockito.verify(studentDao).getById(1);
		assertEquals(1, result.getStudentId());
		assertEquals(7, result.getGroupId());
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		assertEquals(student, result);

	}

	@Test
	void testUpdate() {

		Student updatedStudent = new Student(1, 99, "UPDATED", "UPDATED");
		Optional<Student> optionalStudent = Optional.of(updatedStudent);
		Mockito.when(studentDao.getById(Mockito.anyInt())).thenReturn(optionalStudent);
		assertThat(studentService.update(1, 99, "UPDATED", "UPDATED")).isEqualTo(updatedStudent);
		Mockito.verify(studentDao).update(1, 99, "UPDATED", "UPDATED");
		Mockito.verify(studentDao).getById(1);

	}

	@Test
	void testDelete() {

		assertEquals(true, studentService.delete(1));
		Mockito.verify(studentDao).delete(1);

	}

	@Test
	void testDeleteWhenExceptionIsThrown() {

		Mockito.doThrow(NullPointerException.class).when(studentDao).delete(Mockito.anyInt());
		assertEquals(false, studentService.delete(1));
		Mockito.verify(studentDao).delete(1);

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
		Mockito.verify(studentDao).findStudentsByCourse("Math");

	}

	@Test
	void testAddStudentToCourse() {

		studentService.addStudentToCourse(1, 1);
		Mockito.verify(studentDao).addStudentToCourse(1, 1);

	}

	@Test
	void testRemoveStudentFromCourse() {

		assertEquals(true, studentService.removeStudentFromCourse(1, 1));
		Mockito.verify(studentDao).removeStudentFromCourse(1, 1);

	}

	@Test
	void testRemoveStudentFromCourseWhenExceptionIsThrown() {

		Mockito.doThrow(NullPointerException.class).when(studentDao).removeStudentFromCourse(Mockito.any(Integer.class),
				Mockito.any(Integer.class));
		assertEquals(false, studentService.removeStudentFromCourse(1, 1));
		Mockito.verify(studentDao).removeStudentFromCourse(1, 1);

	}

}