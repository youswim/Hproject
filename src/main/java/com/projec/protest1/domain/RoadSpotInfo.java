package com.projec.protest1.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class RoadSpotInfo {
    @Id
    private String RoadId;

    @Column(nullable = false)
    private String RoadName;
}
