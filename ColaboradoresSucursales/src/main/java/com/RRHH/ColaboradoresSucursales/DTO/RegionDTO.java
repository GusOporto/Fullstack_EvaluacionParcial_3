package com.RRHH.ColaboradoresSucursales.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "id", "nombre", "comunas", "sucursales" })
public class RegionDTO {
    private Long id;
    private String nombre;

    // Relaciones Internas
    private List<String> comunas;
    private List<String> sucursales;
}