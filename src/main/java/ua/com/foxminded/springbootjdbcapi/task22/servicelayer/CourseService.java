package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.CourseDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Course;

@Service
public class CourseService {

	private CourseDao courseDao;
	Logger logger = LoggerFactory.getLogger(CourseService.class);

	public CourseService(CourseDao courseDao) {

		this.courseDao = courseDao;

	}

	public Course getByName(String courseName) {

		logger.debug("Getting course by name: {}", courseName);
		Course course = courseDao.getByName(courseName).orElse(null);
		logger.info("{} has been gotten by name: {}", course, courseName);

		return course;

	}

	public Course getByDescription(String courseDescription) {

		logger.debug("Getting course by description: {}", courseDescription);
		Course course = courseDao.getByDescription(courseDescription).orElse(null);
		logger.info("{} has been gotten by description: {}", course, courseDescription);

		return course;

	}

	public Course getById(int courseId) {

		logger.debug("Getting course by id: {}", courseId);
		Course course = courseDao.getById(courseId).orElse(null);
		logger.info("{} has been gotten by id: {}", course, courseId);

		return course;

	}

	public List<Course> getAll() {

		logger.debug("Getting all courses");
		List<Course> courseList = courseDao.getAll();
		logger.info("All courses have been gotten");

		return courseList;

	}

	public Course save(Course course) {

		logger.debug("Saving {}", course);
		courseDao.save(course.getCourseName(), course.getCourseDescription());
		Course savedCourse = courseDao.getById(course.getCourseId()).orElse(null);
		logger.info("{} has been saved", course);

		return savedCourse;

	}

	public Course update(int courseId, String courseName, String courseDescription) {

		logger.debug("Updating course with id: {} with this parameters: {}", courseId, courseName + ", " + courseDescription);
		courseDao.update(courseId, courseName, courseDescription);
		Course course = courseDao.getById(courseId).orElse(null);
		logger.info("Course with id: {} has been updated with this parameters: {}", courseId, courseName + ", " + courseDescription);

		return course;

	}

	public boolean delete(int courseId) {

		logger.debug("Deleting course with id: {}", courseId);

		try {

			courseDao.delete(courseId);
			logger.info("Course with id: {} has been deleted", courseId);
			return true;

		} catch (Exception e) {

			logger.error("Exception occurred during course deletion by id: {}, message. Exception: ", courseId, e);
			return false;

		}

	}

}