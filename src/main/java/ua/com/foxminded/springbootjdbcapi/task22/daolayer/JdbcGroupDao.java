package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ua.com.foxminded.springbootjdbcapi.task22.models.Group;

@Repository
public class JdbcGroupDao {

	private final EntityManager entityManager;

	public JdbcGroupDao(EntityManager entityManager) {

		this.entityManager = entityManager;

	}

	public Optional<Group> getByName(String groupName) {

		Group group = entityManager.createQuery("select g from Group g where g.groupName = ?1", Group.class)
				.setParameter(1, groupName).getSingleResult();
		return Optional.ofNullable(group);

	}

	public Optional<Group> getById(int groupId) {

		Group group = entityManager.find(Group.class, groupId);
		return Optional.ofNullable(group);

	}

	public List<Group> getAll() {

		return entityManager.createQuery("select g from Group g", Group.class).getResultList();

	}

	@Transactional
	public Group save(Group group) {

		entityManager.persist(group);
		return getById(group.getGroupId()).get();

	}

	@Transactional
	public Group update(int groupId, String[] params) {

		Group group = new Group(groupId, params[0]);
		return entityManager.merge(group);

	}

	public void delete(int groupId) {

		Group group = entityManager.find(Group.class, groupId);
		entityManager.remove(group);

	}

	public List<Group> findGroupsByStudentNumber(int studentNumber) {

		return entityManager.createQuery(
				"SELECT g FROM Group g LEFT JOIN Student s ON g.groupId = s.groupId GROUP BY g.groupId HAVING COUNT(s.groupId) <= ?1",
				Group.class).setParameter(1, (long) studentNumber).getResultList();

	}

}