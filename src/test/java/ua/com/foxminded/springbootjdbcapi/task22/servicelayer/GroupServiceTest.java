package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.springbootjdbcapi.task22.consolemenu.ConsoleMenu;
import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcGroupDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Group;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

	@InjectMocks
	private GroupService groupService;

	@Mock
	private ConsoleMenu consoleMenu;

	@Mock
	private JdbcGroupDao groupDao;

	@Mock
	private DbInitService dbInitService;

	@Test
	void testGetByName() {

		Group group = new Group(1, "AA-01");
		Optional<Group> optionalGroup = Optional.of(group);
		Mockito.when(groupDao.getByName("AA-01")).thenReturn(optionalGroup);
		Optional<Group> result = groupService.getByName("AA-01");
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getGroupId());
		assertEquals("AA-01", result.get().getGroupName());
		assertThat(groupService.getByName("AA-01")).isEqualTo(optionalGroup);

	}

	@Test
	void testGetById() {

		Group group = new Group(1, "AA-02");
		Optional<Group> optionalGroup = Optional.of(group);
		Mockito.when(groupDao.getById(1)).thenReturn(optionalGroup);
		Optional<Group> result = groupService.getById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getGroupId());
		assertEquals("AA-02", result.get().getGroupName());
		assertThat(groupService.getById(1)).isEqualTo(optionalGroup);

	}

	@Test
	void testGetAll() {

		Group group1 = new Group(1, "AA-03");
		Group group2 = new Group(2, "AA-04");
		List<Group> groupList = new ArrayList<>();
		groupList.add(group1);
		groupList.add(group2);
		Mockito.when(groupDao.getAll()).thenReturn(groupList);
		List<Group> results = groupService.getAll();
		assertThat(results).hasSize(2);
		Group result1 = results.get(0);
		assertEquals(1, result1.getGroupId());
		assertEquals("AA-03", result1.getGroupName());
		Group result2 = results.get(1);
		assertEquals(2, result2.getGroupId());
		assertEquals("AA-04", result2.getGroupName());
		assertThat(groupService.getAll()).isEqualTo(groupList);

	}

	@Test
	void testSave() {

		Group group = new Group(1, "AA-05");
		Mockito.when(groupDao.save(group)).thenReturn(group);
		Group result = groupService.save(group);
		assertEquals(1, result.getGroupId());
		assertEquals("AA-05", result.getGroupName());
		assertThat(groupService.save(group)).isEqualTo(group);

	}

	@Test
	void testUpdate() {

		String[] params = { "UPDATED" };
		Group updatedGroup = new Group(1, "UPDATED");
		Mockito.when(groupDao.update(1, params)).thenReturn(updatedGroup);
		assertThat(groupService.update(1, params)).isEqualTo(updatedGroup);

	}

	@Test
	void testDeleteWhenOptionalIsEmpty() {

		Mockito.when(groupDao.getById(1)).thenReturn(Optional.empty());
		assertEquals(true, groupService.delete(1));

	}

	@Test
	void testDeleteWhenOptionalIsNotEmpty() {

		Group group = new Group(1, "AA-06");
		Optional<Group> optionalGroup = Optional.of(group);
		Mockito.when(groupDao.getById(1)).thenReturn(optionalGroup);
		assertEquals(false, groupService.delete(1));

	}

	@Test
	void testFindGroupsByStudentNumber() {

		Group group1 = new Group(1, "AA-07");
		Group group2 = new Group(2, "AA-08");
		List<Group> groupList = new ArrayList<>();
		groupList.add(group1);
		groupList.add(group2);
		Mockito.when(groupDao.findGroupsByStudentNumber(1)).thenReturn(groupList);
		assertThat(groupService.findGroupsByStudentNumber(1)).isEqualTo(groupList);

	}

}