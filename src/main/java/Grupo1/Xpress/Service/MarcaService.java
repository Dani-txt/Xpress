package Grupo1.Xpress.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.Xpress.Modelo.Marca;
import Grupo1.Xpress.Modelo.Producto;
import Grupo1.Xpress.Repository.MarcaRepository;
import Grupo1.Xpress.Repository.ProductoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    //lista de todas las categorías
    public List<Marca> findAll(){
        return marcaRepository.findAll();
    }

    //busqueda x id
    public Marca findById(Long id){
        return marcaRepository.findById(id).orElse(null);
    }

    //busqueda x nombre
    public Marca findByNombre(String nombre){
        return marcaRepository.findByNombre(nombre);
    }

    //agregar categoria
    public Marca save(Marca marca){
        return marcaRepository.save(marca);
    }


    public Marca actualizar(Long id, Marca marca){
        Marca mar = marcaRepository.findById(id).orElse(null);
        if (mar != null) {
            mar.setNombre(mar.getNombre());
            return marcaRepository.save(mar);
        } else{
            return null;
        }
    }

    public Marca patchMarca(Long id, Marca marcaParcial){
    Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if (marcaOptional.isPresent()) {
            Marca marcaToUpdate = marcaOptional.get();
            if (marcaParcial.getNombre() != null) {
                marcaToUpdate.setNombre(marcaParcial.getNombre());
                }
                return marcaRepository.save(marcaToUpdate);
            }else{
                return null;
            }
    }

    //eliminar marca
    public void deleteById(Long id) {
        Marca marca = marcaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        List<Producto> productos = productoRepository.findByMarca(marca);

        for (Producto producto : productos) {
            productoRepository.delete(producto);
        }

        marcaRepository.delete(marca);
    }
}
