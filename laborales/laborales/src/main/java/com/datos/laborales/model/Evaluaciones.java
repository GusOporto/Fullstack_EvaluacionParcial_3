package com.datos.laborales.model;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;//BORAR CREO
import jakarta.persistence.ManyToOne;//BORRAR CREO
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "evaluacion")
// CLASE DE EVALUACION DESEMPEÑO MODEL 
public class Evaluaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate fechaEvaluacion;

    @Column(nullable = false, length = 50)
    private String periodo; // Ej: Semestre 1 2026

    @NotBlank(message = "Las Observaciones son obligatorias")
    @Size(min = 2, max = 650, message = "La experiencia debe tener al menos 2 caracteres")
    @Column(nullable = false, length = 650)
    private String observaciones;

    @NotBlank(message = "Las fortalezas son obligatorias")
    @Size(min = 2, max = 250, message = "Las fortalezas deben tener al menos 2 caracteres")
    @Column(nullable = false, length = 250)
    private String fortalezas;

    @NotBlank(message = "Las habilidades son obligatorias")
    @Size(min = 2, max = 250, message = "Las habilidades deben tener al menos 2 caracteres")
    @Column(nullable = false, length = 250)
    private String debilidades;

    @NotBlank(message = "Las mejoras son obligatorias")
    @Size(min = 2, max = 250, message = "Las mejoras deben tener al menos 2 caracteres")
    @Column(nullable = false, length = 250)
    private String porMejorar;

    // Relaciones

    /* BORRAR (CREO)

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;
    
    */
}
