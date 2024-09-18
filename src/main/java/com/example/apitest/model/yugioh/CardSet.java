package com.example.apitest.model.yugioh;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class CardSet {

    @Id
    @Column(length = 10)
    private String set_code;
    @Column(nullable = false)
    private String set_name;
    @Column(nullable = false)
    private Integer num_of_cards;
    @Column(nullable = false)
    private LocalDate tcg_date;
}
