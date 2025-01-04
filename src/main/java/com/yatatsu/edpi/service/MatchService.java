package com.yatatsu.edpi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatatsu.edpi.Entity.MMatch;
import com.yatatsu.edpi.repository.MatchRepository;

@Service
public class MatchService {
    
    MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    // 試合の結果を登録(勝敗とHS率)
    public void saveMatch(String winLose, Integer userId, MMatch mMatch) {

        MMatch registMatch = new MMatch();
        registMatch.setDpiId(mMatch.getDpiId());
        registMatch.setHsRate(mMatch.getHsRate());
        registMatch.setUserId(userId);
        if(winLose.equals("1")) {
            registMatch.setWin(true);
        } else {
            registMatch.setWin(false);
        }
        matchRepository.saveAndFlush(registMatch);
        
    }
}
