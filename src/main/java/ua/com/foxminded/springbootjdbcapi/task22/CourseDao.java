package ua.com.foxminded.springbootjdbcapi.task22;

import java.util.Optional;

public interface CourseDao {
	
	Optional<Course> getByName(String courseName);
	Optional<Course> getByDescription(String courseDescription);
	
}