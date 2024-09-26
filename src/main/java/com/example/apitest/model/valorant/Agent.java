package com.example.apitest.model.valorant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A entidade de agentes do valorant
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agent {

    @Id
    private String uuid;
    @Column(nullable = false, unique = true)
    private String displayName;
    @Column(length = 1000)
    private String description;
    private String developerName;
    private String displayIcon;
    private String fullPortrait;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "agent_uuid")
    private List<Ability> abilities;

}
