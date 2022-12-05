package ua.com.foxminded.springbootjdbcapi.task22.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.springbootjdbcapi.task22.models.StudentCourse;

@Component
public class StudentCourseRowMapper implements RowMapper<StudentCourse> {

	@Override
	public StudentCourse mapRow(ResultSet rs, int rowNum) throws SQLException {

		int studentId = rs.getInt("student_id");
		int courseId = rs.getInt("course_id");

		return new StudentCourse(studentId, courseId);

	}

}
