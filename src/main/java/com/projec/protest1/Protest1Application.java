package com.projec.protest1;

import com.projec.protest1.utils.ApplicationSetup;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Protest1Application {

    public static void main(String[] args) {
        SpringApplication.run(Protest1Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(ApplicationSetup applicationSetup) { // 애플리케이션에 필요한 setpu
        return (args) -> {
            applicationSetup.signUpMember();
            applicationSetup.saveInfos();
        };
    }
}
