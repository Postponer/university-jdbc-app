package ua.com.foxminded.springbootjdbcapi.task22;

import java.util.Optional;

public interface StudentDao {

	Optional<Student> getByGroupId(String groupId);
	Optional<Student> getByFirstName(String firstName);
	Optional<Student> getByLastName(String lastName);

}