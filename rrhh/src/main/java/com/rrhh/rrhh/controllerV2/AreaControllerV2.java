package com.rrhh.rrhh.controllerV2;

import com.rrhh.rrhh.DTO.AreaDTO;
import com.rrhh.rrhh.assemblers.AreaModelAssembler;
import com.rrhh.rrhh.model.Area;
import com.rrhh.rrhh.service.AreaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/areas")
@Tag(name = "Areas V2-HATEOAS", description = "Operaciones HATEOAS relacionadas con las áreas del sistema RRHH")
public class AreaControllerV2 {

    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las áreas con HATEOAS", description = "Retorna una colección de áreas con  enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Áreas encontradas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public CollectionModel<EntityModel<AreaDTO>> getAllAreas() {
        try {
            List<AreaDTO> areasDTO = areaService.obtenerAreas();

            List<EntityModel<AreaDTO>> areas = new ArrayList<>();
            for (AreaDTO area : areasDTO) {
                areas.add(assembler.toModel(area));
            }
            return CollectionModel.of(areas,
                    linkTo(methodOn(AreaControllerV2.class).getAllAreas()).withSelfRel());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las áreas", e);
        }

    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener área por ID con HATEOAS", description = "Retorna un área específica con enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    
    public ResponseEntity<EntityModel<AreaDTO>> getAreaById(@PathVariable Long id) {
        try {
            AreaDTO areaDTO = areaService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(areaDTO));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el área con ID: " + id, e);
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un área con HATEOAS", description = "Crea un nuevo área y retorna la información del área creada con enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Área creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<AreaDTO>> createArea(@Valid @RequestBody Area area) {
        try {
            AreaDTO areaDTO = areaService.guardarArea(area);
            return ResponseEntity.created(linkTo(methodOn(AreaControllerV2.class).getAreaById(areaDTO.getId())).toUri())
                    .body(assembler.toModel(areaDTO));
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el área", e);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un área con HATEOAS", description = "Actualiza un área existente y retorna la información actualizada con enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<AreaDTO>> updateArea(@PathVariable Long id, @Valid @RequestBody Area area) {
        try {
            AreaDTO areaDTO = areaService.actualizarArea(id, area);
            return ResponseEntity.ok(assembler.toModel(areaDTO));
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el área con ID: " + id, e);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un área con HATEOAS", description = "Elimina un área existente y retorna una respuesta sin contenido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Área eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteArea(@PathVariable Long id) {
        try {
            areaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el área con ID: " + id, e);
        }
    }
}
