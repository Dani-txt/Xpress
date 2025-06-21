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
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

//busqueda de paciente por id
    public Producto findById(Long id){
        return productoRepository.findById(id).orElse(null);
    }

//busqueda por nombre
    public Producto findByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

//diponibilidad
    public List<Producto> findByDisponibilidad(Boolean disponibilidad){
        return productoRepository.findByDisponibilidad(disponibilidad);
    }

//Por categoria de producto
    public List<Producto> findByCategoriaProducto (String nombre){
        CategoriaProducto categoria = categoriaProductoRepository.findByNombre(nombre);
        if(categoria != null){
            return productoRepository.findByCategoriaProducto(categoria);
        }
        return null;
    }

//Obtener productos por marca
    public List<Producto> obtenerProductoPorMarca(String nombreMarca){
        List<Producto> productoMarca = productoRepository.findProductoPorMarca(nombreMarca);
        if (productoMarca !=null) {
            return productoRepository.findProductoPorMarca(nombreMarca);
        }
        return null;
    }

//Obtener productos baratos
    public List<Producto> obtenerProductoMenor(Integer precioMenor) {
        List<Producto> productoBarato = productoRepository.findProductoMenor(precioMenor);
        if (productoBarato !=null) {
            return productoRepository.findProductoMenor(precioMenor);
        }
        return null;
    }

//Obtener productos caros
    public List<Producto> obtenerProductoMayor(Integer precioMayor){
        List<Producto> productoCaro = productoRepository.findProductoMayor(precioMayor);
        if (productoCaro !=null) {
            return productoRepository.findProductoMayor(precioMayor);
        }
        return null;
    }

//Agregar producto
    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

//Actualizar producto
    public Producto actualizar(Long id, Producto producto){
        Producto productoActualizado = productoRepository.findById(id).orElse(null);
        if (productoActualizado != null) {
            productoActualizado.setNombre(producto.getNombre());
            productoActualizado.setDisponibilidad(producto.getDisponibilidad());
            productoActualizado.setDescripcion(producto.getDescripcion());
            productoActualizado.setPrecio(producto.getPrecio());
            productoActualizado.setCategoriaProducto(producto.getCategoriaProducto());
            productoActualizado.setApiService(producto.getApiService());
            return productoRepository.save(productoActualizado);
        } else{
            return null;
        }
    }

//Editar producto
    public Producto patchProducto(Long id, Producto productoParcial){
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            Producto productoToUpdate = productoOptional.get();
            if (productoParcial.getNombre() != null) {
                productoToUpdate.setNombre(productoParcial.getNombre());
            }
            if (productoParcial.getDisponibilidad() != null) {
                productoToUpdate.setDisponibilidad(productoParcial.getDisponibilidad());
            }
            if (productoParcial.getDescripcion() != null) {
                productoToUpdate.setDescripcion(productoParcial.getDescripcion());
            }
            if (productoParcial.getPrecio() != null) {
                productoToUpdate.setPrecio(productoParcial.getPrecio());
            }
            if (productoParcial.getCategoriaProducto() != null) {
                productoToUpdate.setCategoriaProducto(productoParcial.getCategoriaProducto());
            }
            if (productoParcial.getApiService() != null) {
                productoToUpdate.setApiService(productoParcial.getApiService());
            }
            return productoRepository.save(productoToUpdate);
            } else {
                return null;
            }
    }

    //Eliminar producto
    public void delete(Long id){
        productoRepository.deleteById(id);
    }

}