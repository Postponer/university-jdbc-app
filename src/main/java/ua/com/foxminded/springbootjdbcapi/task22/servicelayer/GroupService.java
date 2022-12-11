package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.GroupDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Group;

@Service
public class GroupService {

	private GroupDao groupDao;
	private Scanner scanner = new Scanner(System.in);
	Logger logger = LoggerFactory.getLogger(GroupService.class);

	public GroupService(GroupDao groupDao) {

		this.groupDao = groupDao;

	}

	public Group getByName(String groupName) {

		logger.info("Getting group by name: {}", groupName);
		Group group = groupDao.getByName(groupName).orElse(null);
		logger.info("{} has been gotten by name: {}", group, groupName);

		return group;

	}

	public Group getById(int groupId) {

		logger.info("Getting group by id: {}", groupId);
		Group group = groupDao.getById(groupId).orElse(null);
		logger.info("{} has been gotten by id: {}", group, groupId);

		return group;

	}

	public List<Group> getAll() {

		logger.info("Getting all groups");
		List<Group> groupList = groupDao.getAll();
		logger.info("All groups have been gotten");

		return groupList;

	}

	public Group save(Group group) {

		logger.info("Saving {}", group);
		groupDao.save(group.getGroupName());
		Group savedGroup = groupDao.getById(group.getGroupId()).orElse(null);
		logger.info("{} has been saved", group);

		return savedGroup;

	}

	public Group update(int groupId, String groupName) {

		logger.info("Updating group with id: {} with this parameter: {}", groupId, groupName);
		groupDao.update(groupId, groupName);
		Group group = groupDao.getById(groupId).orElse(null);
		logger.info("Group with id: {} has been updated with this parameter: {}", groupId, groupName);

		return group;

	}

	public boolean delete(int groupId) {

		logger.info("Deleting group with id: {}", groupId);

		try {

			groupDao.delete(groupId);
			logger.info("Group with id: {} has been deleted", groupId);
			return true;

		} catch (Exception e) {

			logger.error("Exception occurred during group deletion by id: {}, message. Exception: ", groupId, e);
			return false;

		}

	}

	public List<Group> findGroupsByStudentNumber(int studentNumber) {

		logger.info("Finding groups by student number: {}", studentNumber);
		List<Group> groupList = groupDao.findGroupsByStudentNumber(studentNumber);
		logger.info("Groups with student number: {} has been found", studentNumber);

		return groupList;

	}

	public void findGroupsByStudentNumber() {

		logger.info("Finding groups by student number in console menu");

		try {

			System.out.println("Please enter a number of students: ");
			int studentNumber = Integer.parseInt(scanner.nextLine());
			System.out.println(findGroupsByStudentNumber(studentNumber));
			logger.info("Groups with student number: " + studentNumber + "has been found in console menu");

		} catch (NumberFormatException e) {

			logger.error(
					"Exception occurred during finding groups by student number in console menu, message. Exception: ",
					e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

}