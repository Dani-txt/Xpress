package Grupo1.Xpress.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
//Inyeccion de dependencias de Usuario
    @Autowired
    private UsuarioService usuarioService;

//Obtener todos los usuario
    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        List<Usuario> usuarios = usuarioService.findAll();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

//Obtener usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario>buscar(@PathVariable Long id){
        Usuario usuario = usuarioService.findById(id);
        if(usuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
            
        }
    
//Obtener usuario por rol
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<Usuario>> buscarPorRol(@PathVariable Long rolId) {
        List<Usuario> usuarios = usuarioService.findByRolUsuarioId(rolId);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

//Agregar un usuario
    @PostMapping
    public ResponseEntity<Usuario>guardar(@RequestBody Usuario usuario) {
        Usuario usuarioNuevo =  usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }

//Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario){
        try{
            usuarioService.actualizar(id, usuario);
            return ResponseEntity.ok(usuario);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    
//Editar usuario
    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> patchPaciente(@PathVariable Long id, @RequestBody Usuario usurioParcial){
        try{
            Usuario usuarioActualizado = usuarioService.patchUsuario(id, usurioParcial);
            return ResponseEntity.ok(usuarioActualizado);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

//Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>eliminar(@PathVariable Long id){
        try{
            usuarioService.deleteById(id);
                return ResponseEntity.noContent().build();
        } catch(Exception e){
                return ResponseEntity.notFound().build();
        }
    }


}