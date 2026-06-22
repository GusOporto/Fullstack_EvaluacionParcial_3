package com.datos.laborales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datos.laborales.DTO.EvaluacionesDTO;
import com.datos.laborales.model.Evaluaciones;
import com.datos.laborales.repository.EvaluacionesRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
//CLASE DE EVALUACIONES SERVICE
public class EvaluacionesService {

    
    //Logica Negocio
    @Autowired
    private EvaluacionesRepository evaluacionesRepository;
    
    private EvaluacionesDTO convertirADTO(Evaluaciones evaluaciones){
        EvaluacionesDTO dto = new EvaluacionesDTO();
        dto.setId(evaluaciones.getId());
        dto.setFechaEvaluacion(evaluaciones.getFechaEvaluacion());
        dto.setPeriodo(evaluaciones.getPeriodo());
        dto.setObservaciones(evaluaciones.getObservaciones());
        dto.setFortalezas(evaluaciones.getFortalezas());
        dto.setDebilidades(evaluaciones.getDebilidades());
        dto.setPorMejorar(evaluaciones.getPorMejorar());

        return dto;
    }

    public List<EvaluacionesDTO> findAll(){//Listar todo
        return evaluacionesRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public EvaluacionesDTO findById(Long id){//Buscar por id
        Evaluaciones evaluaciones = evaluacionesRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Evaluacion NO encontrada."));
        return convertirADTO(evaluaciones);
    }

    public Evaluaciones save(Evaluaciones evaluaciones){
        return evaluacionesRepository.save(evaluaciones);
    }

    public String delete(Long id){
        try{
            Evaluaciones evaluaciones = evaluacionesRepository.findById( id)
            .orElseThrow(()->new RuntimeException("No se pudo borrar la Evaluacion ID: "+id+" no existente."));
            evaluacionesRepository.delete(evaluaciones);
            return "La evaluacion ID: "+id+" ha sido Eliminada con Exito.";
        }catch(RuntimeException e){
            return e.getMessage();
        }
    }

    public Evaluaciones updateEvaluaciones(Long id, Evaluaciones evalu1){
        Evaluaciones evalu2 = evaluacionesRepository.findById(id)
        .orElseThrow(()->new RuntimeException ("La evaluacion no existe."));
        if(evalu1.getFechaEvaluacion()!= null){
            evalu2.setFechaEvaluacion(evalu1.getFechaEvaluacion());
        }
        if(evalu1.getPeriodo()!= null){
            evalu2.setPeriodo(evalu1.getPeriodo());
        }
        if(evalu1.getObservaciones()!= null){
            evalu2.setObservaciones(evalu1.getObservaciones());
        }
        if(evalu1.getFortalezas()!= null){
            evalu2.setFortalezas(evalu1.getFortalezas());
        }
        if(evalu1.getDebilidades()!= null){
            evalu2.setDebilidades(evalu1.getDebilidades());
        }
        if(evalu1.getPorMejorar()!= null){
            evalu2.setPorMejorar(evalu1.getPorMejorar());
        }
        
        return evaluacionesRepository.save(evalu2);
    }
}
