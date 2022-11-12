package ua.com.foxminded.springbootjdbcapi.task22.databasefacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcCourseDao;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcGroupDao;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcStudentDao;
import ua.com.foxminded.springbootjdbcapi.task22.generator.Generator;
import ua.com.foxminded.springbootjdbcapi.task22.models.Course;
import ua.com.foxminded.springbootjdbcapi.task22.models.Group;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;

@Repository
public class DatabaseFacade {

	private static final int NUMBER_OF_GROUPS = 10;
	private static final int MAX_NUMBER_OF_STUDENTS_IN_GROUP = 30;
	private static final int MIN_NUMBER_OF_STUDENTS_IN_GROUP = 10;
	private final JdbcTemplate jdbcTemplate;
	private JdbcCourseDao courseDao;
	private JdbcGroupDao groupDao;
	private JdbcStudentDao studentDao;

	public DatabaseFacade(JdbcTemplate jdbcTemplate, JdbcCourseDao courseDao, JdbcGroupDao groupDao,
			JdbcStudentDao studentDao) {

		this.jdbcTemplate = jdbcTemplate;
		this.courseDao = courseDao;
		this.groupDao = groupDao;
		this.studentDao = studentDao;

	}

	public void createNCourses(int numberToGenerate) {

		List<Course> courses = Generator.getCourses();

		int index = 0;

		for (int i = 0; i < courses.size(); i++) {

			index++;
			courseDao.save(courses.get(i));

			if (index < numberToGenerate && (i + 1) == courses.size()) {

				i = -1;

			}

			if (index == numberToGenerate) {

				i = courses.size();

			}

		}

	}

	public void createNGroups(int numberToGenerate) {

		for (int i = 0; i < numberToGenerate; i++) {

			String groupName = RandomStringUtils.randomAlphabetic(2).toUpperCase() + "-"
					+ Generator.randomInRange(10, 99);

			groupDao.save(new Group(0, groupName));

		}

	}

	public void createNStudents(int numberToGenerate) {

		for (int i = 0; i < numberToGenerate; i++) {

			int randomGroup = Generator.randomInRange(1, NUMBER_OF_GROUPS);
			String randomFirstName = Generator.getRandomNames(numberToGenerate).get(i);
			String randomLastName = Generator.getRandomSurnames(numberToGenerate).get(i);

			studentDao.save(new Student(0, randomGroup, randomFirstName, randomLastName));

		}

	}

	public void removeExcessiveStudentsFromGroups() {

		for (int i = 0; i < NUMBER_OF_GROUPS; i++) {

			int count = jdbcTemplate.queryForObject("select count(*) from students where group_id = ?", Integer.class,
					new Object[] { i + 1 });

			if (count > MAX_NUMBER_OF_STUDENTS_IN_GROUP || count < MIN_NUMBER_OF_STUDENTS_IN_GROUP) {

				jdbcTemplate.update("update students set group_id = null where group_id = ?", (i + 1));

			}

		}

	}

	public void assignCoursesRandomlyToStudents(Map<Integer, ArrayList<Integer>> studentsAndCourses) {

		for (int i = 0; i < studentsAndCourses.size(); i++) {

			int studentId = (int) studentsAndCourses.keySet().toArray()[i];

			ArrayList<Integer> studentCourses = studentsAndCourses.get(studentId);

			for (int j = 0; j < studentCourses.size(); j++) {

				int courseId = studentCourses.get(j);

				jdbcTemplate.update("insert into students_courses(student_id, course_id) values (?, ?)", studentId,
						courseId);

			}

		}

	}

}