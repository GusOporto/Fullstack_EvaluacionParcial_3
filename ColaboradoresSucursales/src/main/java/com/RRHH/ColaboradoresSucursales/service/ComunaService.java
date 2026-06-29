package com.RRHH.ColaboradoresSucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RRHH.ColaboradoresSucursales.DTO.ComunaDTO;
import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.model.Comuna;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
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
    private ComunaValidaciones comunaValidaciones;

    public List<ComunaDTO> findAll() {
        log.info("Buscando todas las comunas...");
        return comunaRepository.findAll().stream()
                .map(comunaValidaciones::convertirADTO)
                .toList();
    }

    public ComunaDTO findById(Long id) {
        log.info("Buscando la comuna con ID: {}", id);
        Comuna comuna = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada."));
        return comunaValidaciones.convertirADTO(comuna);
    }

    public ComunaDTO save(Comuna comuna) {
        log.info("Guardando nueva comuna: {}", comuna.getNombre());
        Comuna guardado = comunaRepository.save(comuna);
        return comunaValidaciones.convertirADTO(guardado);
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
        return comunaValidaciones.convertirADTO(actualizado);
    }

    public List<SucursalDTO> findSucursalesByComunaId(Long id) {
        log.info("Buscando sucursales por Comuna ID: {}", id);
        if (!comunaRepository.existsById(id)) {
            throw new RuntimeException("Comuna no encontrada.");
        }

        List<Sucursal> sucursalesComuna = sucursalRepository.findByComunaId(id);

        List<SucursalDTO> dtos = new java.util.ArrayList<>();
        for (Sucursal s : sucursalesComuna) {
            SucursalDTO dto = new SucursalDTO();
            dto.setId(s.getId());
            dto.setNombre(s.getNombre());
            dto.setDireccion(s.getDireccion());
            dto.setRegion("ID: " + s.getComuna().getRegion().getId() + " - " + s.getComuna().getRegion().getNombre());
            dto.setComuna("ID: " + s.getComuna().getId() + " - " + s.getComuna().getNombre());
            dtos.add(dto);
        }
        return dtos;
    }

}
