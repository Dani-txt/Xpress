package Grupo1.Xpress.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import Grupo1.Xpress.Controller.ApiServiceControllerV2;
import Grupo1.Xpress.Modelo.ApiService;


@Component
public class ApiServiceModelAssembler implements RepresentationModelAssembler<ApiService, EntityModel<ApiService>> {

  @SuppressWarnings("null")

  @Override
  public EntityModel<ApiService> toModel(ApiService apiService) {
    return EntityModel.of(apiService,
        linkTo(methodOn(ApiServiceControllerV2.class).getApiServiceById(apiService.getId())).withSelfRel(),
        linkTo(methodOn(ApiServiceControllerV2.class).getAllApiServices()).withRel("ApiServices"),
        linkTo(methodOn(ApiServiceControllerV2.class).updateApiService(apiService.getId(), apiService)).withRel("actualizar"),
        linkTo(methodOn(ApiServiceControllerV2.class).deleteApiService(apiService.getId())).withRel("eliminar"),
        linkTo(methodOn(ApiServiceControllerV2.class).patchApiService(apiService.getId(), apiService)).withRel("actualizar-ApiService")
    );
  }
}

