package com.example.apitest.repository.yugioh;

import com.example.apitest.model.yugioh.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardSetRepository extends JpaRepository<CardSet, String> {
}
