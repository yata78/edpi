package com.yatatsu.edpi.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.yatatsu.edpi.Entity.MUser;
import com.yatatsu.edpi.repository.MatchRepository;
import com.yatatsu.edpi.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EditUserController {

    HttpSession session;
    UserRepository userRepository;
    MatchRepository matchRepository;

    public EditUserController(HttpSession session, UserRepository userRepository, MatchRepository matchRepository) {
        this.session = session;
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
    }
    
    @GetMapping("/editUser")
    public ModelAndView editUser(ModelAndView mav, @ModelAttribute("MUser") MUser mUser) {
        Optional<MUser> data = userRepository.findById((Integer)session.getAttribute("userId"));
        MUser registUser = data.get();
        //TODO:テーブルから取得する情報の吟味
        mav.addObject("user", registUser);
        mav.setViewName("editProfile");
        return mav;
    }

    @Transactional
    @PostMapping("/editUser")
    public ModelAndView postMethodName(@ModelAttribute("MUser") @Validated MUser mUser, BindingResult bindingResult, ModelAndView mav) {

        //バリデーションチェック
        if (bindingResult.hasErrors()) {
            mav.setViewName("editProfile");
            return mav;
        }

        userRepository.updateData(mUser.getUserName(), mUser.getEmail());
        Optional<MUser> data = userRepository.findById((Integer)session.getAttribute("userId"));
        MUser updateUser = data.get();
        mav.addObject("user", updateUser);
        mav.setViewName("editProfile");
        return mav;
    }
    
}
