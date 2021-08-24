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
    private String Road_id;

    @Column(nullable = false)
    private int date;

    @Column(nullable = false)
    private int time;

    @Column(nullable = false)
    private int io_type;

    @Column(nullable = false)
    private int lane_num;

    @Column(nullable = false)
    private int vol;

    public RoadAll(RoadAllDto roadAllDto){
        this.Road_id = roadAllDto.getRoad_id();
        this.date = roadAllDto.getDate();
        this.time = roadAllDto.getTime();
        this.io_type = roadAllDto.getIo_type();
        this.lane_num = roadAllDto.getLane_num();
        this.vol = roadAllDto.getVol();
    }


}
