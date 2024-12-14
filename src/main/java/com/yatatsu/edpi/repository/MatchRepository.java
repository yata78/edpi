package com.yatatsu.edpi.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yatatsu.edpi.Entity.MMatch;

public interface MatchRepository extends JpaRepository<MMatch , Integer>{
    
    @Query(value = "SELECT * FROM m_match WHERE dpi_id = ?1 AND user_id = ?2", nativeQuery = true)
    List<MMatch> findByMatchIdAndUserId(Integer dpiId, Integer userId);
}
