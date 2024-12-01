package com.yatatsu.edpi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yatatsu.edpi.Entity.MUser;
import com.yatatsu.edpi.repository.UserRepository;

@Controller
public class EditController {

    @Autowired
    UserRepository repository;
    
    @GetMapping("/editUser")
    public ModelAndView editUser(ModelAndView mav) {
        Optional<MUser> data = repository.findById(1);
        MUser user = data.get();
        //TODO:テーブルから取得する情報の吟味
        mav.addObject("userName", user.getUserName());
        mav.setViewName("editProfile");
        return mav;
    }
}
