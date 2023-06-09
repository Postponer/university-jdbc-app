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

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.CourseDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Course;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

	@InjectMocks
	private CourseService courseService;

	@Mock
	private CourseDao courseDao;

	@Test
	void testGetByName() {

		Course course = new Course(1, "Math", "Math course");
		Optional<Course> optionalCourse = Optional.of(course);
		Mockito.when(courseDao.getByName(Mockito.any(String.class))).thenReturn(optionalCourse);
		Course result = courseService.getByName("Math");
		Mockito.verify(courseDao).getByName("Math");
		assertEquals(1, result.getCourseId());
		assertEquals("Math", result.getCourseName());
		assertEquals("Math course", result.getCourseDescription());
		assertEquals(course, result);

	}

	@Test
	void testGetByDescription() {

		Course course = new Course(1, "Math", "Math course");
		Optional<Course> optionalCourse = Optional.of(course);
		Mockito.when(courseDao.getByDescription(Mockito.any(String.class))).thenReturn(optionalCourse);
		Course result = courseService.getByDescription("Math course");
		Mockito.verify(courseDao).getByDescription("Math course");
		assertEquals(1, result.getCourseId());
		assertEquals("Math", result.getCourseName());
		assertEquals("Math course", result.getCourseDescription());
		assertEquals(course, result);

	}

	@Test
	void testGetById() {

		Course course = new Course(1, "Math", "Math course");
		Optional<Course> optionalCourse = Optional.of(course);
		Mockito.when(courseDao.getById(Mockito.anyInt())).thenReturn(optionalCourse);
		Course result = courseService.getById(1);
		Mockito.verify(courseDao).getById(1);
		assertEquals(1, result.getCourseId());
		assertEquals("Math", result.getCourseName());
		assertEquals("Math course", result.getCourseDescription());
		assertEquals(course, result);

	}

	@Test
	void testGetAll() {

		Course course = new Course(1, "Math", "Math course");
		List<Course> courseList = new ArrayList<>();
		courseList.add(course);
		Mockito.when(courseDao.getAll()).thenReturn(courseList);
		List<Course> results = courseService.getAll();
		Mockito.verify(courseDao).getAll();
		assertThat(results).hasSize(1);
		Course result = results.get(0);
		assertEquals(1, result.getCourseId());
		assertEquals("Math", result.getCourseName());
		assertEquals("Math course", result.getCourseDescription());
		assertEquals(courseList, results);

	}

	@Test
	void testSave() {

		Course course = new Course(1, "Math", "Math course");
		Optional<Course> optionalCourse = Optional.of(course);
		Mockito.when(courseDao.getById(Mockito.anyInt())).thenReturn(optionalCourse);
		Course result = courseService.save(course);
		Mockito.verify(courseDao).save(course.getCourseName(), course.getCourseDescription());
		Mockito.verify(courseDao).getById(1);
		assertEquals(1, result.getCourseId());
		assertEquals("Math", result.getCourseName());
		assertEquals("Math course", result.getCourseDescription());
		assertEquals(course, result);

	}

	@Test
	void testUpdate() {

		Course updatedCourse = new Course(1, "UPDATED", "UPDATED");
		Optional<Course> optionalCourse = Optional.of(updatedCourse);
		Mockito.when(courseDao.getById(Mockito.anyInt())).thenReturn(optionalCourse);
		assertThat(courseService.update(1, "UPDATED", "UPDATED")).isEqualTo(updatedCourse);
		Mockito.verify(courseDao).update(1,"UPDATED", "UPDATED");
		Mockito.verify(courseDao).getById(1);

	}

	@Test
	void testDelete() {

		assertEquals(true, courseService.delete(1));
		Mockito.verify(courseDao).delete(1);

	}
	
	@Test
	void testDeleteWhenExceptionIsThrown() {
		
		Mockito.doThrow(NullPointerException.class).when(courseDao).delete(Mockito.anyInt());
		assertEquals(false, courseService.delete(1));
		Mockito.verify(courseDao).delete(1);
		
	}

}