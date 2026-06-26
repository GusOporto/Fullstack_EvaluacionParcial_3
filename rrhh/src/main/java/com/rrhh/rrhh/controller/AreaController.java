package com.rrhh.rrhh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rrhh.rrhh.DTO.AreaDTO;
import com.rrhh.rrhh.model.Area;
import com.rrhh.rrhh.service.AreaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/areas")
@Tag(name = "Áreas", description = "Operaciones relacionadas con las áreas del sistema RRHH")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    @Operation(summary = "Obtener todas las áreas", description = "Retorna una lista con todas las áreas registradas en el sistema RRHH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Áreas encontradas correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen áreas registradas")
    })
    public ResponseEntity<List<AreaDTO>> todasLasAreas() {
        List<AreaDTO> areas = areaService.obtenerAreas();
        if (areas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(areas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar área por ID", description = "Busca y retorna la información de un área según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada")
    })
    public ResponseEntity<AreaDTO> buscarPorId(
            @Parameter(description = "ID del área que desea buscar", required = true, example = "1") @PathVariable Long id) {
        try {
            AreaDTO area = areaService.buscarPorId(id);
            return new ResponseEntity<>(area, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear una nueva área", description = "Registra una nueva área en el sistema RRHH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Área creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para crear el área")
    })
    public ResponseEntity<AreaDTO> agregarArea(@Valid@RequestBody Area area) {
        try {
            AreaDTO guardadaDTO = areaService.guardarArea(area);
            return new ResponseEntity<>(guardadaDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un área", description = "Actualiza los datos de un área existente según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada")
    })
    public ResponseEntity<AreaDTO> actualizarArea(
            @Parameter(description = "ID del área que se desea actualizar", required = true, example = "1") @PathVariable Long id,
            @Valid @RequestBody Area area) {

        try {
            AreaDTO areaActualizadaDTO = areaService.actualizarArea(id, area);
            return new ResponseEntity<>(areaActualizadaDTO, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un área", description = "Elimina un área registrada en el sistema RRHH según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada")
    })
    public ResponseEntity<String> eliminarArea(
            @Parameter(description = "ID del área que se desea eliminar", required = true, example = "1") @PathVariable Long id) {

        String resultado = areaService.eliminar(id);

        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
