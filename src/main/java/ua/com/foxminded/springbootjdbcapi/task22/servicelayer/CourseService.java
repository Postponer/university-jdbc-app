package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcCourseDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Course;

@Service
public class CourseService {

	private JdbcCourseDao courseDao;
	Logger logger = LoggerFactory.getLogger(CourseService.class);

	public CourseService(JdbcCourseDao courseDao) {

		this.courseDao = courseDao;

	}

	public Course getByName(String courseName) {

		logger.debug("Entering get course by name endpoint");
		Course course = courseDao.getByName(courseName).get();
		logger.info(course.toString() + " has been gotten by name: " + courseName);

		return course;

	}

	public Course getByDescription(String courseDescription) {

		logger.debug("Entering get course by description endpoint");
		Course course = courseDao.getByDescription(courseDescription).get();
		logger.info(course.toString() + " has been gotten by description: " + courseDescription);

		return course;

	}

	public Course getById(int courseId) {

		logger.debug("Entering get course by id endpoint");
		Course course = courseDao.getById(courseId).get();
		logger.info(course.toString() + " has been gotten by id: " + courseId);

		return course;

	}

	public List<Course> getAll() {

		logger.debug("Entering get all courses endpoint");
		List<Course> courseList = courseDao.getAll();
		logger.info("All courses have been gotten");

		return courseList;

	}

	public Course save(Course course) {

		logger.debug("Entering save course endpoint");
		Course savedCourse = courseDao.save(course);
		logger.info(course.toString() + " has been saved");

		return savedCourse;

	}

	public Course update(int courseId, String[] params) {

		logger.debug("Entering update course endpoint");
		Course course = courseDao.update(courseId, params);
		logger.info("Course with id: " + courseId + " has been updated with this parameters: " + params);

		return course;

	}

	public boolean delete(int courseId) {

		logger.debug("Entering delete course endpoint");
		courseDao.delete(courseId);

		try {

			getById(courseId);
			logger.info("Course with id: " + courseId + " has been deleted");

		} catch (Exception e) {

			return true;

		}

		logger.error("Unable to delete course with id: " + courseId);
		return false;

	}

}