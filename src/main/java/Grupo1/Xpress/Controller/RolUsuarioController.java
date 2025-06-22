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

import Grupo1.Xpress.Modelo.RolUsuario;
import Grupo1.Xpress.Service.RolUsuarioService;

@RestController
@RequestMapping("/api/v1/rolesusuarios")
public class RolUsuarioController {
//Inyección de dependencias rolUsuario
    @Autowired
    private RolUsuarioService rolUsuarioService;

//Obtener todos los roles
    @GetMapping
    public ResponseEntity<List<RolUsuario>> listar(){
        List<RolUsuario> rolUsuarios = rolUsuarioService.findAll();
        if (rolUsuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rolUsuarios);
    }

//Obtener rol por id
    @GetMapping("/{id}")
    public ResponseEntity<RolUsuario> buscar(@PathVariable Long id){
        RolUsuario rolUsuario = rolUsuarioService.findById(id);
        if(rolUsuario == null){
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.ok(rolUsuario);
        }

//Agregar rol
    @PostMapping
    public ResponseEntity<RolUsuario>guardar(@RequestBody RolUsuario rolUsuario){
        RolUsuario rolUsuarioNuevo = rolUsuarioService.save(rolUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolUsuarioNuevo);
    }

//Actualizar rol
    @PutMapping("/{id}")
    public ResponseEntity<RolUsuario> actualizar(@PathVariable Long id, @RequestBody RolUsuario rolUsuario){
        try {
            rolUsuarioService.actualizar(id, rolUsuario);
            return ResponseEntity.ok(rolUsuario);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
            
        }

//Editar rol
    @PatchMapping("/{id}")
    public ResponseEntity<RolUsuario> patchRolUsuario(@PathVariable Long id, @RequestBody RolUsuario rolUsuario){
        try {
            RolUsuario rolUsuarioActualizado = rolUsuarioService.patchRolUsuario(id, rolUsuario);
            return ResponseEntity.ok(rolUsuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

//Eliminar rol
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        try {
            rolUsuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
