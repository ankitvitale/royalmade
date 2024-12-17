package com.royalmade.mapper;

import com.royalmade.dto.AddressResponseDto;
import com.royalmade.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AddressMapper {

    public abstract AddressResponseDto toAddressResponseDto(Address address);
}
