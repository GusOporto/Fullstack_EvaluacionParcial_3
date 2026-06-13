package com.RRHH.ColaboradoresSucursales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.RRHH.ColaboradoresSucursales.model.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    @Query("SELECT DISTINCT c FROM Colaborador c JOIN c.sucursales s WHERE s.id = :sucursalId")
    List<Colaborador> findBySucursales(@Param("sucursalId") Long sucursalId);

    @Query("SELECT DISTINCT c FROM Colaborador c JOIN c.sucursales s JOIN s.comuna co JOIN co.region r WHERE r.id = :regionId")
    List<Colaborador> findByRegion(@Param("regionId") Long regionId);

    @Query("SELECT DISTINCT c FROM Colaborador c JOIN c.sucursales s JOIN s.comuna co WHERE co.id = :comunaId")
    List<Colaborador> findByComuna(@Param("comunaId") Long comunaId);

    @Query("SELECT c FROM Colaborador c WHERE c.run = :run")
    List<Colaborador> findByRun(@Param("run") String run);
}
