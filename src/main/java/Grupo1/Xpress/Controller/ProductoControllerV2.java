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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Grupo1.Xpress.Modelo.Producto;
import Grupo1.Xpress.Service.ProductoService;
import Grupo1.Xpress.assemblers.ProductoModelAssembler;

@RestController
@RequestMapping("/api/v2/productos")
public class ProductoControllerV2 {

    @Autowired
    public ProductoService productoService;

    @Autowired
    public ProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getAllProductos(){
        List<EntityModel<Producto>> productos = productoService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
        productos,
        linkTo(methodOn(ApiServiceControllerV2.class).getAllApiServices()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(producto));
    }

    @GetMapping(value = "/preciomayor", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosCaros(@RequestParam("preciomayor") Integer preciomayor) {
        List<EntityModel<Producto>> productos = productoService.obtenerProductoMayor(preciomayor).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value = "/preciosmenores", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosBaratos(@RequestParam("preciomenor") Integer preciomenor) {
        List<EntityModel<Producto>> productos = productoService.obtenerProductoMenor(preciomenor).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value = "/marcas", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getByMarca(@RequestParam("marca") String marca) {
        List<EntityModel<Producto>> productos = productoService.obtenerProductoPorMarca(marca).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value = "/categoria/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getByCategoria(@PathVariable String nombre) {
        List<EntityModel<Producto>> productos = productoService.findByCategoriaProducto(nombre).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value = "/disponibles/{disponibilidad}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getByDisponibilidad(@PathVariable Boolean disponibilidad) {
        List<EntityModel<Producto>> productos = productoService.findByDisponibilidad(disponibilidad).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.save(producto);
            return ResponseEntity
            .created(linkTo(methodOn(ApiServiceControllerV2.class).getApiServiceById(newProducto.getId())).toUri())
            .body(assembler.toModel(newProducto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        Producto updatedProducto = productoService.save(producto);
        return ResponseEntity.ok(assembler.toModel(updatedProducto));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> patchProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto updatedProducto = productoService.patchProducto(id, producto);
        if (updatedProducto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedProducto));
    }



    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
    }
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


