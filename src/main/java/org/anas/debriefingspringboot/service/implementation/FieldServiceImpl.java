package org.anas.debriefingspringboot.service.implementation;

import org.anas.debriefingspringboot.exceptions.MaximumFieldAreaException;
import org.anas.debriefingspringboot.exceptions.MinimumFieldAreaException;
import org.anas.debriefingspringboot.service.FieldService;
import org.springframework.stereotype.Service;
import org.anas.debriefingspringboot.domain.Farm;
import org.anas.debriefingspringboot.domain.Field;
import org.anas.debriefingspringboot.service.dto.FieldDTO;
import org.anas.debriefingspringboot.service.dto.mapper.FieldMapper;
import org.anas.debriefingspringboot.repository.FieldRepository;

@Service
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;

    public FieldServiceImpl(FieldRepository fieldRepository, FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
    }

    @Override
    public FieldDTO assignField(FieldDTO fieldDTO, Farm farm) {
        if (fieldDTO.getArea() < 0.1) {
            throw new MinimumFieldAreaException("Field area must be at least 0.1 hectare (1000 m²)");
        }

        double maxFieldArea = farm.getArea() * 0.5;
        if (fieldDTO.getArea() > maxFieldArea) {
            throw new MaximumFieldAreaException("Field area cannot exceed 50% of the farm's total area");
        }

        Field field = fieldMapper.toEntity(fieldDTO);
        field.setFarm(farm);

        farm.getFields().add(field);

        Field savedField = fieldRepository.save(field);

        return fieldMapper.toDTO(savedField);
    }
}
