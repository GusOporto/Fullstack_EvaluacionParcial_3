package com.datos.laborales.model;

import java.time.LocalDate;
import java.util.List; //BORRAR CREO

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany; //BORRAR CREO
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
@Table(name= "titulo")
//CLASE DE TITULO MODEL
public class Titulo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Size(min=5, max=55)
    @Column(nullable= false, length = 55) 
    @NotBlank (message = "El nombre es obligatorio")
    private String nombre; // "Técnico en Administración", "Ingeniero Comercial", etc.

    @NotBlank (message = "La institucion es obligatoria")
    @Size(min = 2, max = 50, message = "La institucion deben tener al menos 2 caracteres")
    @Column(nullable = false, length = 50)
    private String institucion;

    @Column(nullable= false)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate fechaObtencion;

    //Relaciones
    
    /* BORRAR CREO

    @ManyToMany(mappedBy = "titulos")
    private List<Colaborador> colaboradores;
    
    */
}
