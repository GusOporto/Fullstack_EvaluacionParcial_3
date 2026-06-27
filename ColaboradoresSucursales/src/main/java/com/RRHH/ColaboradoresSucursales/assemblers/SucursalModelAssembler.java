package com.RRHH.ColaboradoresSucursales.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.controllerV2.SucursalControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<SucursalDTO, EntityModel<SucursalDTO>> {

    @Override
    public EntityModel<SucursalDTO> toModel(SucursalDTO sucursal) {
        EntityModel<SucursalDTO> model = EntityModel.of(sucursal);

        model.add(linkTo(methodOn(SucursalControllerV2.class).buscarPorId(sucursal.getId())).withSelfRel());

        return model;
    }
}
