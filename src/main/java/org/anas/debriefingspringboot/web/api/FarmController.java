package org.anas.debriefingspringboot.web.api;


import jakarta.validation.Valid;
import org.anas.debriefingspringboot.service.FarmService;
import org.anas.debriefingspringboot.service.dto.FarmDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/farms")
public class FarmController {

    private final FarmService farmServiceWithFields;
    private final FarmService farmServiceWithoutFields;

    public FarmController(
            @Qualifier("farmServiceWithFields") FarmService farmServiceWithFields,
            @Qualifier("farmServiceWithoutFields") FarmService farmServiceWithoutFields) {
        this.farmServiceWithFields = farmServiceWithFields;
        this.farmServiceWithoutFields = farmServiceWithoutFields;
    }

    @PostMapping
    public ResponseEntity<FarmDTO> createFarm(@Valid @RequestBody FarmDTO farmDTO) {
        FarmService chosenService;

        if (farmDTO.getFields() != null && !farmDTO.getFields().isEmpty()) {
            chosenService = farmServiceWithFields;
        } else {
            chosenService = farmServiceWithoutFields;
        }

        FarmDTO createdFarm = chosenService.createFarm(farmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFarm);
    }
}

