package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ua.com.foxminded.springbootjdbcapi.task22.models.Course;


@Repository
public class JdbcCourseDao {

	private final EntityManager entityManager;

	public JdbcCourseDao(EntityManager entityManager) {

		this.entityManager = entityManager;

	}

	public Optional<Course> getByName(String courseName) {

		Course course = entityManager.createQuery("select c from Course c where c.courseName = ?1", Course.class)
				.setParameter(1, courseName).getSingleResult();

		return Optional.ofNullable(course);

	}

	public Optional<Course> getByDescription(String courseDescription) {

		Course course = entityManager.createQuery("select c from Course c where c.courseDescription = ?1", Course.class)
				.setParameter(1, courseDescription).getSingleResult();

		return Optional.ofNullable(course);

	}

	public Optional<Course> getById(int courseId) {

		Course course = entityManager.find(Course.class, courseId);
		return Optional.ofNullable(course);

	}

	public List<Course> getAll() {

		return entityManager.createQuery("select c from Course c", Course.class).getResultList();

	}

	@Transactional
	public Course save(Course course) {

		entityManager.persist(course);
		return getById(course.getCourseId()).get();
		
	}

	@Transactional
	public Course update(int courseId, String[] params) {

		Course course = new Course(courseId, params[0], params[1]);
		return entityManager.merge(course);

	}

	public void delete(int courseId) {

		Course course = entityManager.find(Course.class, courseId);
		entityManager.remove(course);

	}

}