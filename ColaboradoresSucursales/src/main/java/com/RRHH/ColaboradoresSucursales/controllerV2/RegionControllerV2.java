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

import com.RRHH.ColaboradoresSucursales.DTO.RegionDTO;
import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.assemblers.RegionModelAssembler;
import com.RRHH.ColaboradoresSucursales.model.Region;
import com.RRHH.ColaboradoresSucursales.service.RegionService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/regiones")
@Tag(name = "Regiones V2", description = "Operaciones para gestionar Regiones usando HATEOAS.")
public class RegionControllerV2 {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar regiones", description = "Obtiene una lista de todas las regiones registradas (con enlaces HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de regiones obtenida exitosamente")
    })
    public CollectionModel<EntityModel<RegionDTO>> listar() {
        List<EntityModel<RegionDTO>> regiones = regionService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(regiones,
                linkTo(methodOn(RegionControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar región por ID", description = "Obtiene una región específica por su ID (con enlaces HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Región encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public EntityModel<RegionDTO> buscarPorId(@PathVariable Long id) {
        RegionDTO region = regionService.findById(id);
        return assembler.toModel(region);
    }

    @GetMapping(value = "/{id}/sucursales", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar sucursales de la región", description = "Obtiene las sucursales asociadas a la región (HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public CollectionModel<EntityModel<SucursalDTO>> listarSucursalesPorRegion(@PathVariable Long id) {
        List<EntityModel<SucursalDTO>> sucursales = regionService.findSucursalesByRegionId(id).stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return CollectionModel.of(sucursales,
                linkTo(methodOn(RegionControllerV2.class).listarSucursalesPorRegion(id)).withSelfRel());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Agregar región", description = "Crea una nueva región en el sistema (con enlaces HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Región creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EntityModel<RegionDTO>> agregar(@Valid @RequestBody Region region) {
        RegionDTO newRegion = regionService.save(region);
        return ResponseEntity
                .created(linkTo(methodOn(RegionControllerV2.class).buscarPorId(newRegion.getId())).toUri())
                .body(assembler.toModel(newRegion));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Editar región", description = "Actualiza parcialmente la información de una región existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Región actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<EntityModel<RegionDTO>> editarRegion(@PathVariable Long id, @RequestBody Region region) {
        RegionDTO updatedRegion = regionService.updateRegion(id, region);
        return ResponseEntity.ok(assembler.toModel(updatedRegion));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar región", description = "Reemplaza la información de una región existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Región actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<EntityModel<RegionDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody Region region) {
        RegionDTO updatedRegion = regionService.updateRegion(id, region);
        return ResponseEntity
                .ok(assembler.toModel(updatedRegion));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar región", description = "Elimina una región específica del sistema por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Región eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
