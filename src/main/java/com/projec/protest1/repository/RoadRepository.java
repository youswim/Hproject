package com.projec.protest1.repository;

import com.projec.protest1.domain.RoadAll;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoadRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(RoadAll roadAll) {
        em.persist(roadAll);
    }

    public List<RoadAll> findRoadEntities(String rid, String date, Integer time) {
        return em.createQuery("select roadAll from RoadAll roadAll " +
                        "where roadAll.roadId = :rid " +
                        "and roadAll.date = :date " +
                        "and roadAll.time = :time" , RoadAll.class)
                .setParameter("rid", rid)
                .setParameter("date", date)
                .setParameter("time", time)
                .getResultList();
    }
}
