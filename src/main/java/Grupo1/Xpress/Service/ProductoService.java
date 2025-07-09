package Grupo1.Xpress.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.Xpress.Modelo.CategoriaProducto;
import Grupo1.Xpress.Modelo.Favorito;
import Grupo1.Xpress.Modelo.Producto;
import Grupo1.Xpress.Repository.CategoriaProductoRepository;
import Grupo1.Xpress.Repository.FavoritoRepository;
import Grupo1.Xpress.Repository.ProductoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    @Autowired
    private FavoritoRepository favoritoRepository;

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

    //Obtener productos menores al valor otorgado
    public List<Producto> findByProductoMenor(Integer precio) {
        List<Producto> productoBarato = productoRepository.findByProductoMenor(precio);
        if (productoBarato !=null) {
            return productoRepository.findByProductoMenor(precio);
        }
        return null;
    }

//Obtener un producto de menor precio de alguna marca en especifico
    public List<Producto> findByProductoMenorMarca(Integer precio, String nombre){
        List<Producto> productoMayorMarca = productoRepository.findByProductoMenorMarca(precio, nombre);
        if (productoMayorMarca !=null) {
            return productoRepository.findByProductoMenorMarca(precio, nombre);
        }
        return null;
    }

//Obtener productos mayores a precio otorgado
    public List<Producto> findByProductoMayor(Integer precio){
        List<Producto> productoMayor = productoRepository.findByProductoMayor(precio);
        if (productoMayor !=null) {
            return productoRepository.findByProductoMayor(precio);
        }
        return null;
    }

//Obtener un producto de mayor precio de alguna marca en especifico
    public List<Producto> findByProductoMayorMarca(Integer precio, String nombre){
        List<Producto> productoMayorMarca = productoRepository.findByProductoMayorMarca(precio, nombre);
        if (productoMayorMarca !=null) {
            return productoRepository.findByProductoMayorMarca(precio, nombre);
        }
        return null;
    }

    //Obtener productos por marca
    public List<Producto> findByProductoMarca(String nombre){
        List<Producto> productoMarca = productoRepository.findByProductoMarca(nombre);
        if (productoMarca !=null) {
            return productoRepository.findByProductoMarca(nombre);
        }
        return null;
    }

    //Obtener producto por marca, descuento y disponibilidad
    public List<Producto> findByProductoMarcaDescuento(String nombre, Double descuento, Boolean disponibilidad){
        List<Producto> productoMarcaDescuento = productoRepository.findByDescuentoMarca(nombre, descuento, disponibilidad);
        if (productoMarcaDescuento !=null) {
            return productoRepository.findByDescuentoMarca(nombre, descuento, disponibilidad);
        }
        return null;
    }

    //Obtener producto por marca, desceunto, disponibilidad, tienda
    public List<Producto> findByProductoMarcaDescuentoTienda(String nombre, Double descuento, Boolean disponibilidad, String tienda){
        List<Producto> productoMarcaDescuentoTienda = productoRepository.findByDescuentoMarcaTienda(nombre, descuento, disponibilidad, tienda);
        if (productoMarcaDescuentoTienda !=null) {
            return productoRepository.findByDescuentoMarcaTienda(nombre, descuento, disponibilidad, tienda);
        }
        return null;
    }

//Obtener producto disponible de un precio mayor al establecido, que cumpla con la marca y el descuento buscado
    public List<Producto> findByProductoPrecioMayorDescuentoMarca(Integer precio, String nombre, Double descuento, Boolean disponibilidad){
        List<Producto> productoMayor = productoRepository.findByPrecioDescuentoMarcaMayor(precio, nombre, descuento, disponibilidad);
        if (productoMayor !=null) {
            return productoRepository.findByPrecioDescuentoMarcaMayor(precio, nombre, descuento, disponibilidad);
        }
        return null;
    }

//Obtener producto disponible de un precio menor al establecido, que cumpla con la marca, el descuento buscado
    public List<Producto> findByProductoPrecioMenorDescuentoMarca(Integer precio, String nombre, Double descuento, Boolean disponibilidad){
        List<Producto> productoMayor = productoRepository.findByPrecioDescuentoMarcaMenor(precio, nombre, descuento, disponibilidad);
        if (productoMayor !=null) {
            return productoRepository.findByPrecioDescuentoMarcaMenor(precio, nombre, descuento, disponibilidad);
        }
        return null;
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

    public List<Producto> findByDescuentoMarca(String nombreMarca, Double descuento, Boolean disponibilidad){
        List<Producto> productoDisponible = productoRepository.findByDescuentoMarca(nombreMarca, descuento, disponibilidad);
        if (productoDisponible !=null) {
            return productoRepository.findByDescuentoMarca(nombreMarca, descuento, disponibilidad);
        }
    return null;
    }

    public List<Producto> findByDescuentoMarcaTienda(String marca, Double descuento, Boolean disponibilidad, String tienda){
        List<Producto> productodisponible = productoRepository.findByDescuentoMarcaTienda(marca, descuento, disponibilidad, tienda);
        if (productodisponible !=null) {
            return productoRepository.findByDescuentoMarcaTienda(marca, descuento, disponibilidad, tienda);
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
            productoActualizado.setApiTienda(producto.getApiTienda());
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
            if (productoParcial.getApiTienda() != null) {
                productoToUpdate.setApiTienda(productoParcial.getApiTienda());
            }
            return productoRepository.save(productoToUpdate);
        }
        return null;
    }


    public void deleteById(Long id) {
        // 1. Buscar el producto
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // 2. Buscar favoritos que referencien este producto
        List<Favorito> favoritos = favoritoRepository.findByProducto(producto);

        // 3. Eliminar cada favorito
        for (Favorito favorito : favoritos) {
            favoritoRepository.delete(favorito);
        }

        // 4. Eliminar el producto
        productoRepository.delete(producto);
    }


}