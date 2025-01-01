package com.yatatsu.edpi.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Singular;

@Entity
@Data
@Table(name="m_match")
public class MMatch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="match_id")
    private Integer matchId;

    @Column(name="dpi_id")
    @NotNull
    private Integer dpiId;
    
    @Column(name="user_id")
    @NotNull
    private Integer userId;

    @Column(name="iswin")
    @NotNull
    private boolean isWin;

    @Column(name="hs_rate")
    @NotNull
    @Size(min=0, max=100)
    private Integer hsRate;

}