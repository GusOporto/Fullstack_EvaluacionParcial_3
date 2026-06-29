package com.rrhh.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrhh.rrhh.DTO.TipoContratoDTO;
import com.rrhh.rrhh.model.TipoContrato;
import com.rrhh.rrhh.repository.TipoContratoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoContratoService {

    @Autowired
    private TipoContratoRepository tipoContratoRepository;

    @Autowired
    private TipoContratoValidaciones tipoContratoValidaciones;

    public List<TipoContratoDTO> obtenerTipoContrato() {
        return tipoContratoRepository.findAll().stream()
                .map(tipoContratoValidaciones::convertirADTO)
                .toList();
    }

    public TipoContratoDTO buscarPorId(Long id) {
        TipoContrato contrato = tipoContratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de contrato no encontrado!"));
        return tipoContratoValidaciones.convertirADTO(contrato);
    }

    public String eliminar(Long id) {
        try {
            TipoContrato tipoContrato = tipoContratoRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("¡Imposible eliminar! El tipo de contrato con ID" + id + "no existe."));
            tipoContratoRepository.delete(tipoContrato);
            return "El tipo de contrato'" + tipoContrato.getNombre() + " ha sido eliminado exitosamente,";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public TipoContratoDTO guardarTipoContrato(TipoContrato tipoContrato) {
        TipoContrato guardado = tipoContratoRepository.save(tipoContrato);
        return tipoContratoValidaciones.convertirADTO(guardado);
    }

    public TipoContratoDTO actualizarTipoContrato(Long id, TipoContrato tipoContrato) {
        TipoContrato contratoExistente = tipoContratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El tipo de contrato no existe en los registros"));
        if (tipoContrato.getNombre() != null)
            contratoExistente.setNombre(tipoContrato.getNombre());
        if (tipoContrato.getDescripcion() != null)
            contratoExistente.setDescripcion(tipoContrato.getDescripcion());
        if (tipoContrato.getModalidad() != null)
            contratoExistente.setModalidad(tipoContrato.getModalidad());
        if (tipoContrato.getJornada() != null)
            contratoExistente.setJornada(tipoContrato.getJornada());
        if (tipoContrato.getFechaCreacion() != null)
            contratoExistente.setFechaCreacion(tipoContrato.getFechaCreacion());

        TipoContrato actualizado = tipoContratoRepository.save(contratoExistente);
        return tipoContratoValidaciones.convertirADTO(actualizado);
    }

}
