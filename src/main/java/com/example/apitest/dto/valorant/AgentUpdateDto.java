package com.example.apitest.dto.valorant;

import com.example.apitest.model.valorant.Ability;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentUpdateDto {

    private String displayName;
    private String description;
    private String developerName;
    private String displayIcon;
    private String fullPortrait;
    private List<Ability> abilities;
}
