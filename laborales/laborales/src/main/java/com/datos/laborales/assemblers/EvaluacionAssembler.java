package com.datos.laborales.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.datos.laborales.controller.EvaluacionesController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.datos.laborales.model.Evaluaciones;

@Component
public class EvaluacionAssembler implements RepresentationModelAssembler<Evaluaciones, EntityModel<Evaluaciones>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Evaluaciones> toModel(Evaluaciones evaluaciones){
        return EntityModel.of(evaluaciones,

            linkTo(methodOn(EvaluacionesController.class).buscarPorId(evaluaciones.getId())).withSelfRel(),
            linkTo(methodOn(EvaluacionesController.class).listar()).withRel("Listar Evaluaciones"),
            linkTo(methodOn(EvaluacionesController.class).actualizar(evaluaciones.getId(), evaluaciones)).withRel("Actualizar Evaluacion"),
            linkTo(methodOn(EvaluacionesController.class).eliminar(evaluaciones.getId())).withRel("Eliminar Evaluacion"),
            linkTo(methodOn(EvaluacionesController.class).editarEvaluaciones(evaluaciones.getId(), evaluaciones)).withRel("Editar Evaluacion")

        );
    }

}
