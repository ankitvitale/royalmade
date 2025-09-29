package com.royalmade.mapper;

import com.royalmade.dto.*;
import com.royalmade.dto.MeterialDto.MaterialDto;
import com.royalmade.dto.MeterialDto.MaterialItemDto;
import com.royalmade.dto.MeterialDto.MaterialResponse;
import com.royalmade.entity.*;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "materialId")
    @Mapping(target = "items", source = "items")
    Material toEntity(MaterialDto dto);

    @Mapping(target = "id", source = "itemId")
    MaterialItem toEntity(MaterialItemDto dto);

    // Entity → DTO
    @Mapping(target = "materialId", source = "id")
    @Mapping(target = "items", source = "items")
    MaterialDto toDto(Material entity);

    @Mapping(target = "itemId", source = "id")
    @Mapping(target = "totaliteamPrice", expression = "java(item.getPrice() * item.getQuantity())")
    MaterialItemDto toDto(MaterialItem item);

    // Response mapping
    @Mapping(target = "materials", ignore = true)
    MaterialResponse toResponse(Material entity);

    List<MaterialItemDto> toItemDtos(List<MaterialItem> items);
}
