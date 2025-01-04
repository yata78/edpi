package com.yatatsu.edpi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatatsu.edpi.Entity.UsersDpi;
import com.yatatsu.edpi.repository.DpiRepository;
import com.yatatsu.edpi.repository.MatchRepository;

@Service
public class EdpiService {

    DpiRepository dpiRepository;
    MatchRepository matchRepository;

    @Autowired
    public EdpiService(DpiRepository dpiRepository, MatchRepository matchRepository) {
        this.dpiRepository = dpiRepository;
        this.matchRepository = matchRepository;
    }
    
    public List<Map<String,Object>> getEdpiList(Integer userId) {

        //dpi・ゲーム内感度・勝率・HS率を取得
        List<UsersDpi> dpi = dpiRepository.findByUserId(userId);
        
        List<Map<String,Object>> dpiList = new ArrayList<>();

        for (UsersDpi d : dpi) {
            
            //勝率とHS率を取得
            //勝率を取得できない場合は0を返す
            String winRate =  matchRepository.countMatch(d.getDpiId()) > 0 ? String.format("%.1f", ((double)matchRepository.countWinMatch(d.getDpiId()) / matchRepository.countMatch(d.getDpiId())) * 100) : "0";
            //HS率を取得できない場合は0を返す
            String hsRate = matchRepository.getAvgHsRate(d.getDpiId()) > 0 ? String.format("%.1f" ,matchRepository.getAvgHsRate(d.getDpiId())) : "0";
            
            dpiList.add(Map.of(
                "dpiId" , d.getDpiId(),
                "dpi"   , d.getDpi(),
                "sensitivity" , d.getSensitivity(),
                "winRate" , winRate,
                "hsRate" , hsRate 
            ));
        }

        return dpiList;
    }

    //edpiを登録
    public void saveEdpi(Integer userId, UsersDpi usersDpi) {
        
        UsersDpi edpi = new UsersDpi();
        edpi.setDpi(usersDpi.getDpi());
        edpi.setSensitivity(usersDpi.getSensitivity());
        edpi.setUserId(userId);

        dpiRepository.saveAndFlush(edpi);
    }
}
