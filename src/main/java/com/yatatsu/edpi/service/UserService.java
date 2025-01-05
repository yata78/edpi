package com.yatatsu.edpi.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatatsu.edpi.Entity.MUser;
import com.yatatsu.edpi.repository.UserRepository;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //ユーザの重複チェック
    public boolean isAlredyRegistUser(String userName, String email) {

        Optional<MUser> data = userRepository.findUserByNameAndEmail(userName, email);

        if (data.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    //ユーザ名とパスワードでユーザを取得
    public MUser getMUser(String userName, String email) {
        
        Optional<MUser> data = userRepository.findUserByNameAndEmail(userName, email);

        //データが取得できなかった場合に例外を投げる
        data.orElseThrow(() -> new NoSuchElementException("データが存在しません。"));
    
        MUser Loginuser = data.get();

        return Loginuser;
    }

    // ユーザ登録
    public void signUpUser(String userName, String email) {
        MUser signUpUser = new MUser();

        signUpUser.setUserName(userName);
        signUpUser.setEmail(email);
        
        userRepository.saveAndFlush(signUpUser);
    }

}
