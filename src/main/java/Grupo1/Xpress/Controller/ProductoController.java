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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Grupo1.Xpress.Modelo.Producto;
import Grupo1.Xpress.Service.ProductoService;


@RestController
@RequestMapping("/api/v/productos")
public class ProductoController {

//Inyeccion de dependencias, creacion de productos
    @Autowired
    private ProductoService productoService;

//Codigo antiguo (No se utilizaba)
    @GetMapping
    public ResponseEntity<List<Producto>> AllProductos (){
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }


//Obtener objeto por su id
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id){
        Producto producto = productoService.findById(id);
        if(producto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

//Obtener objetos por disponibilidad
    @GetMapping("/disponibles/{disponibilidad}")
    public ResponseEntity<List<Producto>> disponibles(@PathVariable Boolean disponibilidad){
        List<Producto> productosDisponibles = productoService.findByDisponibilidad(disponibilidad);
        if(productosDisponibles.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productosDisponibles);
    }

    @GetMapping("/marca")
    public ResponseEntity<List<Producto>> productosPorMarca(@RequestParam("marca") String marca) {
        List<Producto> productoMarca = productoService.obtenerProductoPorMarca(marca);
        if(productoMarca.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productoMarca);
    }

//Obtener productos mas baratos (Dependerá de la Query en el repository)
    @GetMapping("/preciosmenores")
    public ResponseEntity<List<Producto>> ProductoMenor(@RequestParam("preciomenor") Integer preciomenor) {
        List<Producto> productosBaratos = productoService.obtenerProductoMenor(preciomenor);
        if(productosBaratos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productosBaratos);
    }
    
//Obtener los productos mas caros (Depende del Query)
    @GetMapping("/preciosmayores")
    public ResponseEntity<List<Producto>> ProductoMayor(@RequestParam("preciomayor") Integer preciomayor) {
        List<Producto> productosCaros = productoService.obtenerProductoMayor(preciomayor);
        if(productosCaros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productosCaros);
    }
    
//Obtener productos por su categoria
    @GetMapping("/categoria/{nombre}")
    public ResponseEntity<List<Producto>> porCategoria (@PathVariable String nombre){
        List<Producto> categoriaProducto = productoService.findByCategoriaProducto(nombre);
        if(categoriaProducto.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoriaProducto);
    }
    
//Agregar un producto
    @PostMapping
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        Producto productoNuevo = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    }

//Actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto){
        try{
            productoService.actualizar(id, producto);
            return ResponseEntity.ok(producto);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
            }
        }

//Editar un producto
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> patchProducto(@PathVariable Long id, @RequestBody Producto producto){
        try{
            Producto productoActualizado = productoService.patchProducto(id, producto);
            return ResponseEntity.ok(productoActualizado);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

//Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        try {
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
