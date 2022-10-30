package ua.com.foxminded.springbootjdbcapi.task22;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StudentRowMapper implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {

		int groupId = rs.getInt("group_id");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");

		return new Student(groupId, firstName, lastName);
		
	}

}
