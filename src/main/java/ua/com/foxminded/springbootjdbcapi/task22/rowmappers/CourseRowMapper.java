package ua.com.foxminded.springbootjdbcapi.task22.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.springbootjdbcapi.task22.models.Course;

@Component
public class CourseRowMapper implements RowMapper<Course> {

	@Override
	public Course mapRow(ResultSet rs, int rowNum) throws SQLException {

		int courseId = rs.getInt("course_id");
		String courseName = rs.getString("course_name");
		String courseDescription = rs.getString("course_description");

		return new Course(courseId, courseName, courseDescription);

	}

}
