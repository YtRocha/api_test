package com.example.apitest.model.yugioh;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Card {

    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String frameType;
    @Column(nullable = false, length = 1000)
    private String desc;
    @Column(nullable = false)
    private String race;
    @Column(nullable = false)
    private String archetype;
    private String ygoprodeck_url;
    private List<CardSet> card_sets;
}
