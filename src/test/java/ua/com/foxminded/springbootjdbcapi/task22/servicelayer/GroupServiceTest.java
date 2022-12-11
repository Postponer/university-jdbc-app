package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcGroupDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Group;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

	@InjectMocks
	private GroupService groupService;

	@Mock
	private JdbcGroupDao groupDao;

	@Test
	void testGetByName() {

		Group group = new Group(1, "AA-01");
		Optional<Group> optionalGroup = Optional.of(group);
		Mockito.when(groupDao.getByName(Mockito.any(String.class))).thenReturn(optionalGroup);
		Group result = groupService.getByName("AA-01");
		Mockito.verify(groupDao).getByName(Mockito.any(String.class));
		assertEquals(1, result.getGroupId());
		assertEquals("AA-01", result.getGroupName());
		assertEquals(group, result);

	}

	@Test
	void testGetById() {

		Group group = new Group(1, "AA-02");
		Optional<Group> optionalGroup = Optional.of(group);
		Mockito.when(groupDao.getById(Mockito.any(Integer.class))).thenReturn(optionalGroup);
		Group result = groupService.getById(1);
		Mockito.verify(groupDao).getById(Mockito.any(Integer.class));
		assertEquals(1, result.getGroupId());
		assertEquals("AA-02", result.getGroupName());
		assertEquals(group, result);

	}

	@Test
	void testGetAll() {

		Group group = new Group(1, "AA-03");
		List<Group> groupList = new ArrayList<>();
		groupList.add(group);
		Mockito.when(groupDao.getAll()).thenReturn(groupList);
		List<Group> results = groupService.getAll();
		Mockito.verify(groupDao).getAll();
		assertThat(results).hasSize(1);
		Group result = results.get(0);
		assertEquals(1, result.getGroupId());
		assertEquals("AA-03", result.getGroupName());
		assertEquals(groupList, results);

	}

	@Test
	void testSave() {

		Group group = new Group(1, "AA-05");
		Mockito.when(groupDao.save(Mockito.any(Group.class))).thenReturn(group);
		Group result = groupService.save(group);
		Mockito.verify(groupDao).save(Mockito.any(Group.class));
		assertEquals(1, result.getGroupId());
		assertEquals("AA-05", result.getGroupName());
		assertEquals(group, result);

	}

	@Test
	void testUpdate() {

		String[] params = { "UPDATED" };
		Group updatedGroup = new Group(1, "UPDATED");
		Mockito.when(groupDao.update(Mockito.any(Integer.class), Mockito.any(String[].class))).thenReturn(updatedGroup);
		assertThat(groupService.update(1, params)).isEqualTo(updatedGroup);
		Mockito.verify(groupDao).update(Mockito.any(Integer.class), Mockito.any(String[].class));

	}

	@Test
	void testDelete() {

		assertEquals(true, groupService.delete(1));
		
	}

	@Test
	void testFindGroupsByStudentNumber() {

		Group group1 = new Group(1, "AA-07");
		Group group2 = new Group(2, "AA-08");
		List<Group> groupList = new ArrayList<>();
		groupList.add(group1);
		groupList.add(group2);
		Mockito.when(groupDao.findGroupsByStudentNumber(Mockito.any(Integer.class))).thenReturn(groupList);
		assertThat(groupService.findGroupsByStudentNumber(1)).isEqualTo(groupList);
		Mockito.verify(groupDao).findGroupsByStudentNumber(Mockito.any(Integer.class));

	}

}