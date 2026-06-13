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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/regiones")
public class RegionControllerV2 {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<RegionDTO>> listar() {
        List<EntityModel<RegionDTO>> regiones = regionService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(regiones,
                linkTo(methodOn(RegionControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<RegionDTO> buscarPorId(@PathVariable Long id) {
        RegionDTO region = regionService.findById(id);
        return assembler.toModel(region);
    }

    @GetMapping(value = "/{id}/sucursales", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<SucursalDTO>> listarSucursalesPorRegion(@PathVariable Long id) {
        List<EntityModel<SucursalDTO>> sucursales = regionService.findSucursalesByRegionId(id).stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return CollectionModel.of(sucursales,
                linkTo(methodOn(RegionControllerV2.class).listarSucursalesPorRegion(id)).withSelfRel());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionDTO>> agregar(@Valid @RequestBody Region region) {
        RegionDTO newRegion = regionService.save(region);
        return ResponseEntity
                .created(linkTo(methodOn(RegionControllerV2.class).buscarPorId(newRegion.getId())).toUri())
                .body(assembler.toModel(newRegion));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody Region region) {
        RegionDTO updatedRegion = regionService.updateRegion(id, region);
        return ResponseEntity
                .ok(assembler.toModel(updatedRegion));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
