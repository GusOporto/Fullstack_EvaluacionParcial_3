package com.RRHH.ColaboradoresSucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
import com.RRHH.ColaboradoresSucursales.repository.ColaboradorRepository;

@Service
public class SucursalValidaciones {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public boolean validarNullVacio(Sucursal sucursal) {
        if (sucursal.getNombre() == null || sucursal.getNombre().isEmpty()) {
            return false;
        }
        if (sucursal.getDireccion() == null || sucursal.getDireccion().isEmpty()) {
            return false;
        }
        if (sucursal.getComuna() == null) {
            return false;
        }
        return true;
    }

    public SucursalDTO convertirADTO(Sucursal sucursal) {
        SucursalDTO dto = new SucursalDTO();
        dto.setId(sucursal.getId());
        dto.setNombre(sucursal.getNombre());
        dto.setDireccion(sucursal.getDireccion());

        if (sucursal.getComuna() != null) {
            dto.setComuna("ID: " + sucursal.getComuna().getId() + " - " + sucursal.getComuna().getNombre());
            if (sucursal.getComuna().getRegion() != null) {
                dto.setRegion("ID: " + sucursal.getComuna().getRegion().getId() + " - "
                        + sucursal.getComuna().getRegion().getNombre());
            }
        }

        List<String> colaboradores = colaboradorRepository.findDistinctBySucursalesId(sucursal.getId()).stream()
                .map(c -> "ID: " + c.getId() + " - " + c.getNombres() + " - " + c.getApellidos())
                .toList();

        dto.setColaboradores(colaboradores);
        return dto;
    }

}
