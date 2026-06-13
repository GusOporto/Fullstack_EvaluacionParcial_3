package com.RRHH.ColaboradoresSucursales.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "id", "nombre", "region", "sucursales", "colaboradores" })
public class ComunaDTO {
    private Long id;
    private String nombre;

    // Relaciones Internas
    private String region;
    private List<String> sucursales;
    private List<String> colaboradores;

}
