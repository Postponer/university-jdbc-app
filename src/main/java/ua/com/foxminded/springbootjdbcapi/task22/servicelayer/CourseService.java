package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcCourseDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Course;

@Service
public class CourseService {

	private JdbcCourseDao courseDao;

	public CourseService(JdbcCourseDao courseDao) {

		this.courseDao = courseDao;

	}

	public Optional<Course> getByName(String courseName) {

		return courseDao.getByName(courseName);

	}

	public Optional<Course> getByDescription(String courseDescription) {

		return courseDao.getByDescription(courseDescription);

	}

	public Optional<Course> getById(int courseId) {

		return courseDao.getById(courseId);

	}

	public List<Course> getAll() {

		return courseDao.getAll();

	}

	public Course save(Course course) {

		return courseDao.save(course);

	}

	public Course update(int courseId, String[] params) {

		return courseDao.update(courseId, params);

	}

	public boolean delete(int courseId) {

		courseDao.delete(courseId);
		
		Optional<Course> course = getById(courseId);

		if (course.isPresent()) {

			return false;

		}

		return true;

	}

}