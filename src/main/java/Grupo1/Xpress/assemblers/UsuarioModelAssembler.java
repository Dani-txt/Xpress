package Grupo1.Xpress.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import Grupo1.Xpress.Controller.UsuarioControllerV2;
import Grupo1.Xpress.Modelo.Usuario;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>>{
    
    @SuppressWarnings("null")

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
            linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuario.getId())).withSelfRel(),
            linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("Todos los usuarios"),
            linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(usuario.getId(), usuario)).withRel("actualizar usuario"),
            linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(usuario.getId())).withRel("eliminar usuario"),
            linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(usuario.getId(), usuario)).withRel("editar usuario")
    );
  }
}
