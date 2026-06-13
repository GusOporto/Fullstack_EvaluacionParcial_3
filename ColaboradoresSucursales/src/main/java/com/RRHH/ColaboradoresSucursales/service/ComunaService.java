package com.RRHH.ColaboradoresSucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RRHH.ColaboradoresSucursales.DTO.ComunaDTO;
import com.RRHH.ColaboradoresSucursales.model.Comuna;
import com.RRHH.ColaboradoresSucursales.repository.ColaboradorRepository;
import com.RRHH.ColaboradoresSucursales.repository.ComunaRepository;
import com.RRHH.ColaboradoresSucursales.repository.SucursalRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<ComunaDTO> findAll() {
        log.info("Buscando todas las comunas...");
        return comunaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ComunaDTO findById(Long id) {
        log.info("Buscando la comuna con ID: {}", id);
        Comuna comuna = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada."));
        return convertirADTO(comuna);
    }

    public ComunaDTO save(Comuna comuna) {
        log.info("Guardando nueva comuna: {}", comuna.getNombre());
        Comuna guardado = comunaRepository.save(comuna);
        return convertirADTO(guardado);
    }

    public String delete(Long id) {
        log.info("Eliminando comuna con ID: {}", id);
        try {
            Comuna comuna = comunaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se pudo borrar la Comuna."));
            comunaRepository.delete(comuna);
            return "La Comuna ID: " + id + " ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public ComunaDTO updateComuna(Long id, Comuna comuna1) {
        log.info("Actualizando comuna con ID: {}", id);
        Comuna comuna2 = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La Comuna no existe."));
        if (comuna1.getNombre() != null) {
            comuna2.setNombre(comuna1.getNombre());
        }
        if (comuna1.getRegion() != null) {
            comuna2.setRegion(comuna1.getRegion());
        }
        Comuna actualizado = comunaRepository.save(comuna2);
        return convertirADTO(actualizado);
    }

    private ComunaDTO convertirADTO(Comuna comuna) {
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
