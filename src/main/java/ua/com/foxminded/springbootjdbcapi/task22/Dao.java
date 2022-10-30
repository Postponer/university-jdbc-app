package ua.com.foxminded.springbootjdbcapi.task22;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

	Optional<T> getById(int id);

	List<T> getAll();

	void save(T t);

	void update(int id, String[] params);

	void delete(int id);

}