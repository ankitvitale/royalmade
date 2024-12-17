package com.royalmade.mapper;


import com.royalmade.dto.*;
import com.royalmade.entity.Address;
import com.royalmade.entity.Land;
import com.royalmade.entity.Partner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = { PartnerMapper.class, AddressMapper.class })
public abstract class LandMapper {



    @Mapping(source = "partners", target = "partners")
    @Mapping(target = "soldAmount", expression = "java(land.getTotalAmount() - (land.getAgreementAmount() + land.getTokenAmount()))")
    public abstract LandDto toLandDto(Land land);



    public abstract Land toLand(LandRequestDto landRequestDto);

    @Mapping(target = "soldAmount", expression = "java(" +
            "land.getTotalAmount() + 1000 - (land.getAgreementAmount() + land.getTokenAmount())" +
            ")")
    public abstract LandResponseDto toLandResponseDto( Land land);


    public abstract  List<PartnerDto> toPartnerResponseDtos(Set<Partner> partners);
}
