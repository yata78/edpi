package com.yatatsu.edpi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.yatatsu.edpi.Entity.MUser;



@Controller
public class LogoutController {
    
    private HttpSession session;

    private static Logger logger = LoggerFactory.getLogger(LogoutController.class);

    @Autowired
    public LogoutController(HttpSession session) {
        this.session = session;
    }

    //ログアウト処理(セッションを無効化)
    @GetMapping("/logout")
    public String Logout(ModelAndView mav, @ModelAttribute("MUser") MUser user) {

        logger.debug("LogoutControllerのLogoutメソッド(GET)が呼ばれました。");

        session.invalidate();
        return "redirect:/";
    }
    
}
