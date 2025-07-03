package api.vitaport.health;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class HealthApplicationTests {

	@Test
	void contextLoads() {
		var modules = ApplicationModules.of(HealthApplication.class);
		System.out.println(modules);
		modules.verify();
	}

}
