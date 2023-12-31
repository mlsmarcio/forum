package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication()
@EnableSpringDataWebSupport
@EnableCaching
public class ForumApplication {
	//@EnableSwagger2
	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
		
	}

}
