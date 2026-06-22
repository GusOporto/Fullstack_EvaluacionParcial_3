package com.datos.laborales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datos.laborales.DTO.TituloDTO;
import com.datos.laborales.model.Titulo;
import com.datos.laborales.repository.TituloRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
//CLASE DE TITULO SERVICE
public class TituloService {

    //Logica Negocio
    @Autowired
    private TituloRepository tituloRepository;

    private TituloDTO convertirADTO(Titulo titulo){
        TituloDTO dto = new TituloDTO();
        dto.setId(titulo.getId());
        dto.setNombre(titulo.getNombre());
        dto.setInstitucion(titulo.getInstitucion());
        dto.setFechaObtencion(titulo.getFechaObtencion());

        return dto;
    }

    public List<TituloDTO> findAll(){//Listar todo
        return tituloRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public TituloDTO findById(Long id){//Buscar por id
        Titulo titulo = tituloRepository.findById(id)
            .orElseThrow(()-> new RuntimeException("Titulo NO encontrado."));
            return convertirADTO(titulo);
    }

    public Titulo save(Titulo titulo){
        return tituloRepository.save(titulo);
    }

    public String delete(Long id){
        try{
            Titulo titulo = tituloRepository.findById(id)
            .orElseThrow(()->new RuntimeException("no se pudo borrar el Titulo ID: "+id+", no exsitente."));
            tituloRepository.delete(titulo);
            return "El Titulo ID: "+id+" ha sido Eliminado con Exito";
        }catch (RuntimeException e){
            return e.getMessage();
        }
    }

        public Titulo updateTitulo(Long id, Titulo titul1){
        Titulo titul2 = tituloRepository.findById(id)
        .orElseThrow(()->new RuntimeException ("El Titulo no existe."));
        if(titul1.getNombre()!= null){
            titul2.setNombre(titul1.getNombre());
        }
        if(titul1.getInstitucion()!= null){
            titul2.setInstitucion(titul1.getInstitucion());
        }
        if(titul1.getFechaObtencion()!= null){
            titul2.setFechaObtencion(titul1.getFechaObtencion());
        }
        return tituloRepository.save(titul2);
    }

}
