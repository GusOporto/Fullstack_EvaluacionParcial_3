package com.datos.laborales.model;

import java.util.List;  // BORRAR (CREO)

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;  // BORRAR (CREO)
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
@Table(name = "cargo") //buena practica que no sea en plural dijo prueba pasadaSS
// CLASE DE CARGOS MODEL
public class Cargos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 55, message = "El nombre debe tener entre 2 y 55 caracteres")
    @Column(nullable = false, length = 55)
    private String nombre;// Ej: "Cajero", "Reponedor", "Gerente de Tienda", "Bodeguero"

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 2, max = 550, message = "La descripcion debe tener al menos 2 caracteres")
    @Column(nullable = false, length = 550)
    private String descripcion;

    @Column(nullable = false)
    private Integer sueldo;

    // Relaciones

    /*  BORRRAR (CREO)

    @OneToMany(mappedBy = "cargos")
    private List<Colaborador> colaboradores;
    
    */
    


}
