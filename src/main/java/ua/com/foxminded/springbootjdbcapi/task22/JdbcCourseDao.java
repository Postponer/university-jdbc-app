package ua.com.foxminded.springbootjdbcapi.task22;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCourseDao implements Dao<Course>, CourseDao {

	public static final String FIND_BY_NAME = "select * from courses where course_name = ?";
	public static final String FIND_BY_DESCRIPTION = "select * from courses where course_description = ?";
	public static final String FIND_BY_ID = "select * from courses where course_id = ?";
	public static final String FIND_ALL = "select * from courses";
	public static final String INSERT_VALUES = "insert into courses (course_name, course_description) values (?, ?)";
	public static final String UPDATE_VALUES = "update courses (course_name, course_description) set values (?, ?) where course_id = ?";
	public static final String DELETE_VALUES = "delete from courses where course_id = ?";
	
	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Course> courseRowMapper = new CourseRowMapper();

	public JdbcCourseDao(JdbcTemplate jdbcTemplate) {

		this.jdbcTemplate = jdbcTemplate;

	}

	@Override
	public Optional<Course> getByName(String courseName) {

		try {

			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_NAME, courseRowMapper, courseName));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	@Override
	public Optional<Course> getByDescription(String courseDescription) {

		try {

			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_DESCRIPTION, courseRowMapper, courseDescription));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	@Override
	public Optional<Course> getById(int courseId) {
		
		try {

			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_ID, courseRowMapper, courseId));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}
		
	}

	@Override
	public List<Course> getAll() {
		
		return jdbcTemplate.query(FIND_ALL, courseRowMapper);
				
	}

	@Override
	public void save(Course course) {

		jdbcTemplate.update(INSERT_VALUES, course.getCourseName(), course.getCourseDescription());
		
	}

	@Override
	public void update(int courseId, String[] params) {
		
		jdbcTemplate.update(UPDATE_VALUES, params[0], params[1], courseId);
		
	}

	@Override
	public void delete(int courseId) {

		jdbcTemplate.update(DELETE_VALUES, courseId);
		
	}
}