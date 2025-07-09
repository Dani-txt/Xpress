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

import Grupo1.Xpress.Modelo.ApiTienda;
import Grupo1.Xpress.Service.ApiTiendaService;
import Grupo1.Xpress.assemblers.ApiTiendaModelAssembler;
import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/api/v2/apitiendas")
public class ApiTiendaControllerV2 {

    @Autowired
    public ApiTiendaService apiTiendaService;

    @Autowired
    public ApiTiendaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama todos las api de tiendas", description = "Esta api llama todos las api de tiendas")
    public ResponseEntity<CollectionModel<EntityModel<ApiTienda>>> getAllApiTiendas(){
        List<EntityModel<ApiTienda>> apiTienda = apiTiendaService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (apiTienda.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
        apiTienda,
        linkTo(methodOn(ApiTiendaControllerV2.class).getAllApiTiendas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama todos a una api por su id", description = "Esta api llama todos a una api por su id")
    public ResponseEntity<EntityModel<ApiTienda>> getApiTiendaById(@PathVariable Long id) {
        ApiTienda apiTienda = apiTiendaService.findById(id);
        if (apiTienda == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(apiTienda));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api crea una nueva ap", description = "Esta api crea una nueva api de tienda")
    public ResponseEntity<EntityModel<ApiTienda>> createApiTienda(@RequestBody ApiTienda apiTienda) {
        ApiTienda newApiTienda = apiTiendaService.save(apiTienda);
            return ResponseEntity
            .created(linkTo(methodOn(ApiTiendaControllerV2.class).getApiTiendaById(newApiTienda.getId())).toUri())
            .body(assembler.toModel(newApiTienda));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api actualiza una nueva api por su id", description = "Esta api actualiza una nueva api por su id")
    public ResponseEntity<EntityModel<ApiTienda>> updateApiTienda(@PathVariable Long id, @RequestBody ApiTienda apiTienda) {
        apiTienda.setId(id);
        ApiTienda updatedApiTienda = apiTiendaService.save(apiTienda);
        return ResponseEntity.ok(assembler.toModel(updatedApiTienda));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api edita una nueva api por su id", description = "Esta api edita una nueva api por su id")
    public ResponseEntity<EntityModel<ApiTienda>> patchApiTienda(@PathVariable Long id, @RequestBody ApiTienda apiTienda) {
        ApiTienda updatedApiTienda = apiTiendaService.patchApiTienda(id, apiTienda);
        if (updatedApiTienda == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedApiTienda));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api elimina una nueva api por su id", description = "Esta api elimina una nueva api por su id")
    public ResponseEntity<Void> deleteApiTienda(@PathVariable Long id) {
        ApiTienda apiTienda = apiTiendaService.findById(id);
        if (apiTienda == null) {
            return ResponseEntity.notFound().build();
    }
        apiTiendaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}