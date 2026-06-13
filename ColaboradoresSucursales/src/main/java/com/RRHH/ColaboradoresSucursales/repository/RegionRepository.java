package com.RRHH.ColaboradoresSucursales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RRHH.ColaboradoresSucursales.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

}
