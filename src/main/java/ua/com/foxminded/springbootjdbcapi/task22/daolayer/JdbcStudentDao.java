package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ua.com.foxminded.springbootjdbcapi.task22.models.Course;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;
import ua.com.foxminded.springbootjdbcapi.task22.models.StudentCourse;
import ua.com.foxminded.springbootjdbcapi.task22.models.StudentCoursePK;

@Repository
public class JdbcStudentDao {

	private final EntityManager entityManager;

	public JdbcStudentDao(EntityManager entityManager) {

		this.entityManager = entityManager;

	}

	public Optional<Student> getByGroupId(int groupId) {

		Student student = entityManager.createQuery("select s from Student s where s.groupId = ?1", Student.class)
				.setParameter(1, groupId).getSingleResult();
		return Optional.ofNullable(student);

	}

	public Optional<Student> getByFirstName(String firstName) {

		Student student = entityManager.createQuery("select s from Student s where s.firstName = ?1", Student.class)
				.setParameter(1, firstName).getSingleResult();
		return Optional.ofNullable(student);

	}

	public Optional<Student> getByLastName(String lastName) {

		Student student = entityManager.createQuery("select s from Student s where s.lastName = ?1", Student.class)
				.setParameter(1, lastName).getSingleResult();
		return Optional.ofNullable(student);

	}

	public Optional<Student> getById(int studentId) {

		Student student = entityManager.find(Student.class, studentId);
		return Optional.ofNullable(student);

	}

	public List<Student> getAll() {

		return entityManager.createQuery("select s from Student s", Student.class).getResultList();

	}

	@Transactional
	public Student save(Student student) {

		entityManager.persist(student);
		return getById(student.getStudentId()).get();

	}

	@Transactional
	public Student update(int studentId, String[] params) {

		Student student = new Student(studentId, Integer.parseInt(params[0]), params[1], params[2]);
		return entityManager.merge(student);

	}

	public void delete(int studentId) {

		Student student = entityManager.find(Student.class, studentId);
		entityManager.remove(student);
		
	}

	public List<Student> findStudentsByCourse(String courseName) {

		return entityManager
				.createQuery("SELECT s FROM Student s JOIN s.courses c WHERE c.courseName = ?1", Student.class)
				.setParameter(1, courseName).getResultList();

	}

	@Transactional
	public StudentCourse addStudentToCourse(int studentId, int courseId) {
		
		Student student = entityManager.find(Student.class, studentId);
		Course course = entityManager.find(Course.class, courseId);
		student.addCourse(course);
		entityManager.persist(student);
		return new StudentCourse(new StudentCoursePK(studentId, courseId));
		
	}

	@Transactional
	public void removeStudentFromCourse(int studentId, int courseId) {

		Course course = entityManager.find(Course.class, courseId);
		Student student = entityManager.find(Student.class, studentId);
		student.removeCourse(course);
		
	}

}