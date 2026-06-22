package com.datos.laborales.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.datos.laborales.controller.CurriculumController;
import com.datos.laborales.model.Curriculum;

@Component
public class CurriculumAssembler implements RepresentationModelAssembler<Curriculum, EntityModel<Curriculum>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Curriculum> toModel(Curriculum curriculum){
        return EntityModel.of(curriculum,

            linkTo(methodOn(CurriculumController.class).buscarPorId(curriculum.getId())).withSelfRel(),
            linkTo(methodOn(CurriculumController.class).listar()).withRel("Listar Curriculums"),
            linkTo(methodOn(CurriculumController.class).actualizar(curriculum.getId(), curriculum)).withRel("Actualizar Curriculum"),
            linkTo(methodOn(CurriculumController.class).eliminar(curriculum.getId())).withRel("Eliminar Curriculum"),
            linkTo(methodOn(CurriculumController.class).editarCurriculum(curriculum.getId(), curriculum)).withRel("Editar Curriculum")

        );
    }

}
