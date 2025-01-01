package com.yatatsu.edpi.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    private Integer userId;

    @Column(name="dpi")
    @Size(min=0, max=5000)
    @NotNull
    private Integer dpi;

    @Column(name="sensitivity")
    @NotNull
    @Size(min=0, max = 10)
    private BigDecimal sensitivity;
    
}
