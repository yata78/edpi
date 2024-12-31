package com.yatatsu.edpi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yatatsu.edpi.Entity.MMatch;
import com.yatatsu.edpi.repository.MatchRepository;
import com.yatatsu.edpi.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class EditEdpiController {
    
    HttpSession session;
    MatchRepository matchRepository;

    public EditEdpiController(HttpSession session, UserRepository userRepository, MatchRepository matchRepository) {
        this.session = session;
        this.matchRepository = matchRepository;
    }


    @GetMapping("/editEdpi/{id}")
    public ModelAndView showDpi(ModelAndView mav, @RequestParam String dpiId) {

        List<MMatch> mMatch = matchRepository.findByMatchIdAndUserId(Integer.parseInt(dpiId), (Integer)session.getAttribute("userId"));
        mav.addObject("dpiId", dpiId);
        mav.addObject("matchList", mMatch);
        mav.addObject("id", session.getAttribute("userId"));
        mav.setViewName("editEdpi");
        return mav;
    }
    
    // EDPIの勝敗とHS率を登録している
    @PostMapping("/editEdpi/{id}")
    public ModelAndView editDpi(ModelAndView mav, @RequestParam String dpiId, @RequestParam String winLose , @RequestParam Integer hsRate) {
        
        // 試合の結果を登録(勝敗とHS率)
        MMatch registMatch = new MMatch();
        registMatch.setDpiId(Integer.parseInt(dpiId));
        registMatch.setHsRate(hsRate);
        registMatch.setUserId((Integer)session.getAttribute("userId"));
        if(winLose.equals("1")) {
            registMatch.setWin(true);
        } else {
            registMatch.setWin(false);
        }
        matchRepository.saveAndFlush(registMatch);

        // 表示のために試合データを取得
        List<MMatch> mMatch = matchRepository.findByMatchIdAndUserId(Integer.parseInt(dpiId), (Integer)session.getAttribute("userId"));
        mav.addObject("matchList", mMatch);

        mav.setViewName("editEdpi");
        return mav;
        
    }

    //登録した試合データを削除
    @GetMapping("deleteMatch/{matchId}")
    public ModelAndView deleteMatch(ModelAndView mav , @PathVariable Integer matchId , @RequestParam String dpiId) {
        //削除処理
        matchRepository.deleteById(matchId);

        // 表示のために試合データを取得
        List<MMatch> mMatch = matchRepository.findByMatchIdAndUserId(Integer.parseInt(dpiId), (Integer)session.getAttribute("userId"));
        mav.addObject("dpiId", dpiId);
        mav.addObject("matchList", mMatch);
        mav.addObject("id", session.getAttribute("userId"));
        mav.setViewName("editEdpi");
        return mav;
    }
    

}
