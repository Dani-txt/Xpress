package Grupo1.Xpress.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.Xpress.Modelo.ApiTienda;
import Grupo1.Xpress.Modelo.Producto;
import Grupo1.Xpress.Repository.ApiTiendaRepository;
import Grupo1.Xpress.Repository.ProductoRepository;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class ApiTiendaService {

    @Autowired
    private ApiTiendaRepository apiTiendaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<ApiTienda> findAll(){
        return apiTiendaRepository.findAll();
    }

    public ApiTienda findById(Long id){
        return apiTiendaRepository.findById(id).orElse(null);
    }

    public ApiTienda findBynombre(String nombre){
        return apiTiendaRepository.findByNombre(nombre);
    }

    public ApiTienda save(ApiTienda apiTienda){
        return apiTiendaRepository.save(apiTienda);
    }

    public void delete(Long idApiTienda){
        apiTiendaRepository.deleteById(idApiTienda);
    }

    public ApiTienda actualizarApi(Long id, ApiTienda apiTienda){
        ApiTienda apiActualizada = apiTiendaRepository.findById(id).orElse(null);
        if(apiActualizada != null){
            apiActualizada.setNombre(apiTienda.getNombre());
            apiActualizada.setUrl(apiTienda.getUrl());
            return apiTiendaRepository.save(apiActualizada);
        }else {
            return null;
        }
    }

    public ApiTienda patchApiTienda(Long id, ApiTienda apiTiendaParcial){
    Optional<ApiTienda> apiTiendaOptional = apiTiendaRepository.findById(id);
        if (apiTiendaOptional.isPresent()) {
            ApiTienda apiTiendaToUpdate = apiTiendaOptional.get();
            if (apiTiendaParcial.getNombre() != null) {
                apiTiendaToUpdate.setNombre(apiTiendaParcial.getNombre());
                }
                return apiTiendaRepository.save(apiTiendaToUpdate);
            }else{
                return null;
            }
    }

    public void deleteById(Long id) {
        ApiTienda apiTienda = apiTiendaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));

        List<Producto> productos = productoRepository.findByApiTienda(apiTienda);

        for (Producto producto : productos) {
            productoRepository.delete(producto);
        }

        apiTiendaRepository.delete(apiTienda);
    }

}