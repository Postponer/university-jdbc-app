package ua.com.foxminded.springbootjdbcapi.task22;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.com.foxminded.springbootjdbcapi.task22.servicelayer.ConsoleMenuService;

@SpringBootTest
class ApplicationTests {

	@MockBean
	private ConsoleMenuService consoleMenuService;

	@Test
	void contextLoads() {

	}

}