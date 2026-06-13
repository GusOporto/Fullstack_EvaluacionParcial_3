package com.RRHH.ColaboradoresSucursales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RRHH.ColaboradoresSucursales.model.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {
    List<Comuna> findByRegionId(Long regionId);

}
