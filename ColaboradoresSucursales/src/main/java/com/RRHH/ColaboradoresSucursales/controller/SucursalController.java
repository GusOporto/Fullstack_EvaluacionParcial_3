package com.RRHH.ColaboradoresSucursales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
import com.RRHH.ColaboradoresSucursales.service.SucursalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/sucursales")
@Tag(name = "Sucursales", description = "Operaciones para gestionar Sucursales.")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    @Operation(summary = "Listar sucursales", description = "Obtiene una lista de todas las sucursales registradas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay sucursales registradas")
    })
    public ResponseEntity<List<SucursalDTO>> listar() {
        List<SucursalDTO> sucursales = sucursalService.findAll();
        if (sucursales.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sucursales, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sucursal por ID", description = "Obtiene una sucursal específica por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<SucursalDTO> buscarPorId(@PathVariable Long id) {
        try {
            SucursalDTO sucursal = sucursalService.findById(id);
            return new ResponseEntity<>(sucursal, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar sucursal", description = "Crea una nueva sucursal en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<SucursalDTO> agregar(@Valid @RequestBody Sucursal sucursal) {
        try {
            SucursalDTO guardadoDTO = sucursalService.save(sucursal);
            return new ResponseEntity<>(guardadoDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar sucursal", description = "Actualiza la información de una sucursal existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<SucursalDTO> editarSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        try {
            SucursalDTO editadoDTO = sucursalService.updateSucursal(id, sucursal);
            return new ResponseEntity<>(editadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sucursal", description = "Reemplaza la información de una sucursal existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<SucursalDTO> actualizar(@PathVariable Long id, @Valid @RequestBody Sucursal sucursal) {
        try {
            SucursalDTO actualizadoDTO = sucursalService.updateSucursal(id, sucursal);
            return new ResponseEntity<>(actualizadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal específica del sistema por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        String resultado = sucursalService.delete(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
