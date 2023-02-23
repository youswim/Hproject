package com.projec.protest1.dto;

import com.projec.protest1.domain.RoadAll;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RoadInfoDto {
    private int ioType;
    private int laneNum;
    private int vol;

    public RoadInfoDto(RoadAll roadAll) {
        this.ioType = roadAll.getIoType();
        this.laneNum = roadAll.getLaneNum();
        this.vol = roadAll.getVol();
    }

    public RoadInfoDto(int ioType, int laneNum, int vol) {
        this.ioType = ioType;
        this.laneNum = laneNum;
        this.vol = vol;
    }

    @Override
    public String toString() {
        return "RoadInfoDto{" +
                "ioType=" + ioType +
                ", laneNum=" + laneNum +
                ", vol=" + vol +
                '}';
    }
}
