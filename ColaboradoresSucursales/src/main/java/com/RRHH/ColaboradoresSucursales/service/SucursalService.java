package com.RRHH.ColaboradoresSucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
import com.RRHH.ColaboradoresSucursales.repository.ColaboradorRepository;
import com.RRHH.ColaboradoresSucursales.repository.SucursalRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<SucursalDTO> findAll() {
        log.info("Buscando todas las sucursales...");
        return sucursalRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public SucursalDTO findById(Long id) {
        log.info("Buscando sucursal con ID: {}", id);
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada."));
        return convertirADTO(sucursal);
    }

    public SucursalDTO save(Sucursal sucursal) {
        log.info("Guardando nueva sucursal: {}", sucursal.getNombre());
        Sucursal guardado = sucursalRepository.save(sucursal);
        return convertirADTO(guardado);
    }

    public String delete(Long id) {
        log.info("Eliminando sucursal con ID: {}", id);
        try {
            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(
                            () -> new RuntimeException("No se pudo borrar la Sucursal."));
            sucursalRepository.delete(sucursal);
            return "La Sucursal ID: " + id + " ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public SucursalDTO updateSucursal(Long id, Sucursal sucursal1) {
        log.info("Actualizando sucursal ID: {}", id);
        Sucursal sucursal2 = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La Sucursal no existe."));
        if (sucursal1.getNombre() != null) {
            sucursal2.setNombre(sucursal1.getNombre());
        }
        if (sucursal1.getDireccion() != null) {
            sucursal2.setDireccion(sucursal1.getDireccion());
        }
        if (sucursal1.getComuna() != null) {
            sucursal2.setComuna(sucursal1.getComuna());
        }
        Sucursal guardado = sucursalRepository.save(sucursal2);
        return convertirADTO(guardado);
    }

    private SucursalDTO convertirADTO(Sucursal sucursal) {
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