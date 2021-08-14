package com.ysy.me01;

import com.ysy.me01.domain.Course.Course;
import com.ysy.me01.domain.Course.CourseRepo;
import com.ysy.me01.service.CourseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Me01Application {

	public static void main(String[] args) {
		SpringApplication.run(Me01Application.class, args);
	}

	@Bean
    public CommandLineRunner demo(CourseRepo repo, CourseService courseSerevice){
	    return (args) -> {
			Course course1 = new Course("spring", "hong");
			repo.save(course1);

			List<Course> courseList = repo.findAll();
			for(int i = 0; i<courseList.size(); i++){
				System.out.println(courseList.get(i).getTitle());
				System.out.println(courseList.get(i).getTutor());
			}

			Course new_course = new Course("ang", "ggimo");
			courseSerevice.update(1L, new_course);
			courseList = repo.findAll();
			for(int i = 0; i<courseList.size(); i++){
				System.out.println(courseList.get(i).getTitle());
				System.out.println(courseList.get(i).getTutor());
			}

			repo.deleteAll();
        };
    }
}
