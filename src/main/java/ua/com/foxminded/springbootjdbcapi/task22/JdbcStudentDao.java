package ua.com.foxminded.springbootjdbcapi.task22;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcStudentDao implements Dao<Student>, StudentDao {

	public static final String FIND_BY_GROUP_ID = "select * from students where group_id = ?";
	public static final String FIND_BY_FIRST_NAME = "select * from students where first_name = ?";
	public static final String FIND_BY_LAST_NAME = "select * from students where last_name = ?";
	public static final String FIND_BY_ID = "select * from students where student_id = ?";
	public static final String FIND_ALL = "select * from students";
	public static final String INSERT_VALUES = "insert into students (group_id, first_name, last_name) values (?, ?, ?)";
	public static final String UPDATE_VALUES = "update students (group_id, first_name, last_name) set values (?, ?, ?) where student_id = ?";
	public static final String DELETE_VALUES = "delete from students where student_id = ?";

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Student> studentRowMapper = new StudentRowMapper();

	public JdbcStudentDao(JdbcTemplate jdbcTemplate) {

		this.jdbcTemplate = jdbcTemplate;

	}

	@Override
	public Optional<Student> getByGroupId(String groupId) {

		try {

			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_GROUP_ID, studentRowMapper, groupId));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	@Override
	public Optional<Student> getByFirstName(String firstName) {

		try {

			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_FIRST_NAME, studentRowMapper, firstName));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	@Override
	public Optional<Student> getByLastName(String lastName) {

		try {

			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_LAST_NAME, studentRowMapper, lastName));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	@Override
	public Optional<Student> getById(int studentId) {

		try {

			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_ID, studentRowMapper, studentId));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	@Override
	public List<Student> getAll() {

		return jdbcTemplate.query(FIND_ALL, studentRowMapper);

	}

	@Override
	public void save(Student student) {

		jdbcTemplate.update(INSERT_VALUES, student.getGroupId(), student.getFirstName(), student.getLastName());

	}

	@Override
	public void update(int studentId, String[] params) {

		jdbcTemplate.update(UPDATE_VALUES, params[0], params[1], params[2], studentId);

	}

	@Override
	public void delete(int studentId) {

		jdbcTemplate.update(DELETE_VALUES, studentId);

	}

}