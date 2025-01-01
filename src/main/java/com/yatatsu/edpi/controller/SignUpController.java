package com.yatatsu.edpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yatatsu.edpi.Entity.MUser;
import com.yatatsu.edpi.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class SignUpController {

    HttpSession session;
    UserRepository userRepository;

    @Autowired
    public SignUpController(HttpSession session, UserRepository userRepository) {
        this.session = session;
        this.userRepository = userRepository;
    }
    
    @GetMapping("/signup")
    public ModelAndView movePageToSignUpUser(ModelAndView mav) {
        mav.setViewName("signup");
        return mav;
    }

    @PostMapping("/signup")
    public ModelAndView signUpUser(ModelAndView mav,@ModelAttribute("MUser") @Validated MUser user, BindingResult bindingResult) {

        //バリデーションチェック
        if(bindingResult.hasErrors()) {
            mav.setViewName("signup");
            return mav;
        }

        MUser signUpUser = new MUser();

        signUpUser.setUserName(user.getUserName());
        signUpUser.setEmail(user.getEmail());
        
        userRepository.saveAndFlush(signUpUser);
        mav.setViewName("login");
        return mav;
    }
    
    
}
