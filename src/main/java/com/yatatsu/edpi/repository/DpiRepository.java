package com.yatatsu.edpi.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.yatatsu.edpi.Entity.UsersDpi;

@Repository
public interface DpiRepository extends JpaRepository<UsersDpi , Integer>{

    //dpiとゲーム内感度の組み合わせを取得
    @Query(value = "SELECT * FROM user_id WHERE user_id = ?1 AND dpi = ?2 AND sensitivity = ?3" , nativeQuery = true)
    Optional<UsersDpi> getEdpiByUserId(Integer userId, Integer dpi, BigDecimal sensitivity);

    //userIdに紐づくDPIを取得
    @Query(value = "SELECT * FROM users_dpi WHERE user_id = ?1", nativeQuery = true)
    List<UsersDpi> findByUserId(Integer id);
}
