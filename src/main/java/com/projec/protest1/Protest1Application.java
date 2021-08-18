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

    @Bean
    public CommandLineRunner demo(RoadRepository repository) {
        return (args) -> {

            GetJsonFunction getJsonFunction = new GetJsonFunction();//함수 사용을 위해서 생성
            List<RoadDto> roadDtoList = new ArrayList<>();
            List<RoadInfoDto> roadInfoDtoLIst = new ArrayList<>();
            //데이터 담을 List 생성
            roadDtoList = getJsonFunction.getRoads();
            for(int i = 0; i< roadDtoList.size(); i++){
                String road_id = roadDtoList.get(i).getSpot_num();
                for(int j=20210808; j<20210816; j++){//날짜
                    int date = j;

                    for(int k=0; k<24; k++){//시간
                        int time = k;
                        String str_time = String.format("%1$02d",time);
                        roadInfoDtoLIst = getJsonFunction.getRoadInfo(road_id, date, str_time);

                        for(int l=0; l<roadInfoDtoLIst.size(); l++){
                            int io_type = roadInfoDtoLIst.get(l).getIo_type();
                            int lane_num = roadInfoDtoLIst.get(l).getLane_num();
                            int vol = roadInfoDtoLIst.get(l).getVol();
                            RoadAllDto roadAllDto = new RoadAllDto(road_id, date, time, io_type, lane_num, vol);
                            RoadAll roadAll = new RoadAll(roadAllDto);
                            repository.save(roadAll);

                        }
                        System.out.println("도로 : "+road_id+", 날짜 : "+date+",시간 : "+time);
                    }


                }
            }
        };
    }

}
