package com.rrhh.rrhh.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.rrhh.rrhh.DTO.TipoContratoDTO;
import com.rrhh.rrhh.controllerV2.TipoContratoControllerV2;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class TipoContratoModelAssembler
                implements RepresentationModelAssembler<TipoContratoDTO, EntityModel<TipoContratoDTO>> {

        @Override
        public EntityModel<TipoContratoDTO> toModel(TipoContratoDTO tipoContrato) {
                return EntityModel.of(tipoContrato,
                                linkTo(methodOn(TipoContratoControllerV2.class)
                                                .getTipoContratoById(tipoContrato.getId())).withSelfRel(),
                                linkTo(methodOn(TipoContratoControllerV2.class)
                                                .getAllTiposContrato()).withRel("tipos-contrato"));
        }
}
