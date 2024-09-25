package com.example.apitest.model.valorant;

import jakarta.persistence.*;
import lombok.Data;

/**
 * A entidade das habilidades dos agentes
 */
@Entity
@Data
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false)
    private AbilitySlot slot;
    @Column(nullable = false)
    private String displayName;
    @Column(nullable = false, length = 1000)
    private String description;
    private String displayIcon;

}
