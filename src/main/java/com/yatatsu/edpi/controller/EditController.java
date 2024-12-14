package com.yatatsu.edpi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.yatatsu.edpi.Entity.MMatch;
import com.yatatsu.edpi.Entity.MUser;
import com.yatatsu.edpi.repository.MatchRepository;
import com.yatatsu.edpi.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EditController {

    HttpSession session;
    UserRepository userRepository;
    MatchRepository matchRepository;

    public EditController(HttpSession session, UserRepository userRepository, MatchRepository matchRepository) {
        this.session = session;
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
    }
    
    @GetMapping("/editUser")
    public ModelAndView editUser(ModelAndView mav) {
        Optional<MUser> data = userRepository.findById((Integer)session.getAttribute("userId"));
        MUser user = data.get();
        //TODO:テーブルから取得する情報の吟味
        mav.addObject("user", user);
        mav.setViewName("editProfile");
        return mav;
    }

    @Transactional
    @PostMapping("/editUser")
    public ModelAndView postMethodName(@ModelAttribute MUser user, ModelAndView mav, @RequestParam String userName, @RequestParam String email) {
        userRepository.updateData(userName, email);
        Optional<MUser> data = userRepository.findById((Integer)session.getAttribute("userId"));
        MUser updateUser = data.get();
        mav.addObject("user", updateUser);
        mav.setViewName("editProfile");
        return mav;
    }

    @GetMapping("/editEdpi/{id}")
    public ModelAndView showDpi(ModelAndView mav, @PathVariable Integer id) {

        List<MMatch> mMatch = matchRepository.findByMatchIdAndUserId(id, (Integer)session.getAttribute("userId"));
        mav.addObject("matchList", mMatch);
        mav.addObject("id", session.getAttribute("userId"));
        mav.setViewName("editEdpi");
        return mav;
    }
    
    @PostMapping("/editEdpi/{id}")
    public ModelAndView editDpi(ModelAndView mav, @PathVariable String id) {
        mav.setViewName("editEdpi");
        return mav;
    }
    
}
