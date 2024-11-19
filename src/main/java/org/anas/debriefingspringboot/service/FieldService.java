package org.anas.debriefingspringboot.service;

import org.anas.debriefingspringboot.domain.Farm;
import org.anas.debriefingspringboot.service.dto.FieldDTO;

public interface FieldService {

    FieldDTO assignField(FieldDTO fieldDTO, Farm farm);
}
