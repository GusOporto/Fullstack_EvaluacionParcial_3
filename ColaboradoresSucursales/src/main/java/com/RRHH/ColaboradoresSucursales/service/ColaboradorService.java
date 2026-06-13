package com.RRHH.ColaboradoresSucursales.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RRHH.ColaboradoresSucursales.DTO.ColaboradorDTO;
import com.RRHH.ColaboradoresSucursales.model.Colaborador;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
import com.RRHH.ColaboradoresSucursales.repository.ColaboradorRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<ColaboradorDTO> findAll() {
        log.info("Buscando todos los colaboradores...");
        return colaboradorRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ColaboradorDTO findById(Long id) {
        log.info("Buscando colaborador con ID: {}", id);
        Colaborador colaborador = colaboradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colaborador no encontrado."));
        return convertirADTO(colaborador);
    }

    public ColaboradorDTO save(Colaborador colaborador) {
        log.info("Guardando colaborador...");
        Colaborador guardado = colaboradorRepository.save(colaborador);
        return convertirADTO(guardado);
    }

    public String delete(Long id) {
        log.info("Eliminando colaborador con ID: {}", id);
        try {
            Colaborador colaborador = colaboradorRepository.findById(id)
                    .orElseThrow(
                            () -> new RuntimeException("No se pudo borrar el Colaborador."));
            colaboradorRepository.delete(colaborador);
            return "El Colaborador ID: " + id + " ha sido eliminado exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public ColaboradorDTO updateColaborador(Long id, Colaborador col1) {
        log.info("Actualizando datos del colaborador con ID: {}", id);
        Colaborador col2 = colaboradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El Colaborador no existe."));
        if (col1.getRun() != null)
            col2.setRun(col1.getRun());
        if (col1.getNombres() != null)
            col2.setNombres(col1.getNombres());
        if (col1.getApellidos() != null)
            col2.setApellidos(col1.getApellidos());
        if (col1.getFechaNacimiento() != null)
            col2.setFechaNacimiento(col1.getFechaNacimiento());
        if (col1.getTelefono() != null)
            col2.setTelefono(col1.getTelefono());
        if (col1.getCorreo() != null)
            col2.setCorreo(col1.getCorreo());
        if (col1.getDireccion() != null)
            col2.setDireccion(col1.getDireccion());
        if (col1.getSucursales() != null)
            col2.setSucursales(col1.getSucursales());

        Colaborador actualizado = colaboradorRepository.save(col2);
        return convertirADTO(actualizado);
    }

    public List<ColaboradorDTO> findByRun(String run) {
        log.info("Buscando colaborador RUN: {}", run);
        return colaboradorRepository.findByRun(run).stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<ColaboradorDTO> findBySucursales(Long id) {
        log.info("Buscando colaboradores asignados a la sucursal ID: {}", id);
        return colaboradorRepository.findBySucursales(id).stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<ColaboradorDTO> findByComuna(Long id) {
        log.info("Buscando colaboradores asignados a la comuna ID: {}", id);
        return colaboradorRepository.findByComuna(id).stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<ColaboradorDTO> findByRegion(Long id) {
        log.info("Buscando colaboradores asignados a la region ID: {}", id);
        return colaboradorRepository.findByRegion(id).stream()
                .map(this::convertirADTO)
                .toList();
    }

    private ColaboradorDTO convertirADTO(Colaborador colaborador) {
        ColaboradorDTO dto = new ColaboradorDTO();
        dto.setId(colaborador.getId());
        dto.setRun(colaborador.getRun());
        dto.setNombres(colaborador.getNombres());
        dto.setApellidos(colaborador.getApellidos());
        dto.setFechaNacimiento(colaborador.getFechaNacimiento());
        dto.setTelefono(colaborador.getTelefono());
        dto.setCorreo(colaborador.getCorreo());
        dto.setDireccion(colaborador.getDireccion());

        List<String> sucursales = new ArrayList<>();
        if (colaborador.getSucursales() != null) {
            for (Sucursal s : colaborador.getSucursales()) {
                sucursales.add("ID: " + s.getId() + " - " + s.getNombre());
            }
        }
        dto.setSucursales(sucursales);

        // dto.setCargoId(colaborador.getCargoId());
        // dto.setCurriculumId(colaborador.getCurriculumId());
        // dto.setTituloId(colaborador.getTitulosId());

        return dto;
    }
}
