package com.projec.protest1.repository;

import com.projec.protest1.domain.RoadSpotInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadSpotInfoRepository extends CrudRepository<RoadSpotInfo, String> {
}
