package com.rrhh.rrhh.service;

import org.springframework.stereotype.Service;

import com.rrhh.rrhh.DTO.AreaDTO;
import com.rrhh.rrhh.model.Area;

@Service
public class AreaValidaciones {

    public boolean validarNullVacio(Area area){
        if (area.getNombre() == null || area.getNombre().trim().length() == 0){
            return false;
        }
        return true;
    }

    public AreaDTO convertirADTO(Area area) {
        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setId(area.getId());
        areaDTO.setNombre(area.getNombre());
        areaDTO.setDescripcion(area.getDescripcion());
        return areaDTO;
    }



}
