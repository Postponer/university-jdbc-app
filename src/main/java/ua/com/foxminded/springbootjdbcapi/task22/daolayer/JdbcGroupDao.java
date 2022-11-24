package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.springbootjdbcapi.task22.models.Group;
import ua.com.foxminded.springbootjdbcapi.task22.rowmappers.GroupRowMapper;

@Repository
public class JdbcGroupDao {

	private final JdbcTemplate jdbcTemplate;
	private final GroupRowMapper groupRowMapper;

	public JdbcGroupDao(JdbcTemplate jdbcTemplate, GroupRowMapper groupRowMapper) {

		this.jdbcTemplate = jdbcTemplate;
		this.groupRowMapper = groupRowMapper;

	}

	public Optional<Group> getByName(String groupName) {

		try {

			return Optional.ofNullable(jdbcTemplate.queryForObject("select * from groups where group_name = ?",
					groupRowMapper, groupName));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	public Optional<Group> getById(int groupId) {

		try {

			return Optional.ofNullable(
					jdbcTemplate.queryForObject("select * from groups where group_id = ?", groupRowMapper, groupId));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	public List<Group> getAll() {

		return jdbcTemplate.query("select * from groups", groupRowMapper);

	}

	public Group save(Group group) {

		jdbcTemplate.update("insert into groups (group_name) values (?)", group.getGroupName());
		return jdbcTemplate.queryForObject("select * from groups where group_id = ?", groupRowMapper,
				group.getGroupId());

	}

	public Group update(int groupId, String[] params) {

		jdbcTemplate.update("update groups set group_name = ? where group_id = ?", params[0], groupId);
		return jdbcTemplate.queryForObject("select * from groups where group_id = ?", groupRowMapper, groupId);

	}

	public void delete(int groupId) {

		jdbcTemplate.update("delete from groups where group_id = ?", groupId);

	}

	public List<Group> findGroupsByStudentNumber(int studentNumber) {

		return jdbcTemplate.query(
				"SELECT groups.* FROM groups LEFT JOIN students ON groups.group_id = students.group_id GROUP BY groups.group_id HAVING COUNT(students.group_id) <= ?",
				groupRowMapper, studentNumber);

	}

}