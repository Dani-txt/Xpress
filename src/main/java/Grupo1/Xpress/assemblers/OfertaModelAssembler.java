package Grupo1.Xpress.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import Grupo1.Xpress.Controller.OfertaControllerV2;
import Grupo1.Xpress.Modelo.Oferta;

@Component
public class OfertaModelAssembler implements RepresentationModelAssembler<Oferta, EntityModel<Oferta>>{
    @SuppressWarnings("null")

    @Override
    public EntityModel<Oferta> toModel(Oferta oferta) {
        return EntityModel.of(oferta,
            linkTo(methodOn(OfertaControllerV2.class).getOfertaById(oferta.getId())).withSelfRel(),
            linkTo(methodOn(OfertaControllerV2.class).getAllOferta()).withRel("ApiServices"),
            linkTo(methodOn(OfertaControllerV2.class).updateOferta(oferta.getId(), oferta)).withRel("actualizar"),
            linkTo(methodOn(OfertaControllerV2.class).deleteOferta(oferta.getId())).withRel("eliminar"),
            linkTo(methodOn(OfertaControllerV2.class).patchOferta(oferta.getId(), oferta)).withRel("actualizar-ApiService")
        );
    }
}
