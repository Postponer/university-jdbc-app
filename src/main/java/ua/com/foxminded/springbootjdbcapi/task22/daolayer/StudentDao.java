package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.springbootjdbcapi.task22.models.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Integer> {

	@Query(value = "SELECT s FROM Student s WHERE s.groupId = ?1")
	public Optional<Student> getByGroupId(int groupId);

	@Query(value = "SELECT s FROM Student s WHERE s.firstName = ?1")
	public Optional<Student> getByFirstName(String firstName);

	@Query(value = "SELECT s FROM Student s WHERE s.lastName = ?1")
	public Optional<Student> getByLastName(String lastName);

	@Query(value = "SELECT s FROM Student s WHERE s.studentId = ?1")
	public Optional<Student> getById(int studentId);

	@Query(value = "SELECT s FROM Student s")
	public List<Student> getAll();

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO students (group_id, first_name, last_name) VALUES (?1, ?2, ?3)", nativeQuery = true)
	public void save(int groupId, String firstName, String lastName);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE Student s SET s.groupId = ?2, s.firstName = ?3, s.lastName = ?4 WHERE studentId = ?1")
	public void update(int studentId, int groupId, String firstName, String lastName);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Student s WHERE studentId = ?1")
	public void delete(int studentId);

	@Query(value = "SELECT s FROM Student s JOIN s.courses c WHERE c.courseName = ?1")
	public List<Student> findStudentsByCourse(String courseName);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO students_courses (student_id, course_id) VALUES (?1, ?2)", nativeQuery = true)
	public void addStudentToCourse(int studentId, int courseId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM students_courses WHERE student_id = ?1 AND course_id = ?2", nativeQuery = true)	
	public void removeStudentFromCourse(int studentId, int courseId);

}