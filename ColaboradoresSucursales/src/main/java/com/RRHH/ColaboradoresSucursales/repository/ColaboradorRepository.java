package com.RRHH.ColaboradoresSucursales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.RRHH.ColaboradoresSucursales.model.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    List<Colaborador> findDistinctBySucursalesId(Long sucursalId);

    List<Colaborador> findDistinctBySucursalesComunaRegionId(Long regionId);

    List<Colaborador> findDistinctBySucursalesComunaId(Long comunaId);

    List<Colaborador> findByRun(String run);

    Cargos findByCargoId(Long cargoId);
}
