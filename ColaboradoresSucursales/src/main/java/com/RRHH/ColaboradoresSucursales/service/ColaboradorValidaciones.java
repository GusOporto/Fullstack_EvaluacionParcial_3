package com.RRHH.ColaboradoresSucursales.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.RRHH.ColaboradoresSucursales.DTO.ColaboradorDTO;
import com.RRHH.ColaboradoresSucursales.model.Colaborador;

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

    public CargoExternoDTO obtenerCargo(Long id) {
        CargoExternoDTO cargoRecuperado = new CargoExternoDTO();
        try {
            CargoExternoDTO resultado = webClientBuilder.build()
                    .get()
                    .uri("http://laborales/api/v1/cargos/buscar-por-colaborador" + id)
                    .retrieve()
                    .bodyToMono(CargoExternoDTO.class)
                    .block();

            if (resultado != null) {
                return resultado;
            }
            cargoRecuperado.setId(0);
            cargoRecuperado.setNombreCargo("Sin Cargo asignado");
            return cargoRecuperado;

        } catch (Exception e) {
            cargoRecuperado.setId(0);
            cargoRecuperado.setNombreCargo("No se pudo conectar con el Colaborador");
            return cargoRecuperado;
        }
    }

    public ColaboradorDTO convertirADTO(Colaborador colaborador) {
        ColaboradorDTO dto = new ColaboradorDTO();
        dto.setId(colaborador.getId());
        dto.setRun(colaborador.getRun());
        dto.setNombres(colaborador.getNombres());
        dto.setApellidos(colaborador.getApellidos());
        dto.setFechaNacimiento(colaborador.getFechaNacimiento());
        dto.setTelefono(colaborador.getTelefono());
        dto.setCorreo(colaborador.getCorreo());
        dto.setDireccion(colaborador.getDireccion());
        dto.setCargoId(obtenerCargo(colaborador.getId()));
        return dto;
    }

}
