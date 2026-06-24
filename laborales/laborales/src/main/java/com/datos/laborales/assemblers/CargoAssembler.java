package com.datos.laborales.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.datos.laborales.controller.CargosController;
import com.datos.laborales.model.Cargos;
import org.springframework.hateoas.EntityModel;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CargoAssembler implements RepresentationModelAssembler<Cargos, EntityModel<Cargos>>  {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Cargos> toModel(Cargos cargo){
        return EntityModel.of(cargo,

            linkTo(methodOn(CargosController.class).buscarPorId(cargo.getId())).withSelfRel(),
            linkTo(methodOn(CargosController.class).listar()).withRel("Listar Cargos"),
            linkTo(methodOn(CargosController.class).actualizar(cargo.getId(), cargo)).withRel("Actualizar Cargo"),
            linkTo(methodOn(CargosController.class).eliminar(cargo.getId())).withRel("Eliminar"),
            linkTo(methodOn(CargosController.class).editarCargo(cargo.getId(), cargo)).withRel("Editar Cargo")
            
        );
    
    }

}
