package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcGroupDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Group;

@Service
public class GroupService {

	private JdbcGroupDao groupDao;

	public GroupService(JdbcGroupDao groupDao) {

		this.groupDao = groupDao;

	}

	public Optional<Group> getByName(String groupName) {

		return groupDao.getByName(groupName);

	}

	public Optional<Group> getById(int groupId) {

		return groupDao.getById(groupId);

	}

	public List<Group> getAll() {

		return groupDao.getAll();

	}

	public Group save(Group group) {

		return groupDao.save(group);

	}

	public Group update(int groupId, String[] params) {

		return groupDao.update(groupId, params);

	}

	public boolean delete(int groupId) {

		groupDao.delete(groupId);

		Optional<Group> group = getById(groupId);

		if (group.isPresent()) {

			return false;

		}

		return true;

	}

	public List<Group> findGroupsByStudentNumber(int studentNumber) {

		return groupDao.findGroupsByStudentNumber(studentNumber);

	}

}