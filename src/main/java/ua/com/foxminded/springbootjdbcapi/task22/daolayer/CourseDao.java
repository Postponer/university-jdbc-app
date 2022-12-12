package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.springbootjdbcapi.task22.models.Course;

@Repository
public interface CourseDao extends JpaRepository<Course, Integer> {

	@Query(value = "SELECT c FROM Course c WHERE c.courseName = ?1")
	public Optional<Course> getByName(String courseName);

	@Query(value = "SELECT c FROM Course c WHERE c.courseDescription = ?1")
	public Optional<Course> getByDescription(String courseDescription);

	@Query(value = "SELECT c FROM Course c WHERE c.courseId = ?1")
	public Optional<Course> getById(int courseId);

	@Query(value = "SELECT c FROM Course c")
	public List<Course> getAll();

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO courses (course_name, course_description) VALUES (?1, ?2)", nativeQuery = true)
	public void save(String courseName, String courseDescription);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE Course c SET c.courseName = ?2, c.courseDescription = ?3 WHERE courseId = ?1")
	public int update(int courseId, String courseName, String courseDescription);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Course c WHERE courseId = ?1")
	public void delete(int courseId);

}