package com.datos.laborales.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "id", "nombre", "sueldo", "descripcion"})
public class CargosDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer sueldo;

}
