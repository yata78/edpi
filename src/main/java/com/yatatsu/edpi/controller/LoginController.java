package com.yatatsu.edpi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class LoginController {

    private HttpSession session;

    @Autowired
    public LoginController(HttpSession session) {
        this.session = session;
    }
    
    @RequestMapping("/")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("login");
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView login(ModelAndView mav, @RequestParam String name, @RequestParam String email) {
        // 名前とパスワードで分岐
        // 後々DBから取得する
        if (("system".equals(name)) || ("system@example.jp".equals(email))) {
            mav.setViewName("index");
            this.session.setAttribute("name", name);
            this.session.setAttribute("email", email);
            mav.addObject("name", session.getAttribute("name"));
            mav.addObject("email", session.getAttribute("email"));

            //ダミーデータ準備

            List<Map<String, Object>> rankings = new ArrayList<>();
            rankings.add(Map.of("name", "User1", "sensitivity", 800, "dpi", 0.2, "edpi" , Math.floor(800 * 0.2)));
            rankings.add(Map.of("name", "User2", "sensitivity", 1600, "dpi", 0.15 , "edpi", Math.floor(1600 * 0.15)));

            mav.addObject("rankings", rankings);

        } else {
            mav.setViewName("login");
            mav.addObject("error", "名前もしくはパスワードが違います。");
        }
        return mav; 
    }
    
    
}
