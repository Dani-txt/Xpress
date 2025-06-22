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

import Grupo1.Xpress.Modelo.RolUsuario;
import Grupo1.Xpress.Service.RolUsuarioService;
import Grupo1.Xpress.assemblers.RolUsuarioModelAssembler;

@RestController
@RequestMapping("/api/v2/rolesusuarios")
public class RolUsuarioControllerV2 {
    @Autowired
    public RolUsuarioService rolUsuarioService;

    @Autowired
    public RolUsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<RolUsuario>>> getAllRolUsuario(){
        List<EntityModel<RolUsuario>> rolUsuario = rolUsuarioService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (rolUsuario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
        rolUsuario,
        linkTo(methodOn(RolUsuarioControllerV2.class).getAllRolUsuario()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RolUsuario>> getRolUsuarioById(@PathVariable Long id) {
        RolUsuario rolUsuario = rolUsuarioService.findById(id);
        if (rolUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(rolUsuario));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RolUsuario>> createRolUsuario(@PathVariable Long id, @RequestBody RolUsuario rolUsuario) {
        RolUsuario newRolUsuario = rolUsuarioService.save(rolUsuario);
            return ResponseEntity
            .created(linkTo(methodOn(RolUsuarioControllerV2.class).getRolUsuarioById(newRolUsuario.getId())).toUri())
            .body(assembler.toModel(newRolUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RolUsuario>> updateRolUsuario(@PathVariable Long id, @RequestBody RolUsuario rolUsuario) {
        rolUsuario.setId(id);
        RolUsuario updatedRolUsuario = rolUsuarioService.save(rolUsuario);
        return ResponseEntity.ok(assembler.toModel(updatedRolUsuario));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RolUsuario>> patchRolUsuario(@PathVariable Long id, @RequestBody RolUsuario rolUsuario) {
        RolUsuario updatedRolUsuario = rolUsuarioService.patchRolUsuario(id, rolUsuario);
        if (updatedRolUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedRolUsuario));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteRolUsuario(@PathVariable Long id) {
        RolUsuario rolUsuario = rolUsuarioService.findById(id);
        if (rolUsuario == null) {
            return ResponseEntity.notFound().build();
    }
        rolUsuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

