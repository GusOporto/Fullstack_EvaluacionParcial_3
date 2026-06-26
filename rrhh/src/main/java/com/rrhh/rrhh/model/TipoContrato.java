package com.rrhh.rrhh.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipos_Contrato")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de los tipos de contrato registrados en el sistema RRHH")
public class TipoContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del tipo de contrato", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del contrato es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre del tipo de contrato", example = "Contrato indefinido")
    private String nombre;

    @Size(max = 300, message = "La descripción no puede superar los 300 caracteres")
    @Schema(description = "Descripción general del tipo de contrato", example = "Contrato laboral sin fecha de término definida")
    private String descripcion;

    @NotBlank(message = "La modalidad es obligatoria")
    @Column(nullable = false, length = 50)
    @Schema(description = "Modalidad del contrato", example = "Presencial")
    private String modalidad;

    @NotBlank(message = "La jornada es obligatoria")
    @Column(nullable = false, length = 50)
    @Schema(description = "Tipo de jornada laboral asociada al contrato", example = "Completa")
    private String jornada;

    @NotNull(message = "La fecha de creación es obligatoria")
    @Column(nullable = false)
    @Schema(description = "Fecha en que se registra el tipo de contrato", example = "2026-06-04")
    private LocalDate fechaCreacion;

}
