package ua.com.foxminded.springbootjdbcapi.task22.servicelayer;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.springbootjdbcapi.task22.daolayer.JdbcGroupDao;
import ua.com.foxminded.springbootjdbcapi.task22.models.Group;

@Service
public class GroupService {

	private JdbcGroupDao groupDao;
	private Scanner scanner = new Scanner(System.in);
	Logger logger = LoggerFactory.getLogger(GroupService.class);

	public GroupService(JdbcGroupDao groupDao) {

		this.groupDao = groupDao;

	}

	public Group getByName(String groupName) {

		logger.debug("Entering get group by name endpoint");
		Group group = groupDao.getByName(groupName).get();
		logger.info(group.toString() + " has been gotten by name: " + groupName);

		return group;

	}

	public Group getById(int groupId) {

		logger.debug("Entering get group by id endpoint");
		Group group = groupDao.getById(groupId).get();
		logger.info(group.toString() + " has been gotten by id: " + groupId);

		return group;

	}

	public List<Group> getAll() {

		logger.debug("Entering get all groups endpoint");
		List<Group> groupList = groupDao.getAll();
		logger.info("All groups have been gotten");

		return groupList;

	}

	public Group save(Group group) {

		logger.debug("Entering save group endpoint");
		Group savedGroup = groupDao.save(group);
		logger.info(group.toString() + " has been saved");

		return savedGroup;

	}

	public Group update(int groupId, String[] params) {

		logger.debug("Entering update group endpoint");
		Group group = groupDao.update(groupId, params);
		logger.info("Group with id: " + groupId + " has been updated with this parameters: " + params);

		return group;

	}

	public boolean delete(int groupId) {

		logger.debug("Entering delete group endpoint");
		groupDao.delete(groupId);

		try {

			getById(groupId);
			logger.info("Group with id: " + groupId + " has been deleted");

		} catch (Exception e) {

			return true;

		}

		logger.error("Unable to delete group with id: " + groupId);
		return false;

	}

	public List<Group> findGroupsByStudentNumber(int studentNumber) {

		logger.debug("Entering find groups by student number endpoint");
		List<Group> groupList = groupDao.findGroupsByStudentNumber(studentNumber);
		logger.info("Groups with student number: " + studentNumber + "has been found");

		return groupList;

	}

	public void findGroupsByStudentNumber() {

		logger.debug("Entering find groups by student number endpoint in console menu");

		try {

			System.out.println("Please enter a number of students: ");
			int studentNumber = Integer.parseInt(scanner.nextLine());
			System.out.println(findGroupsByStudentNumber(studentNumber));
			logger.info("Groups with student number: " + studentNumber + "has been found in console menu");

		} catch (NumberFormatException e) {

			logger.error("Unable to find groups by student number in console menu, message: " + e.getMessage(), e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

}