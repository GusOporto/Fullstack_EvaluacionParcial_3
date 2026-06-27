package com.RRHH.ColaboradoresSucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.RRHH.ColaboradoresSucursales.DTO.RegionDTO;
import com.RRHH.ColaboradoresSucursales.model.Region;
import com.RRHH.ColaboradoresSucursales.repository.ComunaRepository;
import com.RRHH.ColaboradoresSucursales.repository.SucursalRepository;

@Service
public class RegionValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    public boolean validarNullVacio(Region region) {
        if (region.getNombre() == null || region.getNombre().trim().length() == 0) {
            return false;
        }
        return true;
    }

    public RegionDTO convertirADTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setNombre(region.getNombre());

        List<String> comunas = comunaRepository.findByRegionId(region.getId()).stream()
                .map(c -> "ID: " + c.getId() + " - " + c.getNombre())
                .toList();
        dto.setComunas(comunas);

        List<String> sucursales = sucursalRepository.findByComunaRegionId(region.getId()).stream()
                .map(s -> "ID: " + s.getId() + " - " + s.getNombre())
                .toList();
        dto.setSucursales(sucursales);

        return dto;
    }
}
