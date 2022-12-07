package ua.com.foxminded.springbootjdbcapi.task22.dbinit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ua.com.foxminded.springbootjdbcapi.task22.generator.Generator;
import ua.com.foxminded.springbootjdbcapi.task22.models.Course;
import ua.com.foxminded.springbootjdbcapi.task22.models.Group;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;
import ua.com.foxminded.springbootjdbcapi.task22.servicelayer.CourseService;
import ua.com.foxminded.springbootjdbcapi.task22.servicelayer.GroupService;
import ua.com.foxminded.springbootjdbcapi.task22.servicelayer.StudentService;

@Component
public class DbInit {

	private static final int NUMBER_OF_GROUPS = 10;
	private static final int MAX_NUMBER_OF_STUDENTS_IN_GROUP = 30;
	private static final int MIN_NUMBER_OF_STUDENTS_IN_GROUP = 10;
	private final EntityManager entityManager;
	private CourseService courseService;
	private GroupService groupService;
	private StudentService studentService;
	Logger logger = LoggerFactory.getLogger(DbInit.class);

	public DbInit(CourseService courseService, GroupService groupService,
			StudentService studentService, EntityManager entityManager) {

		this.courseService = courseService;
		this.groupService = groupService;
		this.studentService = studentService;
		this.entityManager = entityManager;

	}

	public void createNCourses(int numberToGenerate) {

		logger.info("Generating {} courses", numberToGenerate);

		List<Course> courses = Generator.getCourses();

		int index = 0;

		for (int i = 0; i < courses.size(); i++) {

			index++;
			courseService.save(courses.get(i));

			if (index < numberToGenerate && (i + 1) == courses.size()) {

				i = -1;

			}

			if (index == numberToGenerate) {

				i = courses.size();

			}

		}

		logger.info("{} courses have been created", numberToGenerate);

	}

	public void createNGroups(int numberToGenerate) {

		logger.info("Generating {} groups", numberToGenerate);

		for (int i = 0; i < numberToGenerate; i++) {

			String groupName = RandomStringUtils.randomAlphabetic(2).toUpperCase() + "-"
					+ Generator.randomInRange(10, 99);

			groupService.save(new Group(0, groupName));

		}

		logger.info("{} groups have been created", numberToGenerate);

	}

	public void createNStudents(int numberToGenerate) {

		logger.info("Generating {} students", numberToGenerate);

		for (int i = 0; i < numberToGenerate; i++) {

			int randomGroup = Generator.randomInRange(1, NUMBER_OF_GROUPS);
			String randomFirstName = Generator.getRandomNames(numberToGenerate).get(i);
			String randomLastName = Generator.getRandomSurnames(numberToGenerate).get(i);

			studentService.save(new Student(0, randomGroup, randomFirstName, randomLastName));

		}

		logger.info("{} students have been created", numberToGenerate);

	}

	@Transactional
	public void removeExcessiveStudentsFromGroups() {

		logger.info("Removing excessive students from groups");

		for (int i = 0; i < NUMBER_OF_GROUPS; i++) {

			Long count = (Long) entityManager.createQuery("select count(s) from Student s where groupId = ?1")
					.setParameter(1, (i + 1)).getSingleResult();

			if (count > MAX_NUMBER_OF_STUDENTS_IN_GROUP || count < MIN_NUMBER_OF_STUDENTS_IN_GROUP) {

				entityManager.createQuery("update Student s set groupId = null where groupId = ?1")
						.setParameter(1, (i + 1)).executeUpdate();

			}

		}

		logger.info("Excessive students have been removed from groups");

	}

	public void assignCoursesRandomlyToStudents(Map<Integer, ArrayList<Integer>> studentsAndCourses) {

		logger.info("Assigning random courses to students");

		for (int i = 0; i < studentsAndCourses.size(); i++) {

			int studentId = (int) studentsAndCourses.keySet().toArray()[i];

			ArrayList<Integer> studentCourses = studentsAndCourses.get(studentId);

			for (int j = 0; j < studentCourses.size(); j++) {

				int courseId = studentCourses.get(j);

				studentService.addStudentToCourse(studentId, courseId);

			}

		}

		logger.info("Courses have been randomly assigned to students");

	}

	@Transactional
	public void clearDatabaseFacade() {

		logger.info("Clearing database facade");

		entityManager.createNativeQuery("truncate table students_courses, students, groups, courses").executeUpdate();
		entityManager.createNativeQuery("ALTER SEQUENCE students_student_id_seq RESTART WITH 1").executeUpdate();
		entityManager.createNativeQuery("ALTER SEQUENCE groups_group_id_seq RESTART WITH 1").executeUpdate();
		entityManager.createNativeQuery("ALTER SEQUENCE courses_course_id_seq RESTART WITH 1").executeUpdate();

		logger.info("Database facade has been cleared");

	}

	public boolean checkIfDatabaseIsEmpty() {

		logger.info("Checking if database is empty");

		BigDecimal rowCount = (BigDecimal) entityManager
				.createNativeQuery("select sum(n_live_tup) from pg_stat_user_tables").getSingleResult();

		if (rowCount.intValue() == 0) {

			logger.info("Database is empty");
			return true;

		}

		logger.info("Database is not empty");
		return false;

	}

}