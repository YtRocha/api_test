package com.example.apitest.mapper.valorant;

import com.example.apitest.dto.valorant.AgentDto;
import com.example.apitest.model.valorant.Agent;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgentMapper {

    Agent toEntity(AgentDto dto);

    List<Agent> toEntityList(List<AgentDto> dtoList);

    AgentDto toDto(Agent entity);

    List<AgentDto> toDtoList(List<Agent> entityList);
}