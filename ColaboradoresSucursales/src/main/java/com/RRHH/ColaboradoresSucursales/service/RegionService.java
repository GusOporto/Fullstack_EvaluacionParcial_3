package com.RRHH.ColaboradoresSucursales.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RRHH.ColaboradoresSucursales.DTO.RegionDTO;
import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.model.Region;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
import com.RRHH.ColaboradoresSucursales.repository.ComunaRepository;
import com.RRHH.ColaboradoresSucursales.repository.RegionRepository;
import com.RRHH.ColaboradoresSucursales.repository.SucursalRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<RegionDTO> findAll() {
        log.info("Buscando todas las regiones...");
        return regionRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public RegionDTO findById(Long id) {
        log.info("Buscando region por ID: {}", id);
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region no encontrada."));
        return convertirADTO(region);
    }

    public List<SucursalDTO> findSucursalesByRegionId(Long id) {
        log.info("Buscando sucursales por Region ID: {}", id);
        if (!regionRepository.existsById(id)) {
            throw new RuntimeException("Region no encontrada.");
        }

        List<Sucursal> sucursalesRegion = sucursalRepository.findByComunaRegionId(id);

        List<SucursalDTO> dtos = new ArrayList<>();
        for (Sucursal s : sucursalesRegion) {
            SucursalDTO dto = new SucursalDTO();
            dto.setId(s.getId());
            dto.setNombre(s.getNombre());
            dto.setDireccion(s.getDireccion());
            dto.setRegion("ID: " + id + " - " + s.getComuna().getRegion().getNombre());
            dto.setComuna("ID: " + s.getComuna().getId() + " - " + s.getComuna().getNombre());
            dtos.add(dto);
        }
        return dtos;
    }

    public RegionDTO save(Region region) {
        log.info("Guardando nueva region: {}", region.getNombre());
        Region guardado = regionRepository.save(region);
        return convertirADTO(guardado);
    }

    public String delete(Long id) {
        log.info("Eliminando region por ID: {}", id);
        try {
            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se pudo borrar la Region ID: " + id + ", no existe."));
            regionRepository.delete(region);
            return "La Region ID: " + id + " ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public RegionDTO updateRegion(Long id, Region region1) {
        log.info("Actualizando region con ID: {}", id);
        Region region2 = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La Region no existe."));
        if (region1.getNombre() != null) {
            region2.setNombre(region1.getNombre());
        }
        Region actualizado = regionRepository.save(region2);
        return convertirADTO(actualizado);
    }

    private RegionDTO convertirADTO(Region region) {
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