package com.datos.laborales.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "id", "fechaEvaluacion", "periodo",  "fortalezas",
                     "debilidades", "porMejorar", "observaciones", })
public class EvaluacionesDTO {

    private Long id;
    private LocalDate fechaEvaluacion;
    private String periodo;
    private String observaciones;
    private String fortalezas;
    private String debilidades;
    private String porMejorar;
}
