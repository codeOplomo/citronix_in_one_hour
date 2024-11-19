package org.anas.debriefingspringboot.repository;

import org.anas.debriefingspringboot.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {
}
