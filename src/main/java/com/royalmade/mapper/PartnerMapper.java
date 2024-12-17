package com.royalmade.mapper;

import com.royalmade.dto.PartnerDto;
import com.royalmade.entity.Partner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PartnerMapper {


    public abstract PartnerDto toPartnerDto(Partner partner);
}
