package com.rrhh.rrhh.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "areas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de las áreas registradas en el sistema RRHH")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del área", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del área es obligatorio")
    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre del área de la organización", example = "Recursos Humanos")
    private String nombre;

    @Size(max = 300, message = "La descripción no puede superar los 300 carecteres")
    @Schema(description = "Descripción general del área", example = "Area responsable de la gestión  del personal ")
    private String descripcion;

}
