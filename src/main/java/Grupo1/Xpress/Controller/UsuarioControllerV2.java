package Grupo1.Xpress.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Grupo1.Xpress.Modelo.Usuario;
import Grupo1.Xpress.Service.UsuarioService;
import Grupo1.Xpress.assemblers.UsuarioModelAssembler;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("api/v2/usuarios")
public class UsuarioControllerV2 {
    
    @Autowired
    public UsuarioService usuarioService;

    @Autowired
    public UsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api encuentra todos los usuarios", description = "Esta api encuentra todos los usuarios")
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getAllUsuarios(){
        List<EntityModel<Usuario>> usuario = usuarioService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (usuario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
        usuario,
        linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api encuentra ofertas por id", description = "Esta api encuentra ofertas por id")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }



    @GetMapping(value = "/rol/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api encuentra a un usuario por su rol", description = "Esta api encuentra a un usuario por su rol")
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getUsuarioByRol(@PathVariable Long rolId){
        List<Usuario> usuarios = usuarioService.findByRolUsuarioId(rolId);
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<EntityModel<Usuario>> usuariosEncontrados = usuarios.stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        CollectionModel<EntityModel<Usuario>> collectionModel = CollectionModel.of(usuariosEncontrados);

        return ResponseEntity.ok(collectionModel);
    }
    

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api crea usuarios", description = "Esta api crea usuarios")
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@RequestBody Usuario usuario) {
        Usuario newUsuario = usuarioService.save(usuario);
            return ResponseEntity
            .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(newUsuario.getId())).toUri())
            .body(assembler.toModel(newUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api actualiza los usuarios por el id", description = "Esta api actualiza los usuarios por id")
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario updatedUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(assembler.toModel(updatedUsuario));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api edita los usuarios por el id", description = "Esta api edita los usuarios por id")
    public ResponseEntity<EntityModel<Usuario>> patchUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario updatedUsuario = usuarioService.patchUsuario(id, usuario);
        if (updatedUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedUsuario));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api elimina los usuarios por el id", description = "Esta api elimina los usuarios por id")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
    }
        usuarioService.eliminarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }
}
