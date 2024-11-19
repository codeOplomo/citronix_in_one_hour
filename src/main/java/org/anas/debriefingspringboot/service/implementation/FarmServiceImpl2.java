package org.anas.debriefingspringboot.service.implementation;

import org.anas.debriefingspringboot.domain.Farm;
import org.anas.debriefingspringboot.exceptions.NullFarmException;
import org.anas.debriefingspringboot.repository.FarmRepository;
import org.anas.debriefingspringboot.service.FarmService;
import org.anas.debriefingspringboot.service.dto.FarmDTO;
import org.anas.debriefingspringboot.service.dto.mapper.FarmMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("farmServiceWithoutFields")
public abstract class FarmServiceImpl2 implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    public FarmServiceImpl2(FarmRepository farmRepository, FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.farmMapper = farmMapper;
    }

    @Override
    public FarmDTO createFarm(FarmDTO farmDTO) {
        // Case when fields list is empty
        if (farmDTO == null) {
            throw new NullFarmException("Farm cannot be null");
        }

        Farm farm = farmMapper.toEntity(farmDTO);
        farm.setCreationDate(java.time.LocalDate.now());

        Farm savedFarm = farmRepository.save(farm);
        return farmMapper.toDTO(savedFarm);
    }
}
