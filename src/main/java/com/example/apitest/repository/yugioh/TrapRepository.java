package com.example.apitest.repository.yugioh;

import com.example.apitest.model.yugioh.Trap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrapRepository extends JpaRepository<Trap, Long> {
}
