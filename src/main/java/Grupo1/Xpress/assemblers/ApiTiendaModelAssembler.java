package Grupo1.Xpress.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import Grupo1.Xpress.Controller.ApiTiendaControllerV2;
import Grupo1.Xpress.Modelo.ApiTienda;


@Component
public class ApiTiendaModelAssembler implements RepresentationModelAssembler<ApiTienda, EntityModel<ApiTienda>> {
  @SuppressWarnings("null")

  @Override
  public EntityModel<ApiTienda> toModel(ApiTienda apiTienda) {
    return EntityModel.of(apiTienda,
        linkTo(methodOn(ApiTiendaControllerV2.class).getApiTiendaById(apiTienda.getId())).withSelfRel(),
        linkTo(methodOn(ApiTiendaControllerV2.class).getAllApiTiendas()).withRel("ApiTiendas"),
        linkTo(methodOn(ApiTiendaControllerV2.class).updateApiTienda(apiTienda.getId(), apiTienda)).withRel("actualizar"),
        linkTo(methodOn(ApiTiendaControllerV2.class).deleteApiTienda(apiTienda.getId())).withRel("eliminar"),
        linkTo(methodOn(ApiTiendaControllerV2.class).patchApiTienda(apiTienda.getId(), apiTienda)).withRel("actualizar-apiTienda")
    );
  }
}

