package com.yatatsu.edpi.Entity;


import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name="m_match")
public class MMatch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="match_id")
    private Integer matchId;

    @Column(name="dpi_id")
    private Integer dpiId;
    
    @Column(name="user_id")
    private Integer userId;

    @Column(name="iswin")
    @NotNull
    private boolean isWin;

    @Column(name="hs_rate")
    @NotNull
    @Range(min = 0, max = 100, message = "0から100までの値を入力してください。")
    private Integer hsRate;

}