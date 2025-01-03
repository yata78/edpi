package com.yatatsu.edpi.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yatatsu.edpi.Entity.MMatch;
import com.yatatsu.edpi.Entity.UsersDpi;
import com.yatatsu.edpi.repository.DpiRepository;
import com.yatatsu.edpi.repository.MatchRepository;
import com.yatatsu.edpi.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class EditEdpiController {
    
    HttpSession session;
    MatchRepository matchRepository;
    DpiRepository dpiRepository;

    public EditEdpiController(HttpSession session, UserRepository userRepository, MatchRepository matchRepository , DpiRepository dpiRepository) {
        this.session = session;
        this.matchRepository = matchRepository;
        this.dpiRepository = dpiRepository;
    }


    //EDPIの編集画面を表示
    @GetMapping("/editEdpi/{id}")
    public ModelAndView showDpi(ModelAndView mav, @RequestParam String dpiId, @ModelAttribute("MMatch") MMatch mMatch) {

        List<MMatch> getMMatch = matchRepository.findByMatchIdAndUserId(Integer.parseInt(dpiId), (Integer)session.getAttribute("userId"));
        mav.addObject("dpiId", dpiId);
        mav.addObject("matchList", getMMatch);
        mav.addObject("id", session.getAttribute("userId"));
        mav.setViewName("editEdpi");
        return mav;
    }
    
    //EDPIの勝敗とHS率を登録
    @PostMapping("/editEdpi/{id}")
    public ModelAndView editDpi(ModelAndView mav, @RequestParam String winLose, @ModelAttribute("MMatch") @Validated MMatch mMatch, BindingResult bindingResult) {

        //バリデーションチェック
        if (bindingResult.hasErrors()) {
            List<MMatch> getMMatch = matchRepository.findByMatchIdAndUserId(mMatch.getDpiId(), (Integer)session.getAttribute("userId"));
            mav.addObject("matchList", getMMatch);
            mav.setViewName("editEdpi");
            return mav;
        }
        
        // 試合の結果を登録(勝敗とHS率)
        MMatch registMatch = new MMatch();
        registMatch.setDpiId(mMatch.getDpiId());
        registMatch.setHsRate(mMatch.getHsRate());
        registMatch.setUserId((Integer)session.getAttribute("userId"));
        if(winLose.equals("1")) {
            registMatch.setWin(true);
        } else {
            registMatch.setWin(false);
        }
        matchRepository.saveAndFlush(registMatch);

        // 表示のために試合データを取得
        List<MMatch> getMMatch = matchRepository.findByMatchIdAndUserId(mMatch.getDpiId(), (Integer)session.getAttribute("userId"));
        mav.addObject("matchList", getMMatch);

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
    
    //EDPI登録画面を表示
    @GetMapping("/registEdpi")
    public ModelAndView registEdpi(ModelAndView mav, @ModelAttribute("UsersDpi") UsersDpi usersDpi) {
        mav.setViewName("registEdpi");
        return mav;
    }

    //EDPIの組み合わせを登録
    @PostMapping("/registEdpi")
    public ModelAndView registEdpi(ModelAndView mav, @ModelAttribute("UsersDpi") @Validated UsersDpi usersDpi, BindingResult bindingResult) {
        
        //バリデーションチェック
        if(bindingResult.hasErrors()) {
            mav.setViewName("registEdpi");
            return mav;
        }
        
        UsersDpi edpi = new UsersDpi();
        edpi.setDpi(usersDpi.getDpi());
        edpi.setSensitivity(usersDpi.getSensitivity());
        edpi.setUserId((Integer)session.getAttribute("userId"));

        dpiRepository.saveAndFlush(edpi);
        
       mav.setViewName("index");

       //dpi・ゲーム内感度・勝率・HS率を取得
        List<UsersDpi> dpi = dpiRepository.findByUserId((Integer)session.getAttribute("userId"));
        
        List<Map<String,Object>> dpiList = new ArrayList<>();

        for (UsersDpi d : dpi) {
            
            //勝率とHS率を取得
            String winRate =  matchRepository.countMatch(d.getDpiId()) > 0 ? String.format("%.1f", ((double)matchRepository.countWinMatch(d.getDpiId()) / matchRepository.countMatch(d.getDpiId())) * 100) : "0";
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

        return mav;
    }
    
}
