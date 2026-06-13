package com.RRHH.ColaboradoresSucursales.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

import lombok.Data;

@Data
@JsonPropertyOrder({ "id", "run", "nombres", "apellidos", "fechaNacimiento", "telefono", "correo", "direccion",
        "cargoId", "curriculumId", "titulosId" })
public class ColaboradorDTO {
    private Long id;
    private String run;
    private String nombres;
    private String apellidos;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    private String telefono;
    private String correo;
    private String direccion;

    // Relaciones Internas
    private List<String> sucursales;

    // Relaciones Externas
    // private Integer cargoId;
    // private Integer curriculumId;
    // private List<Integer> titulosId;
}
