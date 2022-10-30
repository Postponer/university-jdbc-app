package ua.com.foxminded.springbootjdbcapi.task22;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcGroupDao implements Dao<Group>, GroupDao {

	public static final String FIND_BY_NAME = "select * from groups where group_name = ?";
	public static final String FIND_BY_ID = "select * from groups where group_id = ?";
	public static final String FIND_ALL = "select * from groups";
	public static final String INSERT_VALUES = "insert into groups (group_name) values (?)";
	public static final String UPDATE_VALUES = "update groups (group_name) set values (?) where group_id = ?";
	public static final String DELETE_VALUES = "delete from groups where group_id = ?";

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Group> groupRowMapper = new GroupRowMapper();

	public JdbcGroupDao(JdbcTemplate jdbcTemplate) {

		this.jdbcTemplate = jdbcTemplate;

	}

	@Override
	public Optional<Group> getByName(String groupName) {

		try {

			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_NAME, groupRowMapper, groupName));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	@Override
	public Optional<Group> getById(int groupId) {

		try {

			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_ID, groupRowMapper, groupId));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	@Override
	public List<Group> getAll() {

		return jdbcTemplate.query(FIND_ALL, groupRowMapper);

	}

	@Override
	public void save(Group group) {

		jdbcTemplate.update(INSERT_VALUES, group.getGroupName());

	}

	@Override
	public void update(int groupId, String[] params) {

		jdbcTemplate.update(UPDATE_VALUES, params[0], groupId);

	}

	@Override
	public void delete(int groupId) {

		jdbcTemplate.update(DELETE_VALUES, groupId);

	}

}