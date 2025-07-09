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
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v2/productos")
public class ProductoControllerV2 {

    @Autowired
    public ProductoService productoService;

    @Autowired
    public ProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama todos los productos", description = "esta api se encarga de obtener todos los productos")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getAllProductos(){
        List<EntityModel<Producto>> productos = productoService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
        productos,
        linkTo(methodOn(ApiTiendaControllerV2.class).getAllApiTiendas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un producto por su id", description = "esta api se encarga de obtener un producto por su id")
    public ResponseEntity<EntityModel<Producto>> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(producto));
    }

    @GetMapping(value = "/marcas", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un producto por su marca", description = "esta api se encarga de obtener un producto por su marca")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getMarca(@RequestParam("marca") String marca) {
        List<EntityModel<Producto>> productos = productoService.findByProductoMarca(marca).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value = "/preciosmayores", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un producto de precio mayor al ingresado", description = "esta api se encarga de obtener un producto de precio mayor al entregado")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductoMayor(@RequestParam("precio") Integer precio) {
        List<EntityModel<Producto>> productos = productoService.findByProductoMayor(precio).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value="/preciosmayores/marcas", produces=MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un producto de una marca en especifico y de un precio mayor al ingresado", description = "esta api se encarga de obtener un producto de una marca y un valor superior al ingresado")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductoMayorMarca(@RequestParam("precio") Integer precio, @RequestParam("marca")String marca){
        List<EntityModel<Producto>> productos = productoService.findByProductoMayorMarca(precio, marca).stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value ="/preciosmenores", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un producto de precio menor al ingresado", description = "esta api se encarga de obtener un producto de precio menor al entregado")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosBaratos(@RequestParam("precio") Integer precio) {
        List<EntityModel<Producto>> productos = productoService.findByProductoMenor(precio).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value="/preciosmenores/marcas", produces=MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un producto de una marca especifica y de un precio menor al ingresado", description = "esta api se encarga de obtener un producto de una marca y un valor menor al ingresado")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductoMenorMarca(@RequestParam("precio") Integer precio, @RequestParam("marca")String marca){
        List<EntityModel<Producto>> productos = productoService.findByProductoMenorMarca(precio, marca).stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value="/marcas/descuentos", produces=MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un producto de una marca especifica y que cuente con el desceunto entregado", description = "esta api se encarga de obtener un producto de una marca y un descuento ingresado")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getDescuentoMarca(@RequestParam("marca")String marca, @RequestParam("descuento") Double descuento, @RequestParam("disponibilidad")Boolean disponibilidad){
        List<EntityModel<Producto>> productos = productoService.findByDescuentoMarca(marca, descuento, disponibilidad).stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }


    @GetMapping(value="/marcas/descuentos/tiendas", produces=MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un producto de una marca especifica, que cuente con el descuento entregado y esté en una tienda en especifico", description = "Esta api llama a un producto de una marca especifica, que cuente con el descuento entregado y esté en una tienda en especifico")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductoDescuentoMarcaTienda(@RequestParam("marca")String marca, @RequestParam("descuento") Double descuento, @RequestParam("disponibilidad")Boolean disponibilidad, @RequestParam("tienda") String tienda){
        List<EntityModel<Producto>> productos = productoService.findByDescuentoMarcaTienda(marca, descuento, disponibilidad, tienda).stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value="/preciosmayores/marcas/descuentos", produces=MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un producto de valor mayor al ingresado, que sea de una marca en especifico y que cumpla con descuento", description = "Esta api llama a un producto de valor mayor al ingresado, que sea de una marca en especifico y que cumpla con descuento")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductoPrecioMayorDescuentoMarca(@RequestParam("precio") Integer precio, @RequestParam("marca")String marca, @RequestParam("descuento") Double descuento, @RequestParam("disponibilidad")Boolean disponibilidad){
        List<EntityModel<Producto>> productos = productoService.findByProductoPrecioMayorDescuentoMarca(precio, marca, descuento, disponibilidad).stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value="/preciosmenores/marcas/descuentos", produces=MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un producto de valor menor al ingresado, que sea de una marca en especifico y que cumpla con descuento", description = "Esta api llama a un producto de valor menor al ingresado, que sea de una marca en especifico y que cumpla con descuento")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductoPrecioMenorDescuentoMarca(@RequestParam("precio") Integer precio, @RequestParam("marca")String marca, @RequestParam("descuento") Double descuento, @RequestParam("disponibilidad")Boolean disponibilidad){
        List<EntityModel<Producto>> productos = productoService.findByProductoPrecioMenorDescuentoMarca(precio, marca, descuento, disponibilidad).stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value = "/categoria/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un producto por el nombre de su categoria", description = "Esta api llama a un producto por el nombre de su categoria")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getCategoria(@PathVariable String nombre) {
        List<EntityModel<Producto>> productos = productoService.findByCategoriaProducto(nombre).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @GetMapping(value = "/disponibles/{disponibilidad}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un producto por su disponibilidad", description = "Esta api llama a un producto por su disponibilidad")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getDisponibilidad(@PathVariable Boolean disponibilidad) {
        List<EntityModel<Producto>> productos = productoService.findByDisponibilidad(disponibilidad).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(productos));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api agrega un producto", description = "Esta api agrega un producto")
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.save(producto);
            return ResponseEntity
            .created(linkTo(methodOn(ApiTiendaControllerV2.class).getApiTiendaById(newProducto.getId())).toUri())
            .body(assembler.toModel(newProducto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api actualiza un producto", description = "Esta api actualiza un producto")
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        Producto updatedProducto = productoService.save(producto);
        return ResponseEntity.ok(assembler.toModel(updatedProducto));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api edita un producto", description = "Esta api edita un producto")
    public ResponseEntity<EntityModel<Producto>> patchProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto updatedProducto = productoService.patchProducto(id, producto);
        if (updatedProducto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedProducto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api elimina un producto", description = "Esta api elimina un producto")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
    }
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


