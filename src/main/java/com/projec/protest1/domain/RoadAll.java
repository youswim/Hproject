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

    public RoadAll(String roadId, String date, Integer time, Integer ioType, Integer laneNum, Integer vol) {
        this.roadId = roadId;
        this.date = date;
        this.time = time;
        this.ioType = ioType;
        this.laneNum = laneNum;
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
