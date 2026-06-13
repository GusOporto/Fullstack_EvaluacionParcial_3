package com.RRHH.ColaboradoresSucursales.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.RRHH.ColaboradoresSucursales.controllerV2.RegionControllerV2;
import com.RRHH.ColaboradoresSucursales.DTO.RegionDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RegionModelAssembler implements RepresentationModelAssembler<RegionDTO, EntityModel<RegionDTO>> {

    @Override
    public EntityModel<RegionDTO> toModel(RegionDTO region) {
        return EntityModel.of(region,
                linkTo(methodOn(RegionControllerV2.class).buscarPorId(region.getId())).withSelfRel(),
                linkTo(methodOn(RegionControllerV2.class).listarSucursalesPorRegion(region.getId()))
                        .withRel("sucursales"),
                linkTo(methodOn(RegionControllerV2.class).listar()).withRel("all-regions"));
    }
}
