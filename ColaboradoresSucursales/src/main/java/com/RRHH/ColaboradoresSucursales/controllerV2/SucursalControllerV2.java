package com.RRHH.ColaboradoresSucursales.controllerV2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
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
import com.RRHH.ColaboradoresSucursales.assemblers.SucursalModelAssembler;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
import com.RRHH.ColaboradoresSucursales.service.SucursalService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/sucursales")
@Tag(name = "Sucursales V2", description = "Operaciones para gestionar Sucursales usando HATEOAS.")
public class SucursalControllerV2 {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar sucursales", description = "Obtiene una lista de todas las sucursales registradas (con enlaces HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida exitosamente")
    })
    public CollectionModel<EntityModel<SucursalDTO>> listar() {
        List<EntityModel<SucursalDTO>> sucursales = sucursalService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sucursales,
                linkTo(methodOn(SucursalControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar sucursal por ID", description = "Obtiene una sucursal específica por su ID (con enlaces HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public EntityModel<SucursalDTO> buscarPorId(@PathVariable Long id) {
        SucursalDTO sucursal = sucursalService.findById(id);
        return assembler.toModel(sucursal);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Agregar sucursal", description = "Crea una nueva sucursal en el sistema (con enlaces HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EntityModel<SucursalDTO>> agregar(@Valid @RequestBody Sucursal sucursal) {
        SucursalDTO newSucursal = sucursalService.save(sucursal);
        return ResponseEntity
                .created(linkTo(methodOn(SucursalControllerV2.class).buscarPorId(newSucursal.getId())).toUri())
                .body(assembler.toModel(newSucursal));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Editar sucursal", description = "Actualiza la información de una sucursal existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<EntityModel<SucursalDTO>> editarSucursal(@PathVariable Long id,
            @RequestBody Sucursal sucursal) {
        SucursalDTO updatedSucursal = sucursalService.updateSucursal(id, sucursal);
        return ResponseEntity.ok(assembler.toModel(updatedSucursal));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar sucursal", description = "Reemplaza la información de una sucursal existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<EntityModel<SucursalDTO>> actualizar(@PathVariable Long id,
            @Valid @RequestBody Sucursal sucursal) {
        SucursalDTO updatedSucursal = sucursalService.updateSucursal(id, sucursal);
        return ResponseEntity
                .ok(assembler.toModel(updatedSucursal));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal específica del sistema por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Sucursal eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
