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

import Grupo1.Xpress.Modelo.ApiService;
import Grupo1.Xpress.Service.ApiServiceService;

@RestController
@RequestMapping("/api/v1/apiservices")
public class ApiServiceController {
//Inyeccion de dependencias de apiService
    @Autowired
    private ApiServiceService apiServiceService;

//Obtener todas las ApiService
    @GetMapping
    public ResponseEntity<List<ApiService>> listar(){
        List<ApiService> apiServices = apiServiceService.findAll();
        if (apiServices.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(apiServices);
    }

//Obtener apiService x id
    @GetMapping("/{id}")
    public ResponseEntity<ApiService> buscar(@PathVariable Long id){
        ApiService apiService = apiServiceService.findById(id);
        if(apiService==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(apiService);
    }

//Agregar una nueva api
    @PostMapping
    public ResponseEntity<ApiService>guardar(@RequestBody ApiService apiService){
        ApiService apiServiceNueva = apiServiceService.save(apiService);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiServiceNueva);
    }
        
//Actualizar una api
    @PutMapping("/{id}")
    public ResponseEntity<ApiService> actualizar(@PathVariable Long id, @RequestBody ApiService apiService){
        try {
            apiServiceService.actualizarApi(id,apiService);
            return ResponseEntity.ok(apiService);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//Editar una api
    @PatchMapping("/{id}")
    public ResponseEntity<ApiService> patchApiService(@PathVariable Long id, @RequestBody ApiService apiServiceParcial){
        try {
            ApiService apiServiceActualizado = apiServiceService.patchApiService(id, apiServiceParcial);
            return ResponseEntity.ok(apiServiceActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

//Eliminar una api
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        try {
            apiServiceService.delete(id);
                return ResponseEntity.noContent().build();
            }catch(Exception e){
                return ResponseEntity.notFound().build();
        }
    }
}