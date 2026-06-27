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

import com.RRHH.ColaboradoresSucursales.DTO.ComunaDTO;
import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.assemblers.ComunaModelAssembler;
import com.RRHH.ColaboradoresSucursales.model.Comuna;
import com.RRHH.ColaboradoresSucursales.service.ComunaService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/comunas")
@Tag(name = "Comunas V2", description = "Operaciones para gestionar Comunas usando HATEOAS.")
public class ComunaControllerV2 {

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private ComunaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar comunas", description = "Obtiene una lista de todas las comunas registradas (con enlaces HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de comunas obtenida exitosamente")
    })
    public CollectionModel<EntityModel<ComunaDTO>> listar() {
        List<EntityModel<ComunaDTO>> comunas = comunaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(comunas,
                linkTo(methodOn(ComunaControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar comuna por ID", description = "Obtiene una comuna específica por su ID (con enlaces HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public EntityModel<ComunaDTO> buscarPorId(@PathVariable Long id) {
        ComunaDTO comuna = comunaService.findById(id);
        return assembler.toModel(comuna);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Agregar comuna", description = "Crea una nueva comuna en el sistema (con enlaces HATEOAS).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Comuna creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EntityModel<ComunaDTO>> agregar(@Valid @RequestBody Comuna comuna) {
        ComunaDTO newComuna = comunaService.save(comuna);
        return ResponseEntity
                .created(linkTo(methodOn(ComunaControllerV2.class).buscarPorId(newComuna.getId())).toUri())
                .body(assembler.toModel(newComuna));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Editar comuna", description = "Actualiza parcialmente la información de una comuna existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<EntityModel<ComunaDTO>> editarComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        ComunaDTO updatedComuna = comunaService.updateComuna(id, comuna);
        return ResponseEntity.ok(assembler.toModel(updatedComuna));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar comuna", description = "Reemplaza la información de una comuna existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<EntityModel<ComunaDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody Comuna comuna) {
        ComunaDTO updatedComuna = comunaService.updateComuna(id, comuna);
        return ResponseEntity
                .ok(assembler.toModel(updatedComuna));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar comuna", description = "Elimina una comuna específica del sistema por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Comuna eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        comunaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
