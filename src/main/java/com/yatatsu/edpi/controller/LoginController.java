package com.yatatsu.edpi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yatatsu.edpi.Entity.MUser;
import com.yatatsu.edpi.Entity.UsersDpi;
import com.yatatsu.edpi.repository.DpiRepository;
import com.yatatsu.edpi.repository.MatchRepository;
import com.yatatsu.edpi.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    private HttpSession session;
    private UserRepository userRepository;
    private DpiRepository dpiRepository;
    private MatchRepository matchRepository;

    @Autowired
    public LoginController(HttpSession session , UserRepository userRepository, DpiRepository dpiRepository , MatchRepository matchRepository) {
        this.session = session;
        this.userRepository = userRepository;
        this.dpiRepository = dpiRepository;
        this.matchRepository = matchRepository;
    }
    
    @RequestMapping("/")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("login");
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView login(ModelAndView mav, @RequestParam Integer userId, @RequestParam String email) {
        // idとパスワードで分岐
        Optional<MUser> data = userRepository.findById(userId);
        MUser user = data.get();

        //userが取得出来たら
        if (!user.equals("NULL") && user.getEmail().equals(email)) {
                mav.setViewName("index");
                this.session.setAttribute("userId", userId);
                this.session.setAttribute("email", email);


                //dpi・ゲーム内感度・勝率・HS率を取得
                List<UsersDpi> dpi = dpiRepository.findByUserId(userId);
                
                List<Map<String,Object>> dpiList = new ArrayList<>();

                for (UsersDpi d : dpi) {
                    
                    //勝率とHS率を取得
                    String winRate =  matchRepository.countMatch(d.getDpiId()) != null ? String.format("%.1f", ((double)matchRepository.countWinMatch(d.getDpiId()) / matchRepository.countMatch(d.getDpiId())) * 100) : "0";
                    String hsRate = matchRepository.getAvgHsRate(d.getDpiId()) != null ? String.format("%.1f" ,matchRepository.getAvgHsRate(d.getDpiId())) : "0";
                    
                    dpiList.add(Map.of(
                        "dpiId" , d.getDpiId(),
                        "dpi"   , d.getDpi(),
                        "sensitivity" , d.getSensitivity(),
                        "winRate" , winRate,
                        "hsRate" , hsRate 
                    ));
                }

                mav.addObject("dpiList", dpiList);
        } else {
            mav.setViewName("login");
            mav.addObject("error", "名前もしくはパスワードが違います。");
        }
        return mav; 
    }
    
}
