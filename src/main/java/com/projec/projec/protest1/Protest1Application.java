package com.projec.protest1;

import com.projec.protest1.domain.*;
import com.projec.protest1.utils.GetJsonFunction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Protest1Application {

    GetJsonFunction getJsonFunction = new GetJsonFunction();

    List<RoadDto> roadDtoList = new ArrayList<>();


    public static void main(String[] args) {
        SpringApplication.run(Protest1Application.class, args);
    }

    //api를 받아서 db에 저장하기 위한 부분

    @Bean
    public CommandLineRunner demo(RoadRepository repository) {
        return (args) -> {

            GetJsonFunction getJsonFunction = new GetJsonFunction();
            //함수 사용을 위해서 getJsonFunction 객체 생성
            List<RoadDto> roadDtoList = new ArrayList<>();
            List<RoadInfoDto> roadInfoDtoLIst = new ArrayList<>();
            //데이터 담을 List 생성
            roadDtoList = getJsonFunction.getRoads();
            //roadDto 를 담은 리스트에 값을 넣는다.

            for (int i = 0; i < roadDtoList.size(); i++) {
                //모든 도로에 대해서 실행.(raodDtoList의 size가 곧 도로의 갯수가 되므로)

                String road_id = roadDtoList.get(i).getSpot_num();
                //도로이름 데이터를 api로부터 받아오기 위해서 roadDtoList에서 도로 ID를 빼온다.

                for (int date = 20210808; date < 20210816; date++) {
                    //빼올 날짜

                    for (int time = 0; time < 24; time++) {
                        //빼올 시간

                        String str_time = String.format("%1$02d", time);
                        //정수형인 time을, 00, 01 의 형태로 만들기 위해서 str로 변환.

                        roadInfoDtoLIst = getJsonFunction.getRoadInfo(road_id, date, str_time);
                        //교통량 데이터를  api로부터 받아와서 roadInfoDtoList에 저장

                        for (int l = 0; l < roadInfoDtoLIst.size(); l++) {
                            //roadInfoDtoList에 저장된 사이즈가 교통량 데이터의 갯수이다

                            int io_type = roadInfoDtoLIst.get(l).getIo_type();
                            int lane_num = roadInfoDtoLIst.get(l).getLane_num();
                            int vol = roadInfoDtoLIst.get(l).getVol();
                            //roadInfoDtoList에 저장된 교통량 데이터를 하나씩 빼온다.

                            RoadAllDto roadAllDto = new RoadAllDto(road_id, date, time, io_type, lane_num, vol);
                            //안전한 전달을 위해서 Dto를 한번 거친다.

                            RoadAll roadAll = new RoadAll(roadAllDto);
                            //Dto에 담긴 데이터를 entity로 전달한다.

                            repository.save(roadAll);
                            //데이터를 db에 저장한다.

                        }
                        System.out.println("도로 : " + road_id + ", 날짜 : " + date + ",시간 : " + time);
                        //저장한 데이터를 간략하게 화면에 출력한다.
                    }


                }
                Thread.sleep(10000);
                //짧은 시간에 많은 데이터를 요청하면 교통 api에서 오류를 일으킴.
                //한번 요청하고 10초가량 쉰다.
            }
        };
    }



}
