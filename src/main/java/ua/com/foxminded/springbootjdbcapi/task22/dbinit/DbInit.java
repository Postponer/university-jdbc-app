package ua.com.foxminded.springbootjdbcapi.task22.dbinit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
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
	private final JdbcTemplate jdbcTemplate;
	private CourseService courseService;
	private GroupService groupService;
	private StudentService studentService;
	Logger logger = LoggerFactory.getLogger(DbInit.class);

	public DbInit(JdbcTemplate jdbcTemplate, CourseService courseService, GroupService groupService,
			StudentService studentService) {

		this.jdbcTemplate = jdbcTemplate;
		this.courseService = courseService;
		this.groupService = groupService;
		this.studentService = studentService;

	}

	public void createNCourses(int numberToGenerate) {
		
		logger.debug("Entering courses creation endpoint");

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
		
		logger.info(numberToGenerate + " courses have been created");

	}

	public void createNGroups(int numberToGenerate) {
		
		logger.debug("Entering groups creation endpoint");

		for (int i = 0; i < numberToGenerate; i++) {

			String groupName = RandomStringUtils.randomAlphabetic(2).toUpperCase() + "-"
					+ Generator.randomInRange(10, 99);

			groupService.save(new Group(0, groupName));

		}
		
		logger.info(numberToGenerate + " groups have been created");

	}

	public void createNStudents(int numberToGenerate) {
		
		logger.debug("Entering students creation endpoint");

		for (int i = 0; i < numberToGenerate; i++) {

			int randomGroup = Generator.randomInRange(1, NUMBER_OF_GROUPS);
			String randomFirstName = Generator.getRandomNames(numberToGenerate).get(i);
			String randomLastName = Generator.getRandomSurnames(numberToGenerate).get(i);

			studentService.save(new Student(0, randomGroup, randomFirstName, randomLastName));

		}
		
		logger.info(numberToGenerate + " students have been created");

	}

	public void removeExcessiveStudentsFromGroups() {
		
		logger.debug("Entering excessive students removal from groups endpoint");

		for (int i = 0; i < NUMBER_OF_GROUPS; i++) {

			int count = jdbcTemplate.queryForObject("select count(*) from students where group_id = ?", Integer.class,
					new Object[] { i + 1 });

			if (count > MAX_NUMBER_OF_STUDENTS_IN_GROUP || count < MIN_NUMBER_OF_STUDENTS_IN_GROUP) {

				jdbcTemplate.update("update students set group_id = null where group_id = ?", (i + 1));

			}

		}

		logger.info("Excessive students have been removed from groups");
		
	}

	public void assignCoursesRandomlyToStudents(Map<Integer, ArrayList<Integer>> studentsAndCourses) {
		
		logger.debug("Entering random course assigning to students endpoint");

		for (int i = 0; i < studentsAndCourses.size(); i++) {

			int studentId = (int) studentsAndCourses.keySet().toArray()[i];

			ArrayList<Integer> studentCourses = studentsAndCourses.get(studentId);

			for (int j = 0; j < studentCourses.size(); j++) {

				int courseId = studentCourses.get(j);

				jdbcTemplate.update("insert into students_courses(student_id, course_id) values (?, ?)", studentId,
						courseId);

			}

		}
		
		logger.info("Courses have been randomly assigned to students");

	}
	
	public void clearDatabaseFacade() {
		
		logger.debug("Entering clearing of database facade endpoint");
		
		jdbcTemplate.update("truncate table students_courses, students, groups, courses");
		jdbcTemplate.update("ALTER SEQUENCE students_student_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE groups_group_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE courses_course_id_seq RESTART WITH 1");
		
		logger.info("Database facade has been cleared");
		
	}
	
	public boolean checkIfDatabaseIsEmpty() {
		
		logger.debug("Entering check if database is empty endpoint");

		int rowCount = jdbcTemplate.queryForObject("select sum(n_live_tup) from pg_stat_user_tables", Integer.class);

		if (rowCount == 0) {

			logger.info("Database is empty");
			return true;

		}
		
		logger.info("Database is not empty");
		return false;

	}

}