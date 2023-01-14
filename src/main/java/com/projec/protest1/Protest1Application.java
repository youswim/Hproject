package com.projec.protest1;

import com.projec.protest1.domain.RoadAll;
import com.projec.protest1.dto.RoadAllDto;
import com.projec.protest1.dto.RoadDto;
import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.repository.RoadRepository;
import com.projec.protest1.dto.SignupRequestDto;
import com.projec.protest1.service.UserService;
import com.projec.protest1.utils.UrlMaker;
import com.projec.protest1.utils.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;


@SpringBootApplication
public class Protest1Application {

    public static void main(String[] args) {
        SpringApplication.run(Protest1Application.class, args);
    }

    //api를 받아서 db에 저장하기 위한 부분
    @Bean
    public CommandLineRunner demo(UserService userService, RoadRepository roadRepository) {
        return (args) -> {

            signUpMember(userService);
            saveInfos(roadRepository);
        };
    }

    private void saveInfos(RoadRepository roadRepository) throws InterruptedException {
        XmlParser xmlParser = new XmlParser();
        UrlMaker urlMaker = new UrlMaker();

        List<RoadDto> roads = xmlParser.formJsonToRoadDto(urlMaker.getSpotInfoUrl());

        List<RoadInfoDto> roadInfoDtoLIst;

        for (RoadDto road : roads) {
            String road_id = road.getSpot_num();

            for (int date = 20210808; date < 20210816; date++) {
                for (int time = 0; time < 24; time++) {
                    String str_time = String.format("%1$02d", time);
                    //정수형인 tim을 00, 01 의 format으로 맞추기 위한 과정

                    List<RoadInfoDto> roadInfos = xmlParser.fromJsonToRoadInfoDto(
                            urlMaker.getVolInfoUrl(road_id, date, str_time)
                    );

                    for (RoadInfoDto roadInfo : roadInfos) {
                        int io_type = roadInfo.getIo_type();
                        int lane_num = roadInfo.getLane_num();
                        int vol = roadInfo.getVol();

                        RoadAllDto roadAllDto = new RoadAllDto(road_id, date, time, io_type, lane_num, vol);

                        RoadAll roadAll = new RoadAll(roadAllDto);

                        roadRepository.save(roadAll);
                        //데이터를 db에 저장한다.

                    }
//                    System.out.println("도로 : " + road_id + ", 날짜 : " + date + ",시간 : " + time);
//                    저장한 데이터를 간략하게 화면에 출력한다.
                }
            }
            Thread.sleep(10000);
            //짧은 시간에 많은 데이터를 요청하면 교통 api에서 오류를 일으킴.
            //한번 요청하고 10초가량 쉰다.
        }
    }

    private void signUpMember(UserService userService) {
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("111");
        signupRequestDto.setPassword("111");
        signupRequestDto.setEmail("111@naver.com");
        signupRequestDto.setAdmin(true);
        signupRequestDto.setAdminToken("AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC");

        userService.registerUser(signupRequestDto);
    }


}
