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

import Grupo1.Xpress.Modelo.CategoriaProducto;
import Grupo1.Xpress.Service.CategoriaProductoService;

@RestController
@RequestMapping("/api/v1/categoriasproductos")
public class CategoriaProductoController {
//Inyeccion de dependencia de categoria producto
    @Autowired
    private CategoriaProductoService categoriaProductoService;

//Obtener todas las categorias de producto
    @GetMapping
    public ResponseEntity<List<CategoriaProducto>> lista(){
        List<CategoriaProducto> categoriaProductos = categoriaProductoService.findAll();
        if(categoriaProductos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoriaProductos);
    }

//Obtener la categoria por su id
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProducto> buscar(@PathVariable Long id){
        CategoriaProducto categoria = categoriaProductoService.findById(id);
        if(categoria == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoria);
    }

//Agregar una categoria
    @PostMapping
    public ResponseEntity<CategoriaProducto>guardar(@RequestBody CategoriaProducto categoriaProducto){
        CategoriaProducto categoriaProductoNuevo = categoriaProductoService.save(categoriaProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaProductoNuevo);
    }

//Actualizar una categoria
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProducto> actualizar(@PathVariable Long id, @RequestBody CategoriaProducto categoriaProducto){
        try {
            categoriaProductoService.actualizar(id, categoriaProducto);
            return ResponseEntity.ok(categoriaProducto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//Editar una categoria
    @PatchMapping("/{id}")
    public ResponseEntity<CategoriaProducto> patchCategoriaProducto(@PathVariable Long id, @RequestBody CategoriaProducto categoriaProductoParcial){
        try {
            CategoriaProducto categoriaProductoActualizado = categoriaProductoService.patchCategoriaProducto(id, categoriaProductoParcial);
            return ResponseEntity.ok(categoriaProductoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

//Eliminar una categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        try{
            categoriaProductoService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
    