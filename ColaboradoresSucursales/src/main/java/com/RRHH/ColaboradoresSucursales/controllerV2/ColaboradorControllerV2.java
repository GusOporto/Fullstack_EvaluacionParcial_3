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

import com.RRHH.ColaboradoresSucursales.DTO.ColaboradorDTO;
import com.RRHH.ColaboradoresSucursales.assemblers.ColaboradorModelAssembler;
import com.RRHH.ColaboradoresSucursales.model.Colaborador;
import com.RRHH.ColaboradoresSucursales.service.ColaboradorService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/colaboradores")
@Tag(name = "Colaboradores V2", description = "Operaciones para gestionar Colaboradores usando HATEOAS.")
public class ColaboradorControllerV2 {

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private ColaboradorModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar colaboradores", description = "Obtiene una lista de todos los colaboradores registrados (con enlaces HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de colaboradores obtenida exitosamente")
    })
    public CollectionModel<EntityModel<ColaboradorDTO>> listar() {
        List<EntityModel<ColaboradorDTO>> colaboradores = colaboradorService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(colaboradores,
                linkTo(methodOn(ColaboradorControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar colaborador por ID", description = "Obtiene un colaborador específico por su ID (con enlaces HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colaborador encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    public EntityModel<ColaboradorDTO> buscarPorId(@PathVariable Long id) {
        ColaboradorDTO colaborador = colaboradorService.findById(id);
        return assembler.toModel(colaborador);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Agregar colaborador", description = "Crea un nuevo colaborador en el sistema (con enlaces HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Colaborador creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EntityModel<ColaboradorDTO>> agregar(@Valid @RequestBody Colaborador colaborador) {
        ColaboradorDTO newColaborador = colaboradorService.save(colaborador);
        return ResponseEntity
                .created(linkTo(methodOn(ColaboradorControllerV2.class).buscarPorId(newColaborador.getId())).toUri())
                .body(assembler.toModel(newColaborador));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar colaborador", description = "Reemplaza la información de un colaborador existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colaborador actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    public ResponseEntity<EntityModel<ColaboradorDTO>> actualizar(@PathVariable Long id,
            @Valid @RequestBody Colaborador colaborador) {
        ColaboradorDTO updatedColaborador = colaboradorService.updateColaborador(id, colaborador);
        return ResponseEntity.ok(assembler.toModel(updatedColaborador));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar colaborador", description = "Elimina un colaborador específico del sistema por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Colaborador eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        colaboradorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/run/{run}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar colaborador por RUN", description = "Obtiene un colaborador que coincida con el RUN proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colaborador encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    public EntityModel<ColaboradorDTO> buscarPorRun(@PathVariable String run) {
        ColaboradorDTO colaborador = colaboradorService.findByRun(run);
        return assembler.toModel(colaborador);
    }

    @GetMapping(value = "/sucursal/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar colaboradores por Sucursal", description = "Obtiene una lista de colaboradores asociados a una sucursal específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de colaboradores obtenida exitosamente")
    })
    public CollectionModel<EntityModel<ColaboradorDTO>> buscarPorSucursal(@PathVariable Long id) {
        List<EntityModel<ColaboradorDTO>> colaboradores = colaboradorService.findBySucursales(id).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(colaboradores,
                linkTo(methodOn(ColaboradorControllerV2.class).buscarPorSucursal(id)).withSelfRel());
    }

    @GetMapping(value = "/region/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar colaboradores por Región", description = "Obtiene una lista de colaboradores asociados a una región específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de colaboradores obtenida exitosamente")
    })
    public CollectionModel<EntityModel<ColaboradorDTO>> buscarPorRegion(@PathVariable Long id) {
        List<EntityModel<ColaboradorDTO>> colaboradores = colaboradorService.findByRegion(id).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(colaboradores,
                linkTo(methodOn(ColaboradorControllerV2.class).buscarPorRegion(id)).withSelfRel());
    }

    @GetMapping(value = "/comuna/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar colaboradores por Comuna", description = "Obtiene una lista de colaboradores asociados a una comuna específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de colaboradores obtenida exitosamente")
    })
    public CollectionModel<EntityModel<ColaboradorDTO>> buscarPorComuna(@PathVariable Long id) {
        List<EntityModel<ColaboradorDTO>> colaboradores = colaboradorService.findByComuna(id).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(colaboradores,
                linkTo(methodOn(ColaboradorControllerV2.class).buscarPorComuna(id)).withSelfRel());
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Editar colaborador", description = "Actualiza la información de un colaborador existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colaborador actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    public ResponseEntity<EntityModel<ColaboradorDTO>> editarColaborador(@PathVariable Long id,
            @RequestBody Colaborador colaborador) {
        ColaboradorDTO updatedColaborador = colaboradorService.updateColaborador(id, colaborador);
        return ResponseEntity.ok(assembler.toModel(updatedColaborador));
    }
}
