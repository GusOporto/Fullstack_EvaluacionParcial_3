package com.datos.laborales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datos.laborales.model.Cargos;

@Repository
//CLASE DE CARGOS REPOSITORY
public interface CargosRepository extends JpaRepository<Cargos, Long>{

}
