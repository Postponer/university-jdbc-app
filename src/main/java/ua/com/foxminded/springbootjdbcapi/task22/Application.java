package ua.com.foxminded.springbootjdbcapi.task22;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	private static JdbcCourseDao courseDao;
	private static JdbcGroupDao groupDao;
	private static JdbcStudentDao studentDao;

	public Application(JdbcCourseDao courseDao, JdbcGroupDao groupDao, JdbcStudentDao studentDao) {
		
		Application.courseDao = courseDao;
		Application.groupDao = groupDao;
		Application.studentDao = studentDao;
		
	}
	
	public static void start() {
		
		Course math = new Course("Math", "Math course");
		courseDao.save(math);
		
	}

	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
		start();
	
	}

}