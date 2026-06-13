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

import com.RRHH.ColaboradoresSucursales.DTO.RegionDTO;
import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.model.Region;
import com.RRHH.ColaboradoresSucursales.service.RegionService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/regiones")
@Tag(name = "Regiones", description = "Operaciones para gestionar Regiones.")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    @Operation(summary = "Listar regiones", description = "Obtiene una lista de todas las regiones registradas.")
    public ResponseEntity<List<RegionDTO>> listar() {
        List<RegionDTO> regions = regionService.findAll();
        if (regions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(regions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar región por ID", description = "Obtiene una región específica por su ID.")
    public ResponseEntity<RegionDTO> buscarPorId(@PathVariable Long id) {
        try {
            RegionDTO region = regionService.findById(id);
            return new ResponseEntity<>(region, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/sucursales")
    @Operation(summary = "Listar sucursales de la región", description = "Obtiene las sucursales asociadas a la región.")
    public ResponseEntity<List<SucursalDTO>> listarSucursalesPorRegion(@PathVariable Long id) {
        try {
            List<SucursalDTO> sucursales = regionService.findSucursalesByRegionId(id);
            if (sucursales.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(sucursales, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar región", description = "Crea una nueva región en el sistema.")
    public ResponseEntity<RegionDTO> agregar(@Valid @RequestBody Region region) {
        try {
            RegionDTO guardadoDTO = regionService.save(region);
            return new ResponseEntity<>(guardadoDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar región", description = "Actualiza la información de una región existente por ID.")
    public ResponseEntity<RegionDTO> editarRegion(@PathVariable Long id, @RequestBody Region region) {
        try {
            RegionDTO editadoDTO = regionService.updateRegion(id, region);
            return new ResponseEntity<>(editadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar región", description = "Reemplaza la información de una región existente por ID.")
    public ResponseEntity<RegionDTO> actualizar(@Valid @PathVariable Long id, @RequestBody Region region) {
        try {
            RegionDTO actualizadoDTO = regionService.updateRegion(id, region);
            return new ResponseEntity<>(actualizadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar región", description = "Elimina una región específica del sistema por ID.")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        String resultado = regionService.delete(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
