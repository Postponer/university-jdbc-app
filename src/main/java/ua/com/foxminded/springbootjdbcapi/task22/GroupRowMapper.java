package ua.com.foxminded.springbootjdbcapi.task22;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class GroupRowMapper implements RowMapper<Group> {

	@Override
	public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		String groupName = rs.getString("group_name");

		return new Group(groupName);
		
	}

}