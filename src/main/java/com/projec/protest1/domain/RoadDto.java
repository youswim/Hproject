package com.projec.protest1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@NoArgsConstructor
@Getter
public class RoadDto {

    private String spot_nm;
    private String spot_num;

    public RoadDto(JSONObject roadJson){
        this.spot_nm = roadJson.getString("spot_nm");
        this.spot_num = roadJson.getString("spot_num");

    }
}
