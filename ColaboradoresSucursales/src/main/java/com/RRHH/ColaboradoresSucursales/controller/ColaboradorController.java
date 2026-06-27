package com.RRHH.ColaboradoresSucursales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.RRHH.ColaboradoresSucursales.DTO.ColaboradorDTO;
import com.RRHH.ColaboradoresSucursales.model.Colaborador;
import com.RRHH.ColaboradoresSucursales.service.ColaboradorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/colaboradores")
@Tag(name = "Colaboradores", description = "Operaciones para gestionar Colaboradores.")
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @GetMapping
    @Operation(summary = "Listar colaboradores", description = "Obtiene una lista de todos los colaboradores registrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de colaboradores obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay colaboradores registrados")
    })
    public ResponseEntity<List<ColaboradorDTO>> listar() {
        List<ColaboradorDTO> colaboradores = colaboradorService.findAll();
        if (colaboradores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(colaboradores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar colaborador por ID", description = "Obtiene un colaborador específico por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colaborador encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    public ResponseEntity<ColaboradorDTO> buscarPorId(@PathVariable Long id) {
        try {
            ColaboradorDTO colaborador = colaboradorService.findById(id);
            return new ResponseEntity<>(colaborador, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/run/{run}")
    @Operation(summary = "Buscar colaborador por RUN", description = "Obtiene un colaborador que coincida con el RUN proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colaborador encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    public ResponseEntity<ColaboradorDTO> buscarPorRun(@PathVariable String run) {
        ColaboradorDTO colaborador = colaboradorService.findByRun(run);
        if (colaborador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(colaborador);
    }

    @GetMapping("/sucursal/{id}")
    @Operation(summary = "Buscar colaboradores por Sucursal", description = "Obtiene una lista de colaboradores asociados a una sucursal específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de colaboradores obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay colaboradores en esta sucursal")
    })
    public ResponseEntity<List<ColaboradorDTO>> buscarPorSucursal(@PathVariable Long id) {
        List<ColaboradorDTO> colaboradores = colaboradorService.findBySucursales(id);
        if (colaboradores.isEmpty()) {
            return new ResponseEntity<>(colaboradores, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(colaboradores, HttpStatus.OK);
    }

    @GetMapping("/region/{id}")
    @Operation(summary = "Buscar colaboradores por Región", description = "Obtiene una lista de colaboradores asociados a una región específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de colaboradores obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay colaboradores en esta región")
    })
    public ResponseEntity<List<ColaboradorDTO>> buscarPorRegion(@PathVariable Long id) {
        List<ColaboradorDTO> colaboradores = colaboradorService.findByRegion(id);
        if (colaboradores.isEmpty()) {
            return new ResponseEntity<>(colaboradores, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(colaboradores, HttpStatus.OK);
    }

    @GetMapping("/comuna/{id}")
    @Operation(summary = "Buscar colaboradores por Comuna", description = "Obtiene una lista de colaboradores asociados a una comuna específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de colaboradores obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay colaboradores en esta comuna")
    })
    public ResponseEntity<List<ColaboradorDTO>> buscarPorComuna(@PathVariable Long id) {
        List<ColaboradorDTO> colaboradores = colaboradorService.findByComuna(id);
        if (colaboradores.isEmpty()) {
            return new ResponseEntity<>(colaboradores, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(colaboradores, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Agregar colaborador", description = "Crea un nuevo colaborador en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Colaborador creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ColaboradorDTO> agregar(@Valid @RequestBody Colaborador colab) {
        try {
            ColaboradorDTO guardadoDTO = colaboradorService.save(colab);
            return new ResponseEntity<>(guardadoDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar colaborador", description = "Actualiza la información de un colaborador existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colaborador actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    public ResponseEntity<ColaboradorDTO> editarColaborador(@PathVariable Long id,
            @RequestBody Colaborador colab) {
        try {
            ColaboradorDTO editadoDTO = colaboradorService.updateColaborador(id, colab);
            return new ResponseEntity<>(editadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar colaborador", description = "Reemplaza la información de un colaborador existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colaborador actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    public ResponseEntity<ColaboradorDTO> actualizar(@PathVariable Long id, @Valid @RequestBody Colaborador colab) {
        try {
            ColaboradorDTO actualizadoDTO = colaboradorService.updateColaborador(id, colab);
            return new ResponseEntity<>(actualizadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar colaborador", description = "Elimina un colaborador específico del sistema por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colaborador eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        String resultado = colaboradorService.delete(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

    // @GetMapping("/buscar-por-cargo/{cargoId}")
    // public ResponseEntity<ColaboradorDTO> buscarPorCargo(@PathVariable Long
    // cargoId){
    // CargoDTO cargo = cargoService.buscarPorCargo(cargoId);
    // if (cargo == null) {
    // return ResponseEntity.notFound().build();
    // }
    // return ResponseEntity.ok(colaborador)
    // }
}
