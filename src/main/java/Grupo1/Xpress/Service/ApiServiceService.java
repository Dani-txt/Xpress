package Grupo1.Xpress.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.Xpress.Modelo.ApiService;
import Grupo1.Xpress.Repository.ApiServiceRepository;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class ApiServiceService {
    @Autowired
    private ApiServiceRepository apiServiceRepository;

    public List<ApiService> findAll(){
        return apiServiceRepository.findAll();
    }

    public ApiService findById(Long id){
        return apiServiceRepository.findById(id).orElse(null);
    }

    public ApiService findBynombre(String nombre){
        return apiServiceRepository.findByNombre(nombre);
    }

    public ApiService save(ApiService apiService){
        return apiServiceRepository.save(apiService);
    }

    public void delete(Long idApiService){
        apiServiceRepository.deleteById(idApiService);
    }

    public ApiService actualizarApi(Long id, ApiService apiService){
        ApiService apiActualizada = apiServiceRepository.findById(id).orElse(null);
        if(apiActualizada != null){
            apiActualizada.setNombre(apiService.getNombre());
            apiActualizada.setUrl(apiService.getUrl());
            return apiServiceRepository.save(apiActualizada);
        }else {
            return null;
        }
    }

    public ApiService patchApiService(Long id, ApiService apiServiceParcial){
    Optional<ApiService> apiServiceOptional = apiServiceRepository.findById(id);
        if (apiServiceOptional.isPresent()) {
            ApiService apiServiceToUpdate = apiServiceOptional.get();
            if (apiServiceParcial.getNombre() != null) {
                apiServiceToUpdate.setNombre(apiServiceParcial.getNombre());
                }
                return apiServiceRepository.save(apiServiceToUpdate);
            }else{
                return null;
            }
    }

    public void deleteById(Long id) {
        //1ero se busca a la api por su id
        ApiService apiService = apiServiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        //Al ser encontrada se elimina
        apiServiceRepository.delete(apiService);
    }

}