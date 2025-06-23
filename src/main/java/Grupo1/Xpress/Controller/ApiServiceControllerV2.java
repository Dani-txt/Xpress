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

import Grupo1.Xpress.Modelo.ApiService;
import Grupo1.Xpress.Service.ApiServiceService;
import Grupo1.Xpress.assemblers.ApiServiceModelAssembler;
import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/api/v2/apiServices")
public class ApiServiceControllerV2 {

    @Autowired
    public ApiServiceService apiServiceService;

    @Autowired
    public ApiServiceModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama todos las api de tiendas", description = "Esta api llama todos las api de tiendas")
    public ResponseEntity<CollectionModel<EntityModel<ApiService>>> getAllApiServices(){
        List<EntityModel<ApiService>> apiservices = apiServiceService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (apiservices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
        apiservices,
        linkTo(methodOn(ApiServiceControllerV2.class).getAllApiServices()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama todos a una api por su id", description = "Esta api llama todos a una api por su id")
    public ResponseEntity<EntityModel<ApiService>> getApiServiceById(@PathVariable Long id) {
        ApiService apiService = apiServiceService.findById(id);
        if (apiService == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(apiService));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api crea una nueva ap", description = "Esta api crea una nueva api de tienda")
    public ResponseEntity<EntityModel<ApiService>> createApiService(@RequestBody ApiService apiService) {
        ApiService newApiService = apiServiceService.save(apiService);
            return ResponseEntity
            .created(linkTo(methodOn(ApiServiceControllerV2.class).getApiServiceById(newApiService.getId())).toUri())
            .body(assembler.toModel(newApiService));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api actualiza una nueva api por su id", description = "Esta api actualiza una nueva api por su id")
    public ResponseEntity<EntityModel<ApiService>> updateApiService(@PathVariable Long id, @RequestBody ApiService apiService) {
        apiService.setId(id);
        ApiService updatedApiService = apiServiceService.save(apiService);
        return ResponseEntity.ok(assembler.toModel(updatedApiService));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api edita una nueva api por su id", description = "Esta api edita una nueva api por su id")
    public ResponseEntity<EntityModel<ApiService>> patchApiService(@PathVariable Long id, @RequestBody ApiService apiService) {
        ApiService updatedApiService = apiServiceService.patchApiService(id, apiService);
        if (updatedApiService == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedApiService));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api elimina una nueva api por su id", description = "Esta api elimina una nueva api por su id")
    public ResponseEntity<Void> deleteApiService(@PathVariable Long id) {
        ApiService apiService = apiServiceService.findById(id);
        if (apiService == null) {
            return ResponseEntity.notFound().build();
    }
        apiServiceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}