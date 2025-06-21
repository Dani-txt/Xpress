package Grupo1.Xpress.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import Grupo1.Xpress.Controller.RolUsuarioControllerV2;
import Grupo1.Xpress.Modelo.RolUsuario;

@Component
public class RolUsuarioModelAssembler implements RepresentationModelAssembler<RolUsuario, EntityModel<RolUsuario>>{
  @SuppressWarnings("null")

  @Override
  public EntityModel<RolUsuario> toModel(RolUsuario rolUsuario) {
    return EntityModel.of(rolUsuario,
      linkTo(methodOn(RolUsuarioControllerV2.class).getRolUsuarioById(rolUsuario.getId())).withSelfRel(),
      linkTo(methodOn(RolUsuarioControllerV2.class).getAllRolUsuario()).withRel("Todos los Roles de usuarios"),
      linkTo(methodOn(RolUsuarioControllerV2.class).updateRolUsuario(rolUsuario.getId(), rolUsuario)).withRel("actualizar roles de los usuarios"),
      linkTo(methodOn(RolUsuarioControllerV2.class).deleteRolUsuario(rolUsuario.getId())).withRel("eliminar roles de los usuarios"),
      linkTo(methodOn(RolUsuarioControllerV2.class).patchRolUsuario(rolUsuario.getId(), rolUsuario)).withRel("editar roles de los usuarios")
    );
  }
}