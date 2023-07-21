package com.project.fashion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.project.fashion.dao.AdminDao;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableSwagger2
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FashionApplication {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(AdminDao.class);
		SpringApplication.run(FashionApplication.class, args);
		logger.info("Start Application");
	}
}
