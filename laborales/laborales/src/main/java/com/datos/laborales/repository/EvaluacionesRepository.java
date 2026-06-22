package com.datos.laborales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datos.laborales.model.Evaluaciones;

@Repository
//CLASE DE EVALUACIONES REPOSITORY
public interface EvaluacionesRepository extends JpaRepository<Evaluaciones,Long>{

}
