package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.springbootjdbcapi.task22.consolemenu.ConsoleMenu;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcCourseDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Course;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

	@InjectMocks
	private CourseService courseService;

	@Mock
	private ConsoleMenu consoleMenu;

	@Mock
	private JdbcCourseDao courseDao;

	@Mock
	private DbInitService dbInitService;

	@Test
	void testGetByName() {

		Course course = new Course(1, "Math", "Math course");
		Optional<Course> optionalCourse = Optional.of(course);
		Mockito.when(courseDao.getByName("Math")).thenReturn(optionalCourse);
		Optional<Course> result = courseService.getByName("Math");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getCourseId());
		assertEquals("Math", result.get().getCourseName());
		assertEquals("Math course", result.get().getCourseDescription());
		assertThat(courseService.getByName("Math")).isEqualTo(optionalCourse);

	}

	@Test
	void testGetByDescription() {

		Course course = new Course(1, "Math", "Math course");
		Optional<Course> optionalCourse = Optional.of(course);
		Mockito.when(courseDao.getByDescription("Math course")).thenReturn(optionalCourse);
		Optional<Course> result = courseService.getByDescription("Math course");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getCourseId());
		assertEquals("Math", result.get().getCourseName());
		assertEquals("Math course", result.get().getCourseDescription());
		assertThat(courseService.getByDescription("Math course")).isEqualTo(optionalCourse);

	}

	@Test
	void testGetById() {

		Course course = new Course(1, "Math", "Math course");
		Optional<Course> optionalCourse = Optional.of(course);
		Mockito.when(courseDao.getById(1)).thenReturn(optionalCourse);
		Optional<Course> result = courseService.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getCourseId());
		assertEquals("Math", result.get().getCourseName());
		assertEquals("Math course", result.get().getCourseDescription());
		assertThat(courseService.getById(1)).isEqualTo(optionalCourse);

	}

	@Test
	void testGetAll() {

		Course course1 = new Course(1, "Math", "Math course");
		Course course2 = new Course(1, "Biology", "Biology course");
		List<Course> courseList = new ArrayList<>();
		courseList.add(course1);
		courseList.add(course2);
		Mockito.when(courseDao.getAll()).thenReturn(courseList);
		List<Course> results = courseService.getAll();
		assertThat(results).hasSize(2);
		Course result1 = results.get(0);
		assertEquals(1, result1.getCourseId());
		assertEquals("Math", result1.getCourseName());
		assertEquals("Math course", result1.getCourseDescription());
		Course result2 = results.get(1);
		assertEquals(1, result2.getCourseId());
		assertEquals("Biology", result2.getCourseName());
		assertEquals("Biology course", result2.getCourseDescription());
		assertThat(courseService.getAll()).isEqualTo(courseList);

	}

	@Test
	void testSave() {

		Course course = new Course(1, "Math", "Math course");
		Mockito.when(courseDao.save(course)).thenReturn(course);
		Course result = courseService.save(course);
		assertEquals(1, result.getCourseId());
		assertEquals("Math", result.getCourseName());
		assertEquals("Math course", result.getCourseDescription());
		assertThat(courseService.save(course)).isEqualTo(course);

	}

	@Test
	void testUpdate() {

		String[] params = { "UPDATED", "UPDATED" };
		Course updatedCourse = new Course(1, "UPDATED", "UPDATED");
		Mockito.when(courseDao.update(1, params)).thenReturn(updatedCourse);
		assertThat(courseService.update(1, params)).isEqualTo(updatedCourse);

	}

	@Test
	void testDeleteWhenOptionalIsEmpty() {

		Mockito.when(courseDao.getById(1)).thenReturn(Optional.empty());
		assertEquals(true, courseService.delete(1));

	}

	@Test
	void testDeleteWhenOptionalIsNotEmpty() {

		Course course = new Course(1, "Math", "Math course");
		Optional<Course> optionalCourse = Optional.of(course);
		Mockito.when(courseDao.getById(1)).thenReturn(optionalCourse);
		assertEquals(false, courseService.delete(1));

	}

}