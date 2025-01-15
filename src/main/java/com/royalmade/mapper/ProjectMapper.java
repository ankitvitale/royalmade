package com.royalmade.mapper;

import com.royalmade.dto.ProjectRequestDto;
import com.royalmade.dto.ProjectResponseDto;
import com.royalmade.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { LandMapper.class })
public abstract class ProjectMapper {




 // public abstract ProjectResponseDto toProjectResponseDto(Project project);
    public abstract Project toProject(ProjectRequestDto projectRequestDto);

  public  abstract ProjectResponseDto toProjectResponseDto(Project project);





}
