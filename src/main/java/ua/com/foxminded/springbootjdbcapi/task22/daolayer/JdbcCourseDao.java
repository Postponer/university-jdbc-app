package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.springbootjdbcapi.task22.models.Course;
import ua.com.foxminded.springbootjdbcapi.task22.rowmappers.CourseRowMapper;

@Repository
public class JdbcCourseDao {

	private final JdbcTemplate jdbcTemplate;
	private final CourseRowMapper courseRowMapper;

	public JdbcCourseDao(JdbcTemplate jdbcTemplate, CourseRowMapper courseRowMapper) {

		this.jdbcTemplate = jdbcTemplate;
		this.courseRowMapper = courseRowMapper;

	}

	public Optional<Course> getByName(String courseName) {

		try {

			return Optional.ofNullable(jdbcTemplate.queryForObject("select * from courses where course_name = ?",
					courseRowMapper, courseName));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	public Optional<Course> getByDescription(String courseDescription) {

		try {

			return Optional.ofNullable(jdbcTemplate.queryForObject("select * from courses where course_description = ?",
					courseRowMapper, courseDescription));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	public Optional<Course> getById(int courseId) {

		try {

			return Optional.ofNullable(jdbcTemplate.queryForObject("select * from courses where course_id = ?", courseRowMapper,
					courseId));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	public List<Course> getAll() {

		return jdbcTemplate.query("select * from courses", courseRowMapper);

	}

	public void save(Course course) {

		jdbcTemplate.update("insert into courses (course_name, course_description) values (?, ?)",
				course.getCourseName(), course.getCourseDescription());

	}

	public void update(int courseId, String[] params) {

		jdbcTemplate.update("update courses set course_name = ?, course_description = ? where course_id = ?", params[0],
				params[1], courseId);

	}

	public void delete(int courseId) {

		jdbcTemplate.update("delete from courses where course_id = ?", courseId);

	}

}