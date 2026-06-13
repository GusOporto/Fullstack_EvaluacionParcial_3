package com.RRHH.ColaboradoresSucursales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RRHH.ColaboradoresSucursales.model.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    List<Sucursal> findByComunaRegionId(Long regionId);
    List<Sucursal> findByComunaId(Long comunaId);

}
