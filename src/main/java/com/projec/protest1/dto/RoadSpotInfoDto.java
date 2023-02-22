package com.projec.protest1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@NoArgsConstructor
@Getter
public class RoadSpotInfoDto {

    private String roadId;
    private String roadName;

    public RoadSpotInfoDto(JSONObject roadJson) {
        this.roadId = roadJson.getString("spot_num");
        this.roadName = roadJson.getString("spot_nm");
        //JSONObject를 인자로 받는 생성자.
        //JSONObject의 각 key값을 갖는 정보들을 빼서 저장한다.
    }
}
