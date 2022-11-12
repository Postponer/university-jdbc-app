package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.springbootjdbcapi.task22.models.Student;
import ua.com.foxminded.springbootjdbcapi.task22.rowmappers.StudentRowMapper;

@Repository
public class JdbcStudentDao {

	private final JdbcTemplate jdbcTemplate;
	private final StudentRowMapper studentRowMapper;

	public JdbcStudentDao(JdbcTemplate jdbcTemplate, StudentRowMapper studentRowMapper) {

		this.jdbcTemplate = jdbcTemplate;
		this.studentRowMapper = studentRowMapper;

	}

	public Optional<Student> getByGroupId(int groupId) {

		try {

			return Optional.ofNullable(jdbcTemplate.queryForObject("select * from students where group_id = ?",
					studentRowMapper, groupId));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	public Optional<Student> getByFirstName(String firstName) {

		try {

			return Optional.ofNullable(jdbcTemplate.queryForObject("select * from students where first_name = ?",
					studentRowMapper, firstName));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	public Optional<Student> getByLastName(String lastName) {

		try {

			return Optional.ofNullable(jdbcTemplate.queryForObject("select * from students where last_name = ?",
					studentRowMapper, lastName));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	public Optional<Student> getById(int studentId) {

		try {

			return Optional.ofNullable(jdbcTemplate.queryForObject("select * from students where student_id = ?",
					studentRowMapper, studentId));

		} catch (EmptyResultDataAccessException e) {

			return Optional.empty();

		}

	}

	public List<Student> getAll() {

		return jdbcTemplate.query("select * from students", studentRowMapper);

	}

	public void save(Student student) {

		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)",
				student.getGroupId(), student.getFirstName(), student.getLastName());

	}

	public void update(int studentId, String[] params) {

		jdbcTemplate.update("update students set group_id = ?, first_name = ?, last_name = ? where student_id = ?",
				Integer.parseInt(params[0]), params[1], params[2], studentId);

	}

	public void delete(int studentId) {

		jdbcTemplate.update("delete from students where student_id = ?", studentId);

	}

	public List<Student> findStudentsByCourse(String courseName) {

		List<Map<String, Object>> list = jdbcTemplate.queryForList(
				"SELECT * FROM courses JOIN students_courses ON courses.course_id = students_courses.course_id JOIN students ON students_courses.student_id = students.student_id WHERE course_name = ?",
				courseName);
		List<Student> studentList = new ArrayList<>();
		list.forEach(m -> {
			Student student = new Student((Integer) m.get("student_id"), (Integer) m.get("group_id"),
					(String) m.get("first_name"), (String) m.get("last_name"));
			studentList.add(student);
		});

		return studentList;

	}

	public void addStudentToCourse(int studentId, int courseId) {

		jdbcTemplate.update("INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)", studentId, courseId);

	}

	public void removeStudentFromCourse(int studentId, int courseId) {

		jdbcTemplate.update("DELETE FROM students_courses WHERE student_id = ? AND course_id = ?", studentId, courseId);

	}

}