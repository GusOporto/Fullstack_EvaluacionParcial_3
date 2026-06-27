package com.RRHH.ColaboradoresSucursales.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.RRHH.ColaboradoresSucursales.DTO.ColaboradorDTO;
import com.RRHH.ColaboradoresSucursales.controllerV2.ColaboradorControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ColaboradorModelAssembler
        implements RepresentationModelAssembler<ColaboradorDTO, EntityModel<ColaboradorDTO>> {

    @Override
    public EntityModel<ColaboradorDTO> toModel(ColaboradorDTO colaborador) {
        EntityModel<ColaboradorDTO> model = EntityModel.of(colaborador);

        model.add(linkTo(methodOn(ColaboradorControllerV2.class).buscarPorId(colaborador.getId())).withSelfRel());
        model.add(linkTo(methodOn(ColaboradorControllerV2.class).listar()).withRel("all"));
        model.add(linkTo(methodOn(ColaboradorControllerV2.class)
                .agregar(new com.RRHH.ColaboradoresSucursales.model.Colaborador()))
                .withRel("add"));
        model.add(linkTo(methodOn(ColaboradorControllerV2.class).actualizar(colaborador.getId(),
                new com.RRHH.ColaboradoresSucursales.model.Colaborador()))
                .withRel("update"));
        model.add(linkTo(methodOn(ColaboradorControllerV2.class).eliminar(colaborador.getId())).withRel("delete"));

        return model;
    }
}
