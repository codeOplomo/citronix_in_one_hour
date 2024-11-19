package org.anas.debriefingspringboot.service.dto.mapper;


import org.anas.debriefingspringboot.domain.Farm;
import org.anas.debriefingspringboot.service.dto.FarmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface FarmMapper {
    @Mapping(target = "creationDate", ignore = true)
    Farm toEntity(FarmDTO farmDTO);

    @Mapping(target = "creationDate", source = "creationDate")
    FarmDTO toDTO(Farm farm);
}

