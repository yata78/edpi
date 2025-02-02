package com.yatatsu.edpi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yatatsu.edpi.Entity.MUser;

@Repository
public interface UserRepository extends JpaRepository<MUser , Integer> {

    //ユーザー名とメールアドレスでユーザーを検索
    @Query(value = "SELECT * FROM m_user WHERE user_name = ?1 and email = ?2 ", nativeQuery = true)
    Optional<MUser> findUserByNameAndEmail(String userName , String email);

    //ユーザー名でユーザを検索し、ユーザー名とメールアドレスを更新
    @Modifying
    @Query(value = "UPDATE m_user SET user_name = ?1 , email = ?2" , nativeQuery = true)
    int updateData(String userName, String email);
}
