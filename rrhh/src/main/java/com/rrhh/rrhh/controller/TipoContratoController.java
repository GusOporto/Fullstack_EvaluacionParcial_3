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

import com.rrhh.rrhh.DTO.TipoContratoDTO;
import com.rrhh.rrhh.model.TipoContrato;
import com.rrhh.rrhh.service.TipoContratoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tipos-contrato")
@Tag(name = "Tipos de Contrato", description = "Operaciones relacionadas con los tipos de contrato del sistema RRHH")
public class TipoContratoController {

    @Autowired
    private TipoContratoService tipoContratoService;

    @GetMapping
    @Operation(summary = "Obtener todos los tipos de contrato", description = "Retorna una lista con todos los tipos de contrato registrados en el sistema RRHH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de contrato encontrados correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen tipos de contrato registrados")
    })
    public ResponseEntity<List<TipoContratoDTO>> obtenerTipoContrato() {

        List<TipoContratoDTO> tipos = tipoContratoService.obtenerTipoContrato();

        if (tipos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de contrato por ID", description = "Busca y retorna la información de un tipo de contrato según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de contrato encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de contrato no encontrado")
    })
    public ResponseEntity<TipoContratoDTO> buscarPorId(
            @Parameter(description = "ID del tipo de contrato que se desea buscar", required = true, example = "1") @PathVariable Long id) {

        try {
            TipoContratoDTO tipo = tipoContratoService.buscarPorId(id);
            return new ResponseEntity<>(tipo, HttpStatus.OK);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo tipo de contrato", description = "Registra un nuevo tipo de contrato en el sistema RRHH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de contrato creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para crear el tipo de contrato")
    })
    public ResponseEntity<TipoContratoDTO> agregarTipoContrato(
            @Valid @RequestBody TipoContrato tipoContrato) {

        try {
            TipoContratoDTO guardado = tipoContratoService.guardarTipoContrato(tipoContrato);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tipo de contrato", description = "Actualiza los datos de un tipo de contrato existente según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de contrato actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de contrato no encontrado")
    })
    public ResponseEntity<TipoContratoDTO> actualizarTipoContrato(
            @Parameter(description = "ID del tipo de contrato que se desea actualizar", required = true, example = "1") @PathVariable Long id,
            @Valid @RequestBody TipoContrato tipoContrato) {

        try {
            TipoContratoDTO actualizado = tipoContratoService.actualizarTipoContrato(id, tipoContrato);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tipo de contrato", description = "Elimina un tipo de contrato registrado en el sistema RRHH según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de contrato eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de contrato no encontrado")
    })
    public ResponseEntity<String> eliminarTipoContrato(
            @Parameter(description = "ID del tipo de contrato que se desea eliminar", required = true, example = "1") @PathVariable Long id) {

        String resultado = tipoContratoService.eliminar(id);

        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}

// http://localhost:8080/doc/swagger-ui.html