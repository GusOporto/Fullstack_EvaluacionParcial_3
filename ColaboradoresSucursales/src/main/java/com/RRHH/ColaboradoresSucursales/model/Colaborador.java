package com.RRHH.ColaboradoresSucursales.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "colaboradores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Debe indicar un Run valido, sin punto ni guion.")
    @Size(min = 8, max = 9, message = "El Run debe tener entre 8 y 9 digitos.")
    @Column(nullable = false, length = 9)
    private String run;

    @NotBlank(message = "El nombre no puede estar vacio.")
    @Column(nullable = false, length = 30)
    private String nombres;

    @NotBlank(message = "El apellido no puede estar vacio.")
    @Column(nullable = false, length = 30)
    private String apellidos;

    @NotNull(message = "Ingrese una fecha valida. ej: 2000-12-01")
    @Past(message = "La fecha de nacimiento debe ser en el pasado.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, length = 10)
    private LocalDate fechaNacimiento;

    @NotBlank(message = "Ingrese un numero de telefono valido.")
    @Column(nullable = false, length = 11)
    private String telefono;

    @Email(message = "Ingrese un correo valido.")
    @Column(unique = true, nullable = true, length = 30)
    private String correo;

    @NotBlank(message = "Debe ingresar una direccion valida.")
    @Column(nullable = false, length = 50)
    private String direccion;

    // Relaciones Internas
    @ManyToMany
    @JoinTable(name = "colaborador_sucursal", joinColumns = @JoinColumn(name = "colaborador_id"), inverseJoinColumns = @JoinColumn(name = "sucursal_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Sucursal> sucursales = new ArrayList<>();

    // Relaciones Externas
    @NotNull(message = "Debe seleccionar un cargo.")
    private Integer cargoId;

    @NotNull(message = "Debe asociar un curriculum.")
    private Integer curriculumId;

    @NotNull(message = "Debe seleccionar un titulo.")
    private List<Integer> titulosId;

}
