package com.RRHH.ColaboradoresSucursales.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.RRHH.ColaboradoresSucursales.DTO.ColaboradorDTO;
import com.RRHH.ColaboradoresSucursales.model.Colaborador;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;

@Service
public class ColaboradorValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public boolean validarNullVacio(Colaborador colaborador) {
        if (colaborador.getRun() == null || colaborador.getRun().isEmpty()) {
            return false;
        }
        if (colaborador.getNombres() == null || colaborador.getNombres().isEmpty()) {
            return false;
        }
        if (colaborador.getApellidos() == null || colaborador.getApellidos().isEmpty()) {
            return false;
        }
        if (colaborador.getFechaNacimiento() == null) {
            return false;
        }
        if (colaborador.getTelefono() == null || colaborador.getTelefono().isEmpty()) {
            return false;
        }
        if (colaborador.getCorreo() == null || colaborador.getCorreo().isEmpty()) {
            return false;
        }
        if (colaborador.getDireccion() == null || colaborador.getDireccion().isEmpty()) {
            return false;
        }
        if (colaborador.getSucursales() == null || colaborador.getSucursales().isEmpty()) {
            return false;
        }
        return true;
    }

    public ColaboradorDTO convertirADTO(Colaborador colaborador) {
        if (colaborador == null)
            return null;

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

        // dto.setCurriculumId(colaborador.getCurriculumId());
        // dto.setTituloId(colaborador.getTitulosId());

        return dto;
    }

}
