package com.projec.protest1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@NoArgsConstructor
@Getter
public class RoadDto {

    private String spot_nm;
    private String spot_num;

    public RoadDto(JSONObject roadJson) {
        this.spot_nm = roadJson.getString("spot_nm");
        this.spot_num = roadJson.getString("spot_num");
        //JSONObject를 인자로 받는 생성자.
        //JSONObject의 각 key값을 갖는 정보들을 빼서 저장한다.
    }
}
