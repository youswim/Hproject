package com.projec.protest1.dto;


import com.projec.protest1.domain.RoadAll;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@NoArgsConstructor
@Getter
public class RoadInfoDto {
    private int io_type;
    private int lane_num;
    private int vol;

    public RoadInfoDto(JSONObject roadInfoJson) {
        this.io_type = roadInfoJson.getInt("io_type");
        this.lane_num = roadInfoJson.getInt("lane_num");
        this.vol = roadInfoJson.getInt("vol");
        //JSONObject를 인자로 받는 생성자.
        //JSONObject의 각 key값을 갖는 정보들을 빼서 저장한다.
    }

    public RoadInfoDto(RoadAll roadAll) {
        this.io_type = roadAll.getIoType();
        this.lane_num = roadAll.getLaneNum();
        this.vol = roadAll.getVol();
    }
}
