package org.anas.debriefingspringboot.service.dto.mapper;


import org.anas.debriefingspringboot.domain.Field;
import org.anas.debriefingspringboot.service.dto.FieldDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldMapper {
 // Automatically map farmId to farm's id
    Field toEntity(FieldDTO fieldDTO);

    FieldDTO toDTO(Field field);
}
