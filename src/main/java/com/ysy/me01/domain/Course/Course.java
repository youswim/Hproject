package com.ysy.me01.domain.Course;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String tutor;

    public Long getId(){
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTutor() {
        return this.tutor;
    }

    public Course(String title, String tutor) {
        this.title = title;
        this.tutor = tutor;
    }

    public void update(Course course){
        this.title = course.title;
        this.tutor = course.tutor;
    }
}
