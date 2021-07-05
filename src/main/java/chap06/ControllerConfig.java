package chap06;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"chap06"})
public class ControllerConfig {
	
	@Bean
	public HelloController helloController() {
		return new HelloController();
	}

}
