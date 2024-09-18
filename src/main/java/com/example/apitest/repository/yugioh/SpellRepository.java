package com.example.apitest.repository.yugioh;

import com.example.apitest.model.yugioh.Spell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpellRepository extends JpaRepository<Spell, Long> {
}
