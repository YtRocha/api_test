package com.example.apitest.repository.yugioh;

import com.example.apitest.model.yugioh.Monster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long> {
}
