package com.yatatsu.edpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LogoutController {
    
    private HttpSession session;

    @Autowired
    public LogoutController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/logout")
    public ModelAndView Logout(ModelAndView mav) {
        session.invalidate();
        mav.setViewName("login");
        return mav;
    }
    
}
