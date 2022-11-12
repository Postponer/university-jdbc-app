package ua.com.foxminded.springbootjdbcapi.task22.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.springbootjdbcapi.task22.models.Group;

@Component
public class GroupRowMapper implements RowMapper<Group> {

	@Override
	public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int groupId = rs.getInt("group_id");
		String groupName = rs.getString("group_name");

		return new Group(groupId, groupName);
		
	}

}