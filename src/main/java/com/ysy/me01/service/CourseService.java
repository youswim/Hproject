package com.ysy.me01.service;


import com.ysy.me01.domain.Course.Course;
import com.ysy.me01.domain.Course.CourseRepo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CourseService {

    private final CourseRepo courserepo;
    //find by id 함수를 사용하기 위해서 클래스 변수 선언

    public CourseService(CourseRepo repo){
        this.courserepo = repo;
        //위의 두 과정을 거침으로써 spring이 jpa를 쓸 수 있도록 만들어준다.
    }

    @Transactional //정보가 자동으로 데이터베이스에 업데이트 함수가 적용된다.
    public Long update(Long id, Course course){
        Course course1 = courserepo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        course1.update(course);
        return course1.getId();
    }


}
