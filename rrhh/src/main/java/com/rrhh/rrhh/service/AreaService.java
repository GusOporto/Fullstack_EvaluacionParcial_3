package com.rrhh.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrhh.rrhh.DTO.AreaDTO;
import com.rrhh.rrhh.model.Area;
import com.rrhh.rrhh.repository.AreaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    public List<AreaDTO> obtenerAreas() {
        return areaRepository.findAll().stream().map(this::convertirADTO).toList();

    }

    public AreaDTO buscarPorId(Long id) {
        Area area = areaRepository.findById(id).orElseThrow(() -> new RuntimeException("¡Área no encontrada!"));
        return convertirADTO(area);
    }

    public String eliminar(Long id) {
        try {
            Area area = areaRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("¡Imposible eliminar! El área con ID " + id + " no existe."));
            areaRepository.delete(area);
            return "El área '" + area.getNombre() + "' ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public AreaDTO guardarArea(Area area) {
        Area guardada = areaRepository.save(area);
        return convertirADTO(guardada);
    }

    public AreaDTO actualizarArea(Long id, Area area) {
        Area areaExistente = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡El área no existe en los registros!"));
        if (area.getNombre() != null) {
            areaExistente.setNombre(area.getNombre());
        }
        if (area.getDescripcion() != null) {
            areaExistente.setDescripcion(area.getDescripcion());
        }
        Area actualizada = areaRepository.save(areaExistente);
        return convertirADTO(actualizada);
    }

    private AreaDTO convertirADTO(Area area) {
        AreaDTO dto = new AreaDTO();
        dto.setId(area.getId());
        dto.setNombre(area.getNombre());
        dto.setDescripcion(area.getDescripcion());
        return dto;
    }

}
