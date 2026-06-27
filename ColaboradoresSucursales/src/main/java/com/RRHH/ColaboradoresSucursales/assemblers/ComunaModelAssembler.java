package com.RRHH.ColaboradoresSucursales.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.RRHH.ColaboradoresSucursales.DTO.ComunaDTO;
import com.RRHH.ColaboradoresSucursales.controllerV2.ComunaControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ComunaModelAssembler implements RepresentationModelAssembler<ComunaDTO, EntityModel<ComunaDTO>> {

    @Override
    public EntityModel<ComunaDTO> toModel(ComunaDTO comuna) {
        EntityModel<ComunaDTO> model = EntityModel.of(comuna);

        model.add(linkTo(methodOn(ComunaControllerV2.class).buscarPorId(comuna.getId())).withSelfRel());

        return model;
    }
}
