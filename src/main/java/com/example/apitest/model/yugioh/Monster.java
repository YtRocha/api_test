package com.example.apitest.model.yugioh;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Monster extends Card{

    @Column(nullable = false)
    private Integer atk;
    @Column(nullable = false)
    private Integer def;
    @Column(nullable = false)
    private Integer level;
    @Column(nullable = false)
    private String attribute;
}
