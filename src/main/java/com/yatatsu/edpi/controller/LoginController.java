package com.yatatsu.edpi.controller;

import java.lang.foreign.Linker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yatatsu.edpi.Entity.MUser;
import com.yatatsu.edpi.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class LoginController {

    private HttpSession session;
    private UserRepository repository;

    @Autowired
    public LoginController(HttpSession session , UserRepository repository) {
        this.session = session;
        this.repository = repository;
    }
    
    @RequestMapping("/")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("login");
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView login(ModelAndView mav, @RequestParam Integer userId, @RequestParam String email) {
        // idとパスワードで分岐
        Optional<MUser> data = repository.findById(userId);
        MUser user = data.get();

        //userが取得出来たら
        if (!user.equals("NULL") && user.getEmail().equals(email)) {
                mav.setViewName("index");
                this.session.setAttribute("userId", userId);
                this.session.setAttribute("email", email);               
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
