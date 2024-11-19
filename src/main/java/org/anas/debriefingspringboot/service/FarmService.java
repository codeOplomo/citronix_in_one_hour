package org.anas.debriefingspringboot.service;

import org.anas.debriefingspringboot.service.dto.FarmDTO;

import java.util.List;
import java.util.UUID;

public interface FarmService {

    FarmDTO createFarm(FarmDTO farmDTO);

    FarmDTO updateFarm(UUID id, FarmDTO farmDTO);

    FarmDTO getFarmById(UUID id);

    List<FarmDTO> getAllFarms();

    List<FarmDTO> getFarmsWithFieldAreaLessThan4000();

}
