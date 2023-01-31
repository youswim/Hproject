package com.projec.protest1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RoadAll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String roadId;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private Integer time;

    @Column
    private Integer ioType;

    @Column
    private Integer laneNum;

    @Column
    private Integer vol;

    public RoadAll(String road_id, String date, Integer time, Integer io_type, Integer lane_num, Integer vol) {
        this.roadId = road_id;
        this.date = date;
        this.time = time;
        this.ioType = io_type;
        this.laneNum = lane_num;
        this.vol = vol;
    }

    @Override
    public String toString() {
        return "RoadAll{" +
                "id=" + id +
                ", roadId='" + roadId + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", ioType=" + ioType +
                ", laneNum=" + laneNum +
                ", vol=" + vol +
                '}';
    }
}
