package com.ysy.me01.domain.Course;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CourseDto {

    private String title;
    private String tutor;

    public CourseDto(String title, String tutor){
        this.title = title;
        this.tutor = tutor;
    }

}
