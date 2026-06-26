package com.rrhh.rrhh.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "id", "nombre", "descripcion", "modalidad", "jornada", "fechaCreacion", "colaboradores" })
public class TipoContratoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String modalidad;
    private String jornada;
    private LocalDate fechaCreacion;

}
