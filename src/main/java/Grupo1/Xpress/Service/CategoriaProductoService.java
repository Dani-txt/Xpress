package Grupo1.Xpress.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.Xpress.Modelo.CategoriaProducto;
import Grupo1.Xpress.Modelo.Producto;
import Grupo1.Xpress.Repository.CategoriaProductoRepository;
import Grupo1.Xpress.Repository.ProductoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaProductoService {
    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    //lista de todas las categorías
    public List<CategoriaProducto> findAll(){
        return categoriaProductoRepository.findAll();
    }

    //busqueda x id
    public CategoriaProducto findById(Long id){
        return categoriaProductoRepository.findById(id).orElse(null);
    }

    //busqueda x nombre
    public CategoriaProducto findByNombre(String nombre){
        return categoriaProductoRepository.findByNombre(nombre);
    }

    //agregar categoria
    public CategoriaProducto save(CategoriaProducto categoriaProducto){
        return categoriaProductoRepository.save(categoriaProducto);
    }

    public CategoriaProducto actualizar(Long id, CategoriaProducto categoriaProducto){
        CategoriaProducto cat = categoriaProductoRepository.findById(id).orElse(null);
        if (cat != null) {
            cat.setNombre(categoriaProducto.getNombre());
            return categoriaProductoRepository.save(cat);
        } else{
            return null;
        }
    }

    public CategoriaProducto patchCategoriaProducto(Long id, CategoriaProducto categoriaProductoParcial){
    Optional<CategoriaProducto> categoriaProductoOptional = categoriaProductoRepository.findById(id);
        if (categoriaProductoOptional.isPresent()) {
            CategoriaProducto categoriaProductoToUpdate = categoriaProductoOptional.get();
            if (categoriaProductoParcial.getNombre() != null) {
                categoriaProductoToUpdate.setNombre(categoriaProductoParcial.getNombre());
                }
                return categoriaProductoRepository.save(categoriaProductoToUpdate);
            }else{
                return null;
            }
    }

    //eliminar categoria
    public void deleteById(Long id) {
        // 1. Buscar la categoría
        CategoriaProducto categoria = categoriaProductoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // 2. Buscar productos asociados a esta categoría
        List<Producto> productos = productoRepository.findByCategoriaProducto(categoria);

        // 3. Eliminar cada producto asociado (uno por uno)
        for (Producto producto : productos) {
            productoRepository.delete(producto);
        }

        // 4. Eliminar la categoría después de borrar todos sus productos
        categoriaProductoRepository.delete(categoria);
    }

}
