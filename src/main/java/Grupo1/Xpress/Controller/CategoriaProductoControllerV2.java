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

import Grupo1.Xpress.Modelo.CategoriaProducto;
import Grupo1.Xpress.Service.CategoriaProductoService;
import Grupo1.Xpress.assemblers.CategoriaProductoModelAssembler;

@RestController
@RequestMapping("/api/v2/categoriasproductos")
public class CategoriaProductoControllerV2 {
    @Autowired
    public CategoriaProductoService categoriaProductoService;

    @Autowired
    public CategoriaProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<CategoriaProducto>>> getAllCategoriaProducto(){
        List<EntityModel<CategoriaProducto>> categoriaProductos = categoriaProductoService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (categoriaProductos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
        categoriaProductos,
        linkTo(methodOn(CategoriaProductoControllerV2.class).getAllCategoriaProducto()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaProducto>> getCategoriaProductoById(@PathVariable Long id) {
        CategoriaProducto categoriaProducto = categoriaProductoService.findById(id);
        if (categoriaProducto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(categoriaProducto));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaProducto>> createCategoriaProducto(@RequestBody CategoriaProducto categoriaProducto) {
        CategoriaProducto newCategoriaProducto = categoriaProductoService.save(categoriaProducto);
            return ResponseEntity
            .created(linkTo(methodOn(CategoriaProductoControllerV2.class).getCategoriaProductoById(newCategoriaProducto.getId())).toUri())
            .body(assembler.toModel(newCategoriaProducto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaProducto>> updateCategoriaProducto(@PathVariable Long id, @RequestBody CategoriaProducto categoriaProducto) {
        categoriaProducto.setId(id);
        CategoriaProducto updatedCategoriaProducto = categoriaProductoService.save(categoriaProducto);
        return ResponseEntity.ok(assembler.toModel(updatedCategoriaProducto));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaProducto>> patchCategoriaProducto(@PathVariable Long id, @RequestBody CategoriaProducto categoriaProducto) {
        CategoriaProducto updatedCategoriaProducto = categoriaProductoService.patchCategoriaProducto(id, categoriaProducto);
        if (updatedCategoriaProducto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedCategoriaProducto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteCategoriaProducto(@PathVariable Long id) {
        CategoriaProducto categoriaProducto = categoriaProductoService.findById(id);
        if (categoriaProducto == null) {
            return ResponseEntity.notFound().build();
    }
        categoriaProductoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
