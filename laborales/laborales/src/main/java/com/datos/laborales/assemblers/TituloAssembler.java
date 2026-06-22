package com.datos.laborales.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.datos.laborales.controller.TituloController;
import com.datos.laborales.model.Titulo;

@Component
public class TituloAssembler implements RepresentationModelAssembler<Titulo, EntityModel<Titulo>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Titulo> toModel(Titulo titulo){
        return EntityModel.of(titulo,

            linkTo(methodOn(TituloController.class).buscarPorId(titulo.getId())).withSelfRel(),
            linkTo(methodOn(TituloController.class).listar()).withRel("Listar Titulos"),
            linkTo(methodOn(TituloController.class).actualiazr(titulo.getId(), titulo)).withRel("Actualizar Titulo"),
            linkTo(methodOn(TituloController.class).eliminar(titulo.getId())).withRel("Eliminar Titulo"),
            linkTo(methodOn(TituloController.class).editarTitulo(titulo.getId(), titulo)).withRel("Editar Titulo")

        );
    }
}
