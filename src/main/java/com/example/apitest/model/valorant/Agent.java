package com.example.apitest.model.valorant;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * A entidade de agentes do valorant
 */
@Entity
@Data
public class Agent {

    @Id
    private String uuid;
    @Column(nullable = false)
    private String displayName;
    private String description;
    private String developerName;
    private String displayIcon;
    private String fullPortrait;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "agent_uuid")
    private List<Ability> abilities;

}