package com.yatatsu.edpi.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="users_dpi")
public class UsersDpi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dpi_id")
    private Integer dpiId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="dpi")
    private Integer dpi;

    @Column(name="sensitivity")
    private BigDecimal sensitivity;
    
}
