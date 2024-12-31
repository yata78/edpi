package com.yatatsu.edpi.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yatatsu.edpi.Entity.MMatch;

public interface MatchRepository extends JpaRepository<MMatch , Integer>{
    
    @Query(value = "SELECT * FROM m_match WHERE dpi_id = ?1 AND user_id = ?2", nativeQuery = true)
    List<MMatch> findByMatchIdAndUserId(Integer dpiId, Integer userId);

    //dpiIdに紐づく試合数を取得
    @Query(value = "SELECT COUNT(*) FROM m_match WHERE dpi_id = ?1" , nativeQuery = true)
    Integer countMatch(Integer dpiId);

    //dpiIdに紐づき、勝ち試合を取得
    @Query(value = "SELECT COUNT(*) FROM m_match WHERE dpi_id = ?1 AND isWin = true" , nativeQuery = true)
    Integer countWinMatch(Integer dpiId);

    //HS率の平均を取得
    @Query(value = "SELECT AVG(hs_rate) FROM m_match WHERE dpi_id = ?1" , nativeQuery = true)
    Float getAvgHsRate(Integer dpiId);
}
