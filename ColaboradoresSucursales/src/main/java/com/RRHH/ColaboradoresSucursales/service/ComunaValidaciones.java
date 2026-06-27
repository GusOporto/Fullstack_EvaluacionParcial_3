package com.RRHH.ColaboradoresSucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RRHH.ColaboradoresSucursales.DTO.ComunaDTO;
import com.RRHH.ColaboradoresSucursales.model.Comuna;
import com.RRHH.ColaboradoresSucursales.repository.ColaboradorRepository;
import com.RRHH.ColaboradoresSucursales.repository.SucursalRepository;

@Service
public class ComunaValidaciones {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public ComunaDTO convertirADTO(Comuna comuna) {
        ComunaDTO dto = new ComunaDTO();
        dto.setId(comuna.getId());
        dto.setNombre(comuna.getNombre());

        if (comuna.getRegion() != null) {
            dto.setRegion("ID: " + comuna.getRegion().getId() + " - " + comuna.getRegion().getNombre());
        }

        List<String> sucursales = sucursalRepository.findByComunaId(comuna.getId()).stream()
                .map(s -> "ID: " + s.getId() + " - " + s.getNombre())
                .toList();
        dto.setSucursales(sucursales);

        List<String> colaboradores = colaboradorRepository.findDistinctBySucursalesComunaId(comuna.getId()).stream()
                .map(c -> "RUN: " + c.getRun() + " - " + c.getNombres() + " " + c.getApellidos())
                .toList();
        dto.setColaboradores(colaboradores);

        return dto;
    }

}
