package com.datos.laborales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datos.laborales.model.Curriculum;

@Repository
//CLASE DE CURRICULUM REPOSITORY
public interface CurriculumRepository extends JpaRepository<Curriculum,Long>{

}