package com.yatatsu.edpi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yatatsu.edpi.Entity.UsersDpi;
import com.yatatsu.edpi.repository.DpiRepository;
import com.yatatsu.edpi.repository.MatchRepository;

public class EdpiServiceTest {

    @Mock
    private DpiRepository dpiRepository;

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks EdpiService edpiService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getEdpiListTest_正常系() {

        int expectSize = 2;
        int expectDpiId = 1;
        int expectDpiValue1 = 800;

        //返り値のテストデータ作成
        List<UsersDpi> expectDpi = new ArrayList<>();

        UsersDpi dpi1 = new UsersDpi();
        dpi1.setDpiId(1);
        dpi1.setUserId(1);
        dpi1.setDpi(800);
        dpi1.setSensitivity(new BigDecimal(0.291));

        UsersDpi dpi2 = new UsersDpi();
        dpi2.setDpiId(2);
        dpi2.setUserId(1);
        dpi2.setDpi(800);
        dpi2.setSensitivity(new BigDecimal(0.350));

        expectDpi.add(dpi1);
        expectDpi.add(dpi2);

        //モックの設定
        when(dpiRepository.findByUserId(1)).thenReturn(expectDpi);
        when(matchRepository.countMatch(1)).thenReturn(10);
        when(matchRepository.countWinMatch(1)).thenReturn(5);
        when(matchRepository.getAvgHsRate(1)).thenReturn(50.0f);

        //テスト実行
        List<Map<String, Object>> actualDpiList = edpiService.getEdpiList(1);

        //テスト結果確認
        assertEquals(expectSize, actualDpiList.size());
        assertEquals(expectDpiId, actualDpiList.get(0).get("dpiId"));
        assertEquals(expectDpiValue1, actualDpiList.get(0).get("dpi"));
    }
}
