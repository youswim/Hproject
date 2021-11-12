package com.projec.protest1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class RoadAllDto {
    //entity 인 RoadAll에 전달하기 위해서 만든 Dto(Data Transfer Object)
    private String Road_id;
    private int date;
    private int time;
    private int io_type;
    private int lane_num;
    private int vol;

    public RoadAllDto(String Road_id, int date, int time, int io_type, int lane_num, int vol) {
        this.Road_id = Road_id;
        this.date = date;
        this.time = time;
        this.io_type = io_type;
        this.lane_num = lane_num;
        this.vol = vol;
    }


}
