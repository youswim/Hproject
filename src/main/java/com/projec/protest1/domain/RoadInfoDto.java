package com.projec.protest1.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@NoArgsConstructor
@Getter
public class RoadInfoDto {
    private int io_type;
    private int lane_num;
    private int vol;

    public RoadInfoDto(JSONObject roadInfoJson){
        this.io_type = roadInfoJson.getInt("io_type");
        this.lane_num = roadInfoJson.getInt("lane_num");
        this.vol = roadInfoJson.getInt("vol");

    }

}
