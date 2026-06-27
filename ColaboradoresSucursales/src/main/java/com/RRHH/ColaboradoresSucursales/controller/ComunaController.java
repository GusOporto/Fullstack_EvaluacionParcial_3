package com.RRHH.ColaboradoresSucursales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.RRHH.ColaboradoresSucursales.DTO.ComunaDTO;
import com.RRHH.ColaboradoresSucursales.model.Comuna;
import com.RRHH.ColaboradoresSucursales.service.ComunaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/comunas")
@Tag(name = "Comunas", description = "Operaciones para gestionar Comunas.")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    @Operation(summary = "Listar comunas", description = "Obtiene una lista de todas las comunas registrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de comunas obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay comunas registradas")
    })
    public ResponseEntity<List<ComunaDTO>> listar() {
        List<ComunaDTO> comunas = comunaService.findAll();
        if (comunas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(comunas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar comuna por ID", description = "Obtiene una comuna específica por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<ComunaDTO> buscarPorId(@PathVariable Long id) {
        try {
            ComunaDTO comuna = comunaService.findById(id);
            return new ResponseEntity<>(comuna, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar comuna", description = "Crea una nueva comuna en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Comuna creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ComunaDTO> agregar(@Valid @RequestBody Comuna comuna) {
        try {
            ComunaDTO guardadaDTO = comunaService.save(comuna);
            return new ResponseEntity<>(guardadaDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar comuna", description = "Actualiza la información de una comuna existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<ComunaDTO> editarComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        try {
            ComunaDTO editadoDTO = comunaService.updateComuna(id, comuna);
            return new ResponseEntity<>(editadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar comuna", description = "Reemplaza la información de una comuna existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<ComunaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody Comuna comuna) {
        try {
            ComunaDTO actualizadoDTO = comunaService.updateComuna(id, comuna);
            return new ResponseEntity<>(actualizadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar comuna", description = "Elimina una comuna específica del sistema por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        String resultado = comunaService.delete(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

}
