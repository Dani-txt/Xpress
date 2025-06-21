package Grupo1.Xpress.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import Grupo1.Xpress.Controller.MarcaControllerV2;
import Grupo1.Xpress.Modelo.Marca;

@Component
public class MarcaModelAssembler implements RepresentationModelAssembler<Marca, EntityModel<Marca>> {
    @SuppressWarnings("null")

    @Override
    public EntityModel<Marca> toModel(Marca marca) {
        return EntityModel.of(marca,
            linkTo(methodOn(MarcaControllerV2.class).getMarcaById(marca.getId())).withSelfRel(),
            linkTo(methodOn(MarcaControllerV2.class).getAllMarca()).withRel("ApiServices"),
            linkTo(methodOn(MarcaControllerV2.class).updateMarca(marca.getId(), marca)).withRel("actualizar"),
            linkTo(methodOn(MarcaControllerV2.class).deleteMarca(marca.getId())).withRel("eliminar"),
            linkTo(methodOn(MarcaControllerV2.class).patchMarca(marca.getId(), marca)).withRel("actualizar-ApiService")
        );
    }
}
