package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ua.com.foxminded.springbootjdbcapi.task22.Application;
import ua.com.foxminded.springbootjdbcapi.task22.Config;
import ua.com.foxminded.springbootjdbcapi.task22.models.Group;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;

@ActiveProfiles("test")
@SpringBootTest(classes = { Config.class, Application.class })
class JdbcGroupDaoTest {

	private JdbcGroupDao groupDao;
	private JdbcStudentDao studentDao;
	private EntityManager entityManager;

	@Autowired
	public JdbcGroupDaoTest(JdbcGroupDao groupDao, JdbcStudentDao studentDao, EntityManager entityManager) {

		this.groupDao = groupDao;
		this.studentDao = studentDao;
		this.entityManager = entityManager;

	}

	@BeforeEach
	void reset() {

		entityManager.createNativeQuery("truncate table students_courses, students, groups").executeUpdate();
		entityManager.createNativeQuery("ALTER SEQUENCE groups_group_id_seq RESTART WITH 1").executeUpdate();
		entityManager.createNativeQuery("ALTER SEQUENCE students_student_id_seq RESTART WITH 1").executeUpdate();

	}

	@Test
	@Transactional
	void testGetAllForGroups_shouldCheckIfTableIsEmpty_whenMethodIsExecuted() {

		assertThat(groupDao.getAll()).hasSize(0);

	}

	@Test
	@Transactional
	void testSaveForGroups_shouldCheckIfGroupHasBeenSaved_whenMethodIsExecuted() {
		
		Group group = new Group();
		group.setGroupName("AA-01");
		groupDao.save(group);
		assertThat(groupDao.getAll()).hasSize(1);

	}

	@Test
	@Transactional
	void testDeleteForGroups_shouldCheckIfGroupHasBeenDeleted_whenMethodIsExecuted() {

		Group group = new Group();
		group.setGroupName("AA-02");
		groupDao.save(group);
		groupDao.delete(1);
		assertThat(groupDao.getAll()).hasSize(0);

	}

	@Test
	@Transactional
	void testGetByIdForGroups_shouldCheckIfGroupHasBeenFoundById_whenMethodIsExecuted() {

		Group group = new Group();
		group.setGroupName("AA-03");
		groupDao.save(group);
		Optional<Group> result = groupDao.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getGroupId());
		assertEquals("AA-03", result.get().getGroupName());

	}

	@Test
	@Transactional
	void testUpdateForGroups_shouldCheckIfGroupHasBeenUpdated_whenMethodIsExecuted() {

		Group group = new Group();
		group.setGroupName("AA-04");
		groupDao.save(group);
		String[] params = { "UPDATED" };
		groupDao.update(1, params);
		Optional<Group> result = groupDao.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getGroupId());
		assertEquals("UPDATED", result.get().getGroupName());

	}

	@Test
	@Transactional
	void testGetByNameForGroups_shouldCheckIfGroupHasBeenFoundByName_whenMethodIsExecuted() {

		Group group = new Group();
		group.setGroupName("AA-05");
		groupDao.save(group);
		Optional<Group> result = groupDao.getByName("AA-05");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getGroupId());
		assertEquals("AA-05", result.get().getGroupName());

	}

	@Test
	@Transactional
	void testFindGroupsByStudentNumber_shouldReturnAllGroupsWithLessOrEqualStudentsNumber_whenMethodIsExecuted() {

		Group firstGroup = new Group();
		firstGroup.setGroupName("AA-06");
		Group secondGroup = new Group();
		secondGroup.setGroupName("AA-07");
		groupDao.save(firstGroup);
		groupDao.save(secondGroup);
		Student student1 = new Student();
		student1.setGroupId(1);
		student1.setFirstName("John");
		student1.setLastName("Doe");
		Student student2 = new Student();
		student2.setGroupId(1);
		student2.setFirstName("Jane");
		student2.setLastName("Doe");
		Student student3 = new Student();
		student3.setGroupId(2);
		student3.setFirstName("Alex");
		student3.setLastName("Miller");
		studentDao.save(student1);
		studentDao.save(student2);
		studentDao.save(student3);
		Group result = groupDao.findGroupsByStudentNumber(1).get(0);
		assertEquals(2, result.getGroupId());
		assertEquals("AA-07", result.getGroupName());

	}

	@Test
	@Transactional
	void testFindGroupsByStudentNumber_shouldReturnAllGroups_whenArgumentIsBiggerThanStudentNumber() {

		Group firstGroup = new Group();
		firstGroup.setGroupName("AA-08");
		Group secondGroup = new Group();
		secondGroup.setGroupName("AA-09");
		groupDao.save(firstGroup);
		groupDao.save(secondGroup);
		Student student1 = new Student();
		student1.setGroupId(1);
		student1.setFirstName("John");
		student1.setLastName("Doe");
		Student student2 = new Student();
		student2.setGroupId(1);
		student2.setFirstName("Jane");
		student2.setLastName("Doe");
		Student student3 = new Student();
		student3.setGroupId(2);
		student3.setFirstName("Alex");
		student3.setLastName("Miller");
		studentDao.save(student1);
		studentDao.save(student2);
		studentDao.save(student3);
		List<Group> results = groupDao.findGroupsByStudentNumber(4);
		assertThat(results).hasSize(2);
		Group firstResult = results.get(0);
		assertEquals(2, firstResult.getGroupId());
		assertEquals("AA-09", firstResult.getGroupName());
		Group secondResult = results.get(1);
		assertEquals(1, secondResult.getGroupId());
		assertEquals("AA-08", secondResult.getGroupName());

	}

}