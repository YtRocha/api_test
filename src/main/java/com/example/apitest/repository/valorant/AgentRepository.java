package com.example.apitest.repository.valorant;

import com.example.apitest.model.valorant.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * O repositorio para Agent
 */
public interface AgentRepository extends JpaRepository<Agent, String> {
}
