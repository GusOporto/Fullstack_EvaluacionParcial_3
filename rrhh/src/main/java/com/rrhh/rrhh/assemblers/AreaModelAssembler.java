package com.rrhh.rrhh.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.rrhh.rrhh.DTO.AreaDTO;
import com.rrhh.rrhh.controllerV2.AreaControllerV2;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AreaModelAssembler implements RepresentationModelAssembler<AreaDTO, EntityModel<AreaDTO>> {

    @Override
    public EntityModel<AreaDTO> toModel(AreaDTO area) {
        return EntityModel.of(area,
                linkTo(methodOn(AreaControllerV2.class).getAreaById(area.getId())).withSelfRel(),
                linkTo(methodOn(AreaControllerV2.class).getAllAreas()).withRel("areas"));
    };
}
