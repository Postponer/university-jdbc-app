package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcStudentDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;
import ua.com.foxminded.springbootjdbcapi.task22.models.StudentCourse;

@Service
public class StudentService {

	private JdbcStudentDao studentDao;

	public StudentService(JdbcStudentDao studentDao) {

		this.studentDao = studentDao;

	}

	public Optional<Student> getByGroupId(int groupId) {

		return studentDao.getByGroupId(groupId);

	}

	public Optional<Student> getByFirstName(String firstName) {

		return studentDao.getByFirstName(firstName);

	}

	public Optional<Student> getByLastName(String lastName) {

		return studentDao.getByLastName(lastName);

	}

	public Optional<Student> getById(int studentId) {

		return studentDao.getById(studentId);

	}

	public List<Student> getAll() {

		return studentDao.getAll();

	}

	public Student save(Student student) {

		return studentDao.save(student);

	}

	public Student update(int studentId, String[] params) {

		return studentDao.update(studentId, params);

	}

	public boolean delete(int studentId) {

		studentDao.delete(studentId);

		Optional<Student> group = getById(studentId);

		if (group.isPresent()) {

			return false;

		}

		return true;

	}

	public List<Student> findStudentsByCourse(String courseName) {

		return studentDao.findStudentsByCourse(courseName);

	}

	public StudentCourse addStudentToCourse(int studentId, int courseId) {

		return studentDao.addStudentToCourse(studentId, courseId);

	}

	public boolean removeStudentFromCourse(int studentId, int courseId) {

		return studentDao.removeStudentFromCourse(studentId, courseId);

	}

}