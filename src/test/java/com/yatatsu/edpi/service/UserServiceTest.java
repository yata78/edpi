package com.yatatsu.edpi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yatatsu.edpi.Entity.MUser;
import com.yatatsu.edpi.repository.UserRepository;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void getMUserTest_正常系() {

        //返り値のテストデータ作成
        MUser expectUser = new MUser();
        expectUser.setUserId(1);
        expectUser.setUserName("test");
        expectUser.setEmail("test@example.co.jp");

        when(userRepository.findUserByNameAndEmail("test", "test@example.co.jp")).thenReturn(Optional.of(expectUser));

        //テスト実行
        String actualName = "test";
        String actualEmail = "test@example.co.jp";
        MUser actualUser = userService.getMUser(actualName, actualEmail);

        //結果比較
        assertEquals(expectUser.getUserName(), actualUser.getUserName());
        assertEquals(expectUser.getEmail(), actualUser.getEmail());

    }

    @Test
    public void getMUserTest_異常系() {

        //返り値のテストデータ作成
        MUser expectUser = new MUser();
        expectUser.setUserId(1);
        expectUser.setUserName("test");
        expectUser.setEmail("test@example.co.jp");
        NullPointerException expectException = new NullPointerException();

        when(userRepository.findUserByNameAndEmail("test", "test@example.co.jp")).thenReturn(Optional.empty());

        //テスト実行
        String actualName = "test";
        String actualEmail = "test@example.co.jp";

        //DBからデータを取得できない場合に例外が発生することを確認
        try {
            MUser actualUser = userService.getMUser(actualName, actualEmail);
        } catch (NullPointerException e) {
            assertEquals(expectException.getClass(), e.getClass());
        }

    }
}
