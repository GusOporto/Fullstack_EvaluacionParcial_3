package com.rrhh.rrhh.controllerV2;

import com.rrhh.rrhh.DTO.TipoContratoDTO;
import com.rrhh.rrhh.assemblers.TipoContratoModelAssembler;
import com.rrhh.rrhh.model.TipoContrato;
import com.rrhh.rrhh.service.TipoContratoService;

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
@RequestMapping("/api/v2/tipos-contrato")
@Tag(name = "Tipos de Contrato V2-HATEOAS", description = "Operaciones HATEOAS relacionadas con los tipos de contrato del sistema RRHH")
public class TipoContratoControllerV2 {

    @Autowired
    private TipoContratoService tipoContratoService;

    @Autowired
    private TipoContratoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los tipos de contrato con HATEOAS", description = "Retorna una lista de todos los tipos de contrato con enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de contrato encontrados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public CollectionModel<EntityModel<TipoContratoDTO>> getAllTiposContrato() {
        try {
            List<TipoContratoDTO> tipoDTOs = tipoContratoService.obtenerTipoContrato();
            List<EntityModel<TipoContratoDTO>> tipoContrato = new ArrayList<>();
            for (TipoContratoDTO tipo : tipoDTOs) {
                tipoContrato.add(assembler.toModel(tipo));
            }
            return CollectionModel.of(tipoContrato,
                    linkTo(methodOn(TipoContratoControllerV2.class).getAllTiposContrato()).withSelfRel());

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los tipos de contrato", e);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener tipo de contrato por ID con HATEOAS", description = "Retorna un tipo de contrato específico con enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de contrato encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de contrato no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<TipoContratoDTO>> getTipoContratoById(@PathVariable Long id) {
        try {
            TipoContratoDTO tipoDTO = tipoContratoService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(tipoDTO));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el tipo de contrato con ID: " + id, e);
        }

    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un tipo de contrato con HATEOAS", description = "Crea un nuevo tipo de contrato y retorna la información del tipo de contrato creado con enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de contrato creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<TipoContratoDTO>> createTipoContrato(@Valid @RequestBody TipoContrato tipoContrato) {
        try {
            TipoContratoDTO tipoDTO = tipoContratoService.guardarTipoContrato(tipoContrato);
            return ResponseEntity
                    .created(linkTo(methodOn(TipoContratoControllerV2.class).getTipoContratoById(tipoDTO.getId()))
                            .toUri())
                    .body(assembler.toModel(tipoDTO));
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el tipo de contrato", e);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un tipo de contrato con HATEOAS", description = "Actualiza un tipo de contrato existente y retorna la información actualizada con enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de contrato actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de contrato no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<TipoContratoDTO>> updateTipoContrato(@PathVariable Long id,
            @Valid @RequestBody TipoContrato tipoContrato) {
        try {
            TipoContratoDTO tipoDTO = tipoContratoService.actualizarTipoContrato(id, tipoContrato);
            return ResponseEntity.ok(assembler.toModel(tipoDTO));
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el tipo de contrato con ID: " + id, e);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un tipo de contrato con HATEOAS", description = "Elimina un tipo de contrato existente y retorna una respuesta sin contenido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de contrato eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de contrato no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteTipoContrato(@PathVariable Long id) {
        try {
            tipoContratoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el tipo de contrato con ID: " + id, e);
        }
    }
}