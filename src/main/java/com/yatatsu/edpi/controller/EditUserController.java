package com.yatatsu.edpi.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.yatatsu.edpi.Entity.MUser;
import com.yatatsu.edpi.repository.UserRepository;
import com.yatatsu.edpi.service.EdpiService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class EditUserController {

    HttpSession session;
    UserRepository userRepository;
    EdpiService edpiService;

    private static Logger logger = LoggerFactory.getLogger(EditUserController.class);

    @Autowired
    public EditUserController(HttpSession session, UserRepository userRepository, EdpiService edpiService) {
        this.session = session;
        this.userRepository = userRepository;
        this.edpiService = edpiService;
    }
    
    //ユーザ情報編集画面を表示
    @GetMapping("/editUser")
    public ModelAndView editUser(ModelAndView mav, @ModelAttribute("MUser") MUser mUser) {

        logger.debug("EditUserControllerのeditUserメソッド(GET)が呼ばれました。");

        Optional<MUser> data = userRepository.findById((Integer)session.getAttribute("userId"));
        MUser registUser = data.get();
        mav.addObject("user", registUser);
        mav.setViewName("editProfile");
        return mav;
    }

    //ユーザ情報を更新
    @Transactional
    @PostMapping("/editUser")
    public ModelAndView postMethodName(@ModelAttribute("MUser") @Validated MUser mUser, BindingResult bindingResult, ModelAndView mav) {

        logger.debug("EditUserControllerのpostMethodNameメソッド(POST)が呼ばれました。");

        //バリデーションチェック
        if (bindingResult.hasErrors()) {
            logger.error("バリデーションエラーが発生しました");
            mav.setViewName("editProfile");
            return mav;
        }

        //ユーザ情報を更新
        userRepository.updateData(mUser.getUserName(), mUser.getEmail());

        //dpi・ゲーム内感度・勝率・HS率を取得 
        List<Map<String,Object>> dpiList = edpiService.getEdpiList((Integer)session.getAttribute("userId"));

        mav.addObject("dpiList", dpiList);
        mav.setViewName("index");
        return mav;
    }
    
}
