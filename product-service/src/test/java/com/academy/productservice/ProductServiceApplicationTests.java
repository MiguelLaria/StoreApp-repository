package com.academy.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

	@Test
	void productNameIsValid() {
		String name = "Laptop";
		assertNotNull(name);
		assertFalse(name.isBlank());
	}
}