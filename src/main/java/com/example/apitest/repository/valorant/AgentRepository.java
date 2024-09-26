package com.example.apitest.repository.valorant;

import com.example.apitest.model.valorant.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * O repositorio para Agent
 */
@Repository
public interface AgentRepository extends JpaRepository<Agent, String> {

    Optional<Agent> findByDisplayName(String name);

}
