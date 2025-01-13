package com.yatatsu.edpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.yatatsu.edpi.Entity.MUser;
import com.yatatsu.edpi.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@Log4j2
public class SignUpController {

    HttpSession session;
    UserService userService;

    @Autowired
    public SignUpController(HttpSession session, UserService userService) {
        this.session = session;
        this.userService = userService;
    }

    //ユーザ登録画面を表示
    @GetMapping("/signup")
    public ModelAndView movePageToSignUpUser(ModelAndView mav, @ModelAttribute("MUser") MUser user) {

        log.info("SignUpControllerのmovePageToSignUpUserメソッド(GET)が呼ばれました。");

        mav.setViewName("signup");
        return mav;
    }

    //ユーザ登録処理
    @PostMapping("/signup")
    public ModelAndView signUpUser(ModelAndView mav,@ModelAttribute("MUser") @Validated MUser user, BindingResult bindingResult) {

        log.info("SignUpControllerのsignUpUserメソッド(POST)が呼ばれました。");

        //バリデーションチェック
        if(bindingResult.hasErrors()) {
            log.error("バリデーションエラーが発生しました");
            mav.setViewName("signup");
            return mav;
        }

        //ユーザの重複チェック
        if (userService.isAlredyRegistUser(user.getUserName(), user.getEmail())) {
            //ユーザ登録
            userService.signUpUser(user.getUserName(), user.getEmail());
        } else {
            log.error("userの重複が発生しました");
            mav.addObject("error","既にユーザが存在します");
            mav.setViewName("signup");
            return mav;
        }
        
        mav.setViewName("login");
        return mav;
    }
    
}