package com.datos.laborales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datos.laborales.model.Titulo;

@Repository
//CLASE DE TITULO REPOSITORY
public interface TituloRepository extends JpaRepository<Titulo,Long>{

}
