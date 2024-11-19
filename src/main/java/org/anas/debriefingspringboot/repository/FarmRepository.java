package org.anas.debriefingspringboot.repository;

import org.anas.debriefingspringboot.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FarmRepository extends JpaRepository<Farm, UUID> {

    @Query(value = "SELECT * FROM farms f " +
            "JOIN fields fi ON f.id = fi.farm_id " +
            "GROUP BY f.id HAVING SUM(fi.area) < 4000", nativeQuery = true)
    List<Farm> findFarmsWithFieldAreaLessThan4000();
}
