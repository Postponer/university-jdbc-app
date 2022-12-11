package ua.com.foxminded.springbootjdbcapi.task22.daolayer;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.springbootjdbcapi.task22.models.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Integer> {

	@Query(value = "SELECT g FROM Group g WHERE g.groupName = ?1")
	public Optional<Group> getByName(String groupName);

	@Query(value = "SELECT g FROM Group g WHERE g.groupId = ?1")
	public Optional<Group> getById(int groupId);

	@Query(value = "SELECT g FROM Group g")
	public List<Group> getAll();

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO groups (group_name) VALUES (?1)", nativeQuery = true)
	public void save(String groupName);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE Group g SET g.groupName = ?2 WHERE groupId = ?1")
	public void update(int groupId, String groupName);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Group g WHERE groupId = ?1")
	public void delete(int groupId);

	@Query(value = "SELECT groups.* FROM groups LEFT JOIN students ON groups.group_id = students.group_id GROUP BY groups.group_id HAVING COUNT(students.group_id) <= ?1", nativeQuery = true)
	public List<Group> findGroupsByStudentNumber(int studentNumber);

}