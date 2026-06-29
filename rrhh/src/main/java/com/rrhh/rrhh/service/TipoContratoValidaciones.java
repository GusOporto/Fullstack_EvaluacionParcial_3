package com.rrhh.rrhh.service;

import org.springframework.stereotype.Service;

import com.rrhh.rrhh.DTO.TipoContratoDTO;
import com.rrhh.rrhh.model.TipoContrato;

@Service
public class TipoContratoValidaciones {

    public boolean validarNullVacio(TipoContrato tipocontrato) {
        if (tipocontrato.getNombre() == null || tipocontrato.getNombre().trim().length() == 0) {
            return false;
        }
        if (tipocontrato.getDescripcion() == null || tipocontrato.getDescripcion().trim().length() == 0) {
            return false;
        }
        if (tipocontrato.getModalidad() == null || tipocontrato.getModalidad().trim().length() == 0) {
            return false;
        }
        if (tipocontrato.getJornada() == null || tipocontrato.getJornada().trim().length() == 0) {
            return false;
        }
        if (tipocontrato.getFechaCreacion() == null) {
            return false;
        }

        return true;
    }

    public TipoContratoDTO convertirADTO(TipoContrato tipocontrato) {
        TipoContratoDTO tipocontratoDTO = new TipoContratoDTO();
        tipocontratoDTO.setId(tipocontrato.getId());
        tipocontratoDTO.setNombre(tipocontrato.getNombre());
        tipocontratoDTO.setDescripcion(tipocontrato.getDescripcion());
        tipocontratoDTO.setModalidad(tipocontrato.getModalidad());
        tipocontratoDTO.setJornada(tipocontrato.getJornada());
        tipocontratoDTO.setFechaCreacion(tipocontrato.getFechaCreacion());
        return tipocontratoDTO;
    }

}
