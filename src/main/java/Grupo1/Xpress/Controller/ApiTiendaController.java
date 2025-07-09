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

import Grupo1.Xpress.Modelo.ApiTienda;
import Grupo1.Xpress.Service.ApiTiendaService;

@RestController
@RequestMapping("/api/v1/apiservices")
public class ApiTiendaController {
//Inyeccion de dependencias de apiTienda
    @Autowired
    private ApiTiendaService apiTiendaService;

//Obtener todas las ApiService
    @GetMapping
    public ResponseEntity<List<ApiTienda>> listar(){
        List<ApiTienda> apiTienda = apiTiendaService.findAll();
        if (apiTienda.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(apiTienda);
    }

//Obtener apiService x id
    @GetMapping("/{id}")
    public ResponseEntity<ApiTienda> buscar(@PathVariable Long id){
        ApiTienda apiTienda = apiTiendaService.findById(id);
        if(apiTienda==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(apiTienda);
    }

//Agregar una nueva api
    @PostMapping
    public ResponseEntity<ApiTienda>guardarApi(@RequestBody ApiTienda apiTienda){
        ApiTienda apiTiendaNueva = apiTiendaService.save(apiTienda);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiTiendaNueva);
    }
        
//Actualizar una api
    @PutMapping("/{id}")
    public ResponseEntity<ApiTienda> actualizarApi (@PathVariable Long id, @RequestBody ApiTienda apiTienda){
        try {
            apiTiendaService.actualizarApi(id,apiTienda);
            return ResponseEntity.ok(apiTienda);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//Editar una api
    @PatchMapping("/{id}")
    public ResponseEntity<ApiTienda> patchApiTienda(@PathVariable Long id, @RequestBody ApiTienda apiTiendaParcial){
        try {
            ApiTienda apiTiendaActualizado = apiTiendaService.patchApiTienda(id, apiTiendaParcial);
            return ResponseEntity.ok(apiTiendaActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

//Eliminar una api
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        try {
            apiTiendaService.delete(id);
                return ResponseEntity.noContent().build();
            }catch(Exception e){
                return ResponseEntity.notFound().build();
        }
    }
}