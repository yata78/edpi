package com.yatatsu.edpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.yatatsu.edpi.Entity.UsersDpi;

@Repository
public interface DpiRepository extends JpaRepository<UsersDpi , Integer>{

    @Query(value = "SELECT * FROM users_dpi WHERE user_id = ?1", nativeQuery = true)
    List<UsersDpi> findByUserId(Integer id);
}
