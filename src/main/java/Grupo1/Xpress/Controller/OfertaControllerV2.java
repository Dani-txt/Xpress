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

import Grupo1.Xpress.Modelo.Oferta;
import Grupo1.Xpress.Service.OfertaService;
import Grupo1.Xpress.assemblers.OfertaModelAssembler;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v2/ofertas")
public class OfertaControllerV2 {
    @Autowired
    public OfertaService ofertaService;

    @Autowired
    public OfertaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api encuentra todas las ofertas", description = "Esta api encuentra todas las ofertas")
    public ResponseEntity<CollectionModel<EntityModel<Oferta>>> getAllOferta(){
        List<EntityModel<Oferta>> ofertas = ofertaService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (ofertas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
        ofertas,
        linkTo(methodOn(MarcaControllerV2.class).getAllMarca()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api encuentra ofertas por id", description = "Esta api encuentra ofertas por id")
    public ResponseEntity<EntityModel<Oferta>> getOfertaById(@PathVariable Long id) {
        Oferta oferta = ofertaService.findById(id);
        if (oferta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(oferta));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api crea ofertas", description = "Esta api crea ofertas")
    public ResponseEntity<EntityModel<Oferta>> createOferta(@RequestBody Oferta oferta) {
        Oferta newOferta = ofertaService.save(oferta);
            return ResponseEntity
            .created(linkTo(methodOn(OfertaControllerV2.class).getOfertaById(newOferta.getId())).toUri())
            .body(assembler.toModel(newOferta));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api actualiza las ofertas por el id", description = "Esta api actualiza las ofertas por id")
    public ResponseEntity<EntityModel<Oferta>> updateOferta(@PathVariable Long id, @RequestBody Oferta oferta) {
        oferta.setId(id);
        Oferta updatedOferta = ofertaService.save(oferta);
        return ResponseEntity.ok(assembler.toModel(updatedOferta));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api edita las ofertas por el id", description = "Esta api edita las ofertas por id")
    public ResponseEntity<EntityModel<Oferta>> patchOferta(@PathVariable Long id, @RequestBody Oferta oferta) {
        Oferta updatedOferta = ofertaService.patchOferta(id, oferta);
        if (updatedOferta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedOferta));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api elimina las ofertas por el id", description = "Esta api elimina las ofertas por id")
    public ResponseEntity<Void> deleteOferta(@PathVariable Long id) {
        Oferta oferta = ofertaService.findById(id);
        if (oferta == null) {
            return ResponseEntity.notFound().build();
    }
        ofertaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}