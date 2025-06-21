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

import Grupo1.Xpress.Modelo.Marca;
import Grupo1.Xpress.Service.MarcaService;
import Grupo1.Xpress.assemblers.MarcaModelAssembler;

@RestController
@RequestMapping("/api/v2/marcas")
public class MarcaControllerV2 {
    @Autowired
    public MarcaService marcaService;

    @Autowired
    public MarcaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Marca>>> getAllMarca(){
        List<EntityModel<Marca>> marcas = marcaService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
        marcas,
        linkTo(methodOn(MarcaControllerV2.class).getAllMarca()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Marca>> getMarcaById(@PathVariable Long id) {
        Marca marca = marcaService.findById(id);
        if (marca == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(marca));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Marca>> createCategoriaProducto(@RequestBody Marca marca) {
        Marca newMarca = marcaService.save(marca);
            return ResponseEntity
            .created(linkTo(methodOn(MarcaControllerV2.class).getMarcaById(newMarca.getId())).toUri())
            .body(assembler.toModel(newMarca));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Marca>> updateMarca(@PathVariable Long id, @RequestBody Marca marca) {
        marca.setId(id);
        Marca updatedMarca = marcaService.save(marca);
        return ResponseEntity.ok(assembler.toModel(updatedMarca));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Marca>> patchMarca(@PathVariable Long id, @RequestBody Marca marca) {
        Marca updatedMarca = marcaService.patchMarca(id, marca);
        if (updatedMarca == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedMarca));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteMarca(@PathVariable Long id) {
        Marca marca = marcaService.findById(id);
        if (marca == null) {
            return ResponseEntity.notFound().build();
    }
        marcaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
