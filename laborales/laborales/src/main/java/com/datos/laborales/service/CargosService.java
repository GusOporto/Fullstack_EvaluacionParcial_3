package com.datos.laborales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datos.laborales.DTO.CargosDTO;
import com.datos.laborales.model.Cargos;
import com.datos.laborales.repository.CargosRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
//CLASE DE CARGOS SERVICE
public class CargosService {

    //logica Negocio
    @Autowired
    private CargosRepository cargosRepository;

    private CargosDTO convertirADTO(Cargos cargo){
        CargosDTO dto = new CargosDTO();
        dto.setId(cargo.getId());
        dto.setNombre(cargo.getNombre());
        dto.setDescripcion(cargo.getDescripcion());
        dto.setSueldo(cargo.getSueldo());

        return dto;
    }

    public List<CargosDTO> findAll(){//Listar todo
        return cargosRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }
    
    public CargosDTO findById(Long id){//Buscar por id
        Cargos cargos = cargosRepository.findById(id)
            .orElseThrow(()-> new RuntimeException("Cargo NO encontrado."));
            return convertirADTO(cargos);
    }

    public Cargos save(Cargos cargos){
        return cargosRepository.save(cargos);
    }

    public String delete(Long id){
        try{
            Cargos cargos = cargosRepository.findById(id)
            .orElseThrow(()->new RuntimeException("no se pudo borrar el Cargo ID: "+id+", no exsitente."));
            cargosRepository.delete(cargos);
            return "El Cargo ID: "+id+" ha sido Eliminado con Exito";
        }catch (RuntimeException e){
            return e.getMessage();
        }
    }

    public Cargos updateCargos(Long id, Cargos carg1){
        Cargos carg2 = cargosRepository.findById(id)
        .orElseThrow(()->new RuntimeException ("El Cargo no existe."));
        if(carg1.getNombre()!= null){
            carg2.setNombre(carg1.getNombre());
        }
        if(carg1.getDescripcion()!= null){
            carg2.setDescripcion(carg1.getDescripcion());
        }
        if(carg1.getSueldo()!= null){
            carg2.setSueldo(carg1.getSueldo());
        }
        return cargosRepository.save(carg2);
    }

}
