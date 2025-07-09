package Grupo1.Xpress.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import Grupo1.Xpress.Modelo.ApiTienda;
import Grupo1.Xpress.Modelo.CategoriaProducto;
import Grupo1.Xpress.Modelo.Marca;
import Grupo1.Xpress.Modelo.Oferta;
import Grupo1.Xpress.Modelo.Producto;
import Grupo1.Xpress.Repository.CategoriaProductoRepository;
import Grupo1.Xpress.Repository.ProductoRepository;
import Grupo1.Xpress.Service.ProductoService;

@ActiveProfiles("test")
@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockBean
    private ProductoRepository productoRepository;

    @MockBean
    private CategoriaProductoRepository categoriaProductoRepository;

    private Producto createProducto() {
        return new Producto(1L, "Microondas", true, 15000, "baratin baraton",
                new CategoriaProducto(1L, "Electrodomésticos"),
                new ApiTienda(1L, "www.falabella.com", "falabella"),
                new Marca(1L, "Samsung"),
                new Oferta(1L, 20.0));
    }

    @Test
    public void testFindAll() {
        when(productoRepository.findAll()).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.findAll();
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindById() {
        when(productoRepository.findById(1L)).thenReturn(createProducto());
        Producto producto = productoService.findById(1L);
        assertNotNull(producto);
        assertEquals(1L, producto.getId());
    }

    @Test
    public void testFindByNombre() {
        when(productoRepository.findByNombre("Microondas")).thenReturn(createProducto());
        Producto producto = productoService.findByNombre("Microondas");
        assertNotNull(producto);
        assertEquals("Microondas", producto.getNombre());
    }

    @Test
    public void testFindByDisponibilidad() {
        when(productoRepository.findByDisponibilidad(true)).thenReturn(List.of(createProducto()));
        List<Producto> disponibles = productoService.findByDisponibilidad(true);
        assertNotNull(disponibles);
        assertEquals(1, disponibles.size());
        assertTrue(disponibles.get(0).getDisponibilidad());
    }

    @Test
    public void testFindByCategoriaProducto() {
        CategoriaProducto categoria = new CategoriaProducto(1L, "Electrodomésticos");
        when(categoriaProductoRepository.findByNombre("Electrodomésticos")).thenReturn(categoria);
        when(productoRepository.findByCategoriaProducto(categoria)).thenReturn(List.of(createProducto()));

        List<Producto> productos = productoService.findByCategoriaProducto("Electrodomésticos");
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindByProductoMenor() {
        when(productoRepository.findByProductoMenor(30000)).thenReturn(List.of(createProducto()));
        List<Producto> productosBaratos = productoService.findByProductoMenor(30000);
        assertNotNull(productosBaratos);
        assertEquals(1, productosBaratos.size());
    }

    @Test
    public void testFindByProductoMenorMarca() {
        when(productoRepository.findByProductoMenorMarca(30000, "Samsung")).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.findByProductoMenorMarca(30000, "Samsung");
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindByProductoMayor() {
        when(productoRepository.findByProductoMayor(10000)).thenReturn(List.of(createProducto()));
        List<Producto> productosCaros = productoService.findByProductoMayor(10000);
        assertNotNull(productosCaros);
        assertEquals(1, productosCaros.size());
    }

    @Test
    public void testFindByProductoMayorMarca() {
        when(productoRepository.findByProductoMayorMarca(10000, "Samsung")).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.findByProductoMayorMarca(10000, "Samsung");
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindByProductoMarca() {
        when(productoRepository.findByProductoMarca("Samsung")).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.findByProductoMarca("Samsung");
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindByProductoMarcaDescuento() {
        when(productoRepository.findByDescuentoMarca("Samsung", 20.0, true)).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.findByProductoMarcaDescuento("Samsung", 20.0, true);
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindByProductoMarcaDescuentoTienda() {
        when(productoRepository.findByDescuentoMarcaTienda("Samsung", 20.0, true, "falabella")).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.findByProductoMarcaDescuentoTienda("Samsung", 20.0, true, "falabella");
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindByProductoPrecioMayorDescuentoMarca() {
        when(productoRepository.findByPrecioDescuentoMarcaMayor(10000, "Samsung", 20.0, true)).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.findByProductoPrecioMayorDescuentoMarca(10000, "Samsung", 20.0, true);
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindByProductoPrecioMenorDescuentoMarca() {
        when(productoRepository.findByPrecioDescuentoMarcaMenor(30000, "Samsung", 20.0, true)).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.findByProductoPrecioMenorDescuentoMarca(30000, "Samsung", 20.0, true);
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindByDescuentoMarca() {
        when(productoRepository.findByDescuentoMarca("Samsung", 20.0, true)).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.findByDescuentoMarca("Samsung", 20.0, true);
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindByDescuentoMarcaTienda() {
        when(productoRepository.findByDescuentoMarcaTienda("Samsung", 20.0, true, "falabella")).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.findByDescuentoMarcaTienda("Samsung", 20.0, true, "falabella");
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testActualizarProducto() {
        Producto existente = createProducto();
        Producto actualizado = createProducto();
        actualizado.setNombre("Nuevo Producto");

        when(productoRepository.findById(1L)).thenReturn(existente);
        when(productoRepository.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));

        Producto result = productoService.actualizar(1L, actualizado);
        assertNotNull(result);
        assertEquals("Nuevo Producto", result.getNombre());
    }

    @Test
    public void testSave() {
        Producto producto = createProducto();
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto saved = productoService.save(producto);
        assertNotNull(saved);
        assertEquals("Microondas", saved.getNombre());
    }

    @Test
    public void testPatchProducto() {
        Producto existente = createProducto();
        Producto patchData = new Producto();
        patchData.setNombre("Producto actualizado");

        when(productoRepository.findById(1L)).thenReturn(existente);
        when(productoRepository.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));

        Producto actualizado = productoService.patchProducto(1L, patchData);
        assertNotNull(actualizado);
        assertEquals("Producto actualizado", actualizado.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(productoRepository).deleteById(1L);
        productoService.deleteById(1L);
        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByCategoriaProductoNotFound() {
        when(categoriaProductoRepository.findByNombre("No existe")).thenReturn(null);
        List<Producto> productos = productoService.findByCategoriaProducto("No existe");
        assertNull(productos);
    }

    @Test
    public void testFindByProductoMenorNotFound() {
        when(productoRepository.findByProductoMenor(1000)).thenReturn(null);
        List<Producto> productos = productoService.findByProductoMenor(1000);
        assertNull(productos);
    }
}