package ua.com.foxminded.springbootjdbcapi.task22;

import java.util.Optional;

public interface GroupDao {

	Optional<Group> getByName(String groupName);
	
}