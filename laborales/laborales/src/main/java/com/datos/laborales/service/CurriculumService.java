package com.datos.laborales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datos.laborales.DTO.CurriculumDTO;
import com.datos.laborales.model.Curriculum;
import com.datos.laborales.repository.CurriculumRepository;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
//CLASE DE CURRICULUM SERVICE
public class CurriculumService {

    //Logica Negocio
    @Autowired
    private CurriculumRepository curriculumRepository;

    private CurriculumDTO convertirADTO(Curriculum curriculum){
        CurriculumDTO dto = new CurriculumDTO();
        dto.setId(curriculum.getId());
        dto.setNombre(curriculum.getNombre());
        dto.setEdad(curriculum.getEdad());
        dto.setExperienciaLaboral(curriculum.getExperienciaLaboral());
        dto.setCertificaciones(curriculum.getCertificaciones());
        dto.setHabilidades(curriculum.getHabilidades());
        dto.setFortalezas(curriculum.getFortalezas());
        dto.setIdiomas(curriculum.getIdiomas());

        return dto;
    }

    public List<CurriculumDTO> findAll(){ //Listar todo
        return curriculumRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public CurriculumDTO findById(Long id){//Buscar por id
        Curriculum curriculum = curriculumRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Curriculum NO encontrado."));
        return convertirADTO(curriculum);
    }

    public Curriculum save(Curriculum curriculum){
        return curriculumRepository.save(curriculum);
    }

    public String delete(Long id){
        try{
            Curriculum curriculum = curriculumRepository.findById( id)
            .orElseThrow(()->new RuntimeException("No se pudo borrar el Curriculum ID: "+id+" no existente."));
            curriculumRepository.delete(curriculum);
            return "El Curriculum ID: "+id+" ha sido Eliminado con Exito.";
        }catch(RuntimeException e){
            return e.getMessage();
        }
    }

    public Curriculum updateCurriculum(Long id, Curriculum curr1){
        Curriculum curr2 = curriculumRepository.findById(id)
        .orElseThrow(()->new RuntimeException ("el Curriculum no existe."));
        if(curr1.getNombre()!= null){
            curr2.setNombre(curr1.getNombre());
        }
        if(curr1.getExperienciaLaboral()!= null){
            curr2.setExperienciaLaboral(curr1.getExperienciaLaboral());
        }
        if(curr1.getCertificaciones()!= null){
            curr2.setCertificaciones(curr1.getCertificaciones());
        }
        if(curr1.getHabilidades()!= null){
            curr2.setHabilidades(curr1.getHabilidades());
        }
        if(curr1.getFortalezas()!=null){
            curr2.setFortalezas(curr1.getFortalezas());
        }
        if(curr1.getIdiomas()!=null){
            curr2.setIdiomas(curr1.getIdiomas());
        }

        return curriculumRepository.save(curr2);
    }
}
