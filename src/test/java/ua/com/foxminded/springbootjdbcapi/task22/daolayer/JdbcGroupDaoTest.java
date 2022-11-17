package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import ua.com.foxminded.springbootjdbcapi.task22.Application;
import ua.com.foxminded.springbootjdbcapi.task22.Config;
import ua.com.foxminded.springbootjdbcapi.task22.controller.Controller;
import ua.com.foxminded.springbootjdbcapi.task22.models.Group;
import ua.com.foxminded.springbootjdbcapi.task22.models.Student;

@SpringBootTest(classes = { Config.class, Application.class })
class JdbcGroupDaoTest {

	private JdbcGroupDao groupDao;
	private JdbcStudentDao studentDao;
	private JdbcTemplate jdbcTemplate;

	@MockBean
	private Controller controller;

	@Autowired
	public JdbcGroupDaoTest(JdbcGroupDao groupDao, JdbcStudentDao studentDao, JdbcTemplate jdbcTemplate) {

		this.groupDao = groupDao;
		this.studentDao = studentDao;
		this.jdbcTemplate = jdbcTemplate;

	}

	@BeforeEach
	void reset() {

		jdbcTemplate.update("truncate table students_courses, students, groups");
		jdbcTemplate.update("ALTER SEQUENCE groups_group_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE students_student_id_seq RESTART WITH 1");

	}

	@Test
	void testGetAllForGroups_shouldCheckIfTableIsEmpty_whenMethodIsExecuted() {

		assertThat(groupDao.getAll()).hasSize(0);

	}

	@Test
	void testSaveForGroups_shouldCheckIfGroupHasBeenSaved_whenMethodIsExecuted() {

		groupDao.save(new Group(1, "AA-01"));
		assertThat(groupDao.getAll()).hasSize(1);

	}

	@Test
	void testDeleteForGroups_shouldCheckIfGroupHasBeenDeleted_whenMethodIsExecuted() {

		groupDao.save(new Group(1, "AA-02"));
		groupDao.delete(1);
		assertThat(groupDao.getAll()).hasSize(0);

	}

	@Test
	void testGetByIdForGroups_shouldCheckIfGroupHasBeenFoundById_whenMethodIsExecuted() {

		groupDao.save(new Group(1, "AA-03"));
		Optional<Group> result = groupDao.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getGroupId());
		assertEquals("AA-03", result.get().getGroupName());

	}

	@Test
	void testUpdateForGroups_shouldCheckIfGroupHasBeenUpdated_whenMethodIsExecuted() {

		groupDao.save(new Group(1, "AA-04"));
		String[] params = { "UPDATED" };
		groupDao.update(1, params);
		Optional<Group> result = groupDao.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getGroupId());
		assertEquals("UPDATED", result.get().getGroupName());

	}

	@Test
	void testGetByNameForGroups_shouldCheckIfGroupHasBeenFoundByName_whenMethodIsExecuted() {

		groupDao.save(new Group(1, "AA-05"));
		Optional<Group> result = groupDao.getByName("AA-05");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getGroupId());
		assertEquals("AA-05", result.get().getGroupName());

	}

	@Test
	void testFindGroupsByStudentNumber_shouldReturnAllGroupsWithLessOrEqualStudentsNumber_whenMethodIsExecuted() {

		groupDao.save(new Group(1, "AA-06"));
		groupDao.save(new Group(2, "AA-07"));
		studentDao.save(new Student(1, 1, "John", "Doe"));
		studentDao.save(new Student(2, 1, "Jane", "Doe"));
		studentDao.save(new Student(3, 2, "Alex", "Miller"));
		Group result = groupDao.findGroupsByStudentNumber(1).get(0);
		assertEquals(2, result.getGroupId());
		assertEquals("AA-07", result.getGroupName());

	}

	@Test
	void testFindGroupsByStudentNumber_shouldReturnAllGroups_whenArgumentIsBiggerThanStudentNumber() {

		groupDao.save(new Group(1, "AA-08"));
		groupDao.save(new Group(2, "AA-09"));
		studentDao.save(new Student(1, 1, "John", "Doe"));
		studentDao.save(new Student(2, 1, "Jane", "Doe"));
		studentDao.save(new Student(3, 2, "Alex", "Miller"));
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