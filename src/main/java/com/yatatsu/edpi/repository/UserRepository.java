package com.yatatsu.edpi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yatatsu.edpi.Entity.MUser;

@Repository
public interface UserRepository extends JpaRepository<MUser , Integer> {

    //TODO:user_idの指定を動的にできるように変更
    @Query(value = "SELECT * FROM m_user WHERE user_id = ?1", nativeQuery = true)
    Optional<MUser> findById(Integer id);

    @Modifying
    @Query(value = "UPDATE m_user SET user_name = ?1 , email = ?2" , nativeQuery = true)
    int updateData(String userName, String email);
}
