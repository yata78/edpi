package com.yatatsu.edpi.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yatatsu.edpi.Entity.MUser;
import com.yatatsu.edpi.service.EdpiService;
import com.yatatsu.edpi.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@Log4j2
public class LoginController {

    private HttpSession session;
    private UserService userService;
    private EdpiService edpiService;

    @Autowired
    public LoginController(HttpSession session , UserService userService , EdpiService edpiService) {
        this.session = session;
        this.userService = userService;
        this.edpiService = edpiService;
    }
    
    //ログイン画面を表示
    @RequestMapping("/")
    public ModelAndView getLogin(ModelAndView mav,@ModelAttribute("MUser") MUser user) {

        log.debug("LoginControllerのgetLoginメソッド(GET)が呼ばれました。");

        mav.setViewName("login");
        return mav;
    }

    //ホーム画面遷移
    @GetMapping("/login")
    public ModelAndView getLogin(ModelAndView mav,@ModelAttribute("MUser") @Validated MUser user, BindingResult bindingResult) {

        log.info("LoginControllerのpostLoginメソッド(Get)が呼ばれました。");

        //dpi・ゲーム内感度・勝率・HS率を取得 
        List<Map<String,Object>> dpiList = edpiService.getEdpiList((Integer)session.getAttribute("userId"));

        log.info("userId:" + (Integer)session.getAttribute("userId"));

        mav.addObject("dpiList", dpiList);
        mav.setViewName("index");
        return mav; 
    }

    //ログイン処理
    @PostMapping("/login")
    public ModelAndView postLogin(ModelAndView mav,@ModelAttribute("MUser") @Validated MUser user, BindingResult bindingResult) {

        log.info("LoginControllerのpostLoginメソッド(POST)が呼ばれました。");
        
        //バリデーションチェック
        if(bindingResult.hasErrors()) {
            log.error("バリデーションエラーが発生しました");
            System.out.println(bindingResult.getAllErrors());
            mav.setViewName("login");
            return mav;
        }

        try {
            MUser loginUser = userService.getMUser(user.getUserName(), user.getEmail());

            //DBからユーザを取得できた場合にセッションにユーザ情報をセット
            this.session.setAttribute("userId", loginUser.getUserId());
            this.session.setAttribute("email", loginUser.getEmail());

            //dpi・ゲーム内感度・勝率・HS率を取得 
            List<Map<String,Object>> dpiList = edpiService.getEdpiList((Integer)session.getAttribute("userId"));

            log.debug("userId:" + loginUser.getUserId());

            mav.addObject("dpiList", dpiList);
            mav.setViewName("index");

        } catch (NoSuchElementException e) {
            //DBからユーザを取得できなかった場合
            mav.addObject("error", "ユーザ名もしくはパスワードが違います");
            mav.setViewName("login");
        }
        return mav; 
    }
    
}