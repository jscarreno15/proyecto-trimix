package com.trimix.backendpersonas.repository;

import com.trimix.backendpersonas.entity.PersonaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {
    Page<PersonaEntity> findAll(Specification<PersonaEntity> specification, Pageable pageable);
}
