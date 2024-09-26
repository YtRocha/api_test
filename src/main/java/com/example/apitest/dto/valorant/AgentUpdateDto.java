package com.example.apitest.dto.valorant;

import com.example.apitest.model.valorant.Ability;
import lombok.Data;

import java.util.List;

@Data
public class AgentUpdateDto {

    private String displayName;
    private String description;
    private String developerName;
    private String displayIcon;
    private String fullPortrait;
    private List<Ability> abilities;
}
