package com.juandelacierva.ChurnGym;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Requiere PostgreSQL activo — se cubre con los tests de controller (@WebMvcTest)")
@SpringBootTest
class ChurnGymApplicationTests {

	@Test
	void contextLoads() {
	}

}
