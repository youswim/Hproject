//package com.projec.protest1.utils;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.projec.protest1.dto.RoadAllDto;
//import com.projec.protest1.dto.RoadDto;
//import com.projec.protest1.dto.RoadInfoDto;
//import com.projec.protest1.domain.RoadRepository;
//import com.projec.protest1.utils.GetJsonFunction;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.context.annotation.Bean;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@RequiredArgsConstructor
//public class GetData {
//
//
//
//
//    public static void main(String[] args) throws JsonProcessingException {
//
//
//        GetJsonFunction getJsonFunction = new GetJsonFunction();
//        List<RoadDto> roadDtoList = new ArrayList<>();
//        List<RoadInfoDto> roadInfoDtoLIst = new ArrayList<>();
//
//        roadDtoList = getJsonFunction.getRoads();
//
//        for(int i = 0; i< roadDtoList.size(); i++){
//            String road_id = roadDtoList.get(i).getSpot_num();
//
//            for(int j=20210808; j<20210816; j++){
//                int date = j;
//
//                for(int k=0; k<24; k++){
//                    int time = k;
//                    roadInfoDtoLIst = getJsonFunction.getRoadInfo(road_id, date, time);
//
//                    for(int l=0; l<roadInfoDtoLIst.size(); l++){
//                        int io_type = roadInfoDtoLIst.get(l).getIo_type();
//                        int lane_num = roadInfoDtoLIst.get(l).getLane_num();
//                        int vol = roadInfoDtoLIst.get(l).getVol();
//                        RoadAllDto roadAllDto = new RoadAllDto(road_id, date, time, io_type, lane_num, vol);
//
//
//
//                    }
//                }
//
//
//            }
//        }
//    }
//
//
//}
