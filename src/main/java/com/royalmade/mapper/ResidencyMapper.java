package com.royalmade.mapper;



import com.royalmade.dto.ResidencyDto;
import com.royalmade.entity.Residency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ResidencyMapper {


    public abstract Residency toResidency(ResidencyDto residencyDto);
}
