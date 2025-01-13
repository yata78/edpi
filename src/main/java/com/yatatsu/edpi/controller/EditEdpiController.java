package com.yatatsu.edpi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.yatatsu.edpi.repository.MatchRepository;
import com.yatatsu.edpi.repository.UserRepository;
import com.yatatsu.edpi.service.EdpiService;
import com.yatatsu.edpi.service.MatchService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;


@Controller
@Log4j2
public class EditEdpiController {
    
    HttpSession session;
    MatchRepository matchRepository;
    EdpiService edpiService;
    MatchService matchService;

    @Autowired
    public EditEdpiController(HttpSession session, UserRepository userRepository, MatchRepository matchRepository , 
                                EdpiService edpiService, MatchService matchService) {
        this.session = session;
        this.matchRepository = matchRepository;
        this.edpiService = edpiService;
        this.matchService = matchService;
    }


    //EDPIの編集画面を表示
    @GetMapping("/editEdpi/{id}")
    public ModelAndView showDpi(ModelAndView mav, @RequestParam String dpiId, @ModelAttribute("MMatch") MMatch mMatch) {

        log.info("EditEdpiControllerのshowDpiメソッド(GET)が呼ばれました。");

        List<MMatch> getMMatch = matchRepository.findByMatchIdAndUserId(Integer.parseInt(dpiId), (Integer)session.getAttribute("userId"));
        mav.addObject("dpiId", dpiId);
        mav.addObject("matchList", getMMatch);
        mav.addObject("id", session.getAttribute("userId"));
        mav.setViewName("editEdpi");
        return mav;
    }
    
    @PostMapping("/editEdpi/{id}")
    public ModelAndView editDpi(ModelAndView mav, @PathVariable("id") Integer dpiId, 
                                @RequestParam String winLose, 
                                @ModelAttribute("MMatch") @Validated MMatch mMatch, 
                                BindingResult bindingResult) {

        log.info("EditEdpiControllerのeditDpiメソッド(POST)が呼ばれました。");

        // バリデーションチェック
        if (bindingResult.hasErrors()) {
            log.error("バリデーションエラーが発生しました");
            List<MMatch> getMMatch = matchRepository.findByMatchIdAndUserId(mMatch.getDpiId(), (Integer)session.getAttribute("userId"));
            mav.addObject("dpiId", dpiId);
            mav.addObject("matchList", getMMatch);
            mav.addObject("id", session.getAttribute("userId"));
            mav.addObject("matchList", getMMatch);
            mav.setViewName("editEdpi");  // エラー時はそのままビューを表示
            return mav;
        }
        

        // 試合の結果を登録(勝敗とHS率)
        matchService.saveMatch(winLose, (Integer)session.getAttribute("userId"), mMatch);

        // 表示のために試合データを取得
        List<MMatch> getMMatch = matchRepository.findByMatchIdAndUserId(mMatch.getDpiId(), (Integer)session.getAttribute("userId"));
        mav.addObject("dpiId", dpiId);
        mav.addObject("matchList", getMMatch);
        mav.addObject("id", session.getAttribute("userId"));

        mav.setViewName("redirect:/editEdpi/" + dpiId);

        return mav;
    }

    //登録した試合データを削除
    @GetMapping("deleteMatch/{matchId}")
    public ModelAndView deleteMatch(ModelAndView mav , @PathVariable Integer matchId , @RequestParam String dpiId) {

        log.info("EditEdpiControllerのdeleteMatchメソッド(GET)が呼ばれました。");

        //削除処理
        matchRepository.deleteById(matchId);

        // 表示のために試合データを取得
        List<MMatch> getMMatch = matchRepository.findByMatchIdAndUserId(Integer.parseInt(dpiId), (Integer)session.getAttribute("userId"));
        mav.addObject("dpiId", dpiId);
        mav.addObject("matchList", getMMatch);
        mav.addObject("id", session.getAttribute("userId"));

        mav.setViewName("redirect:/editEdpi/" + dpiId);
        return mav;
    }
    
    //EDPI登録画面を表示
    @GetMapping("/registEdpi")
    public ModelAndView registEdpi(ModelAndView mav, @ModelAttribute("UsersDpi") UsersDpi usersDpi) {

        log.info("EditEdpiControllerのregistEdpiメソッド(GET)が呼ばれました。");

        mav.setViewName("registEdpi");
        return mav;
    }

    //EDPIの組み合わせを登録
    @PostMapping("/registEdpi")
    public ModelAndView registEdpi(ModelAndView mav, @ModelAttribute("UsersDpi") @Validated UsersDpi usersDpi, BindingResult bindingResult) {

        log.info("EditEdpiControllerのregistEdpiメソッド(POST)が呼ばれました。");
        
        //バリデーションチェック
        if(bindingResult.hasErrors()) {
            log.error("バリデーションエラーが発生しました");
            mav.setViewName("registEdpi");
            return mav;
        }

        //edpiの重複チェック
        if(edpiService.isAlredyRegistEdpi((Integer) session.getAttribute("userId"), usersDpi.getDpi(), usersDpi.getSensitivity())) {
            //edpiを登録
            edpiService.saveEdpi((Integer)session.getAttribute("userId"), usersDpi);
        } else {
            log.error("edpiの重複が発生しました");
            mav.addObject("error", "既に登録されています");
            mav.setViewName("registEdpi");
            return mav;
        }

        
        mav.setViewName("index");

       //dpi・ゲーム内感度・勝率・HS率を取得        
        List<Map<String,Object>> dpiList = edpiService.getEdpiList((Integer)session.getAttribute("userId"));

        mav.addObject("dpiList", dpiList);

        return mav;
    }
    
}