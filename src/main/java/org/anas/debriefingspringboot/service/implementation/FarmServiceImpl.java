package org.anas.debriefingspringboot.service.implementation;

import org.anas.debriefingspringboot.domain.Farm;
import org.anas.debriefingspringboot.exceptions.FarmNotFoundException;
import org.anas.debriefingspringboot.exceptions.NullFarmException;
import org.anas.debriefingspringboot.repository.FarmRepository;
import org.anas.debriefingspringboot.service.FarmService;
import org.anas.debriefingspringboot.service.FieldService;
import org.anas.debriefingspringboot.service.dto.FarmDTO;
import org.anas.debriefingspringboot.service.dto.FieldDTO;
import org.anas.debriefingspringboot.service.dto.mapper.FarmMapper;
import org.anas.debriefingspringboot.service.dto.mapper.FieldMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Qualifier("farmServiceWithFields")
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;
    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    public FarmServiceImpl(FarmRepository farmRepository, FarmMapper farmMapper, FieldService fieldService, FieldMapper fieldMapper) {
        this.farmRepository = farmRepository;
        this.farmMapper = farmMapper;
        this.fieldService = fieldService;
        this.fieldMapper = fieldMapper;
    }

    @Override
    public FarmDTO createFarm(FarmDTO farmDTO) {
        if (farmDTO == null) {
            throw new NullFarmException("Farm cannot be null");
        }

        Farm farm = farmMapper.toEntity(farmDTO);

        farm.setCreationDate(java.time.LocalDate.now());

        Farm savedFarm = farmRepository.save(farm);

        if (farmDTO.getFields() != null && !farmDTO.getFields().isEmpty()) {
            for (FieldDTO fieldDTO : farmDTO.getFields()) {
                FieldDTO assignedField = fieldService.assignField(fieldDTO, savedFarm);
                savedFarm.getFields().add(fieldMapper.toEntity(assignedField));
            }
        }

        // Save the farm again to persist associations
        savedFarm = farmRepository.save(savedFarm);

        return farmMapper.toDTO(savedFarm);
    }



    @Override
    public List<FarmDTO> getFarmsWithFieldAreaLessThan4000() {
        List<Farm> farms = farmRepository.findFarmsWithFieldAreaLessThan4000();
        return farms.stream()
                .map(farm -> farmMapper.toDTO(farm))
                .collect(Collectors.toList());
    }


    @Override
    public FarmDTO updateFarm(UUID id, FarmDTO farmDTO) {
        if (farmDTO == null) {
            throw new NullFarmException("Farm cannot be null");
        }

        // Find the farm by ID
        Farm existingFarm = farmRepository.findById(id)
                .orElseThrow(() -> new FarmNotFoundException("Farm with ID " + id + " not found"));

        Farm farm = farmMapper.toEntity(farmDTO);

        // Update fields
        existingFarm.setName(farm.getName());
        existingFarm.setLocation(farm.getLocation());
        existingFarm.setArea(farm.getArea());

        // Save the updated farm
        Farm updatedFarm = farmRepository.save(existingFarm);

        // Convert to DTO
        return farmMapper.toDTO(updatedFarm);
    }

    @Override
    public FarmDTO getFarmById(UUID id) {

        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new FarmNotFoundException("Farm with ID " + id + " not found"));

        return farmMapper.toDTO(farm);
    }

    @Override
    public List<FarmDTO> getAllFarms() {
        List<Farm> farms = farmRepository.findAll();

        return farms.stream()
                .map(farmMapper::toDTO)
                .collect(Collectors.toList());
    }

}
