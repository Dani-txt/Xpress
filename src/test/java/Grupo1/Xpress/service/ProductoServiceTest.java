package Grupo1.Xpress.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import Grupo1.Xpress.Modelo.ApiService;
import Grupo1.Xpress.Modelo.CategoriaProducto;
import Grupo1.Xpress.Modelo.Marca;
import Grupo1.Xpress.Modelo.Oferta;
import Grupo1.Xpress.Modelo.Producto;
import Grupo1.Xpress.Repository.CategoriaProductoRepository;
import Grupo1.Xpress.Repository.ProductoRepository;
import Grupo1.Xpress.Service.ProductoService;

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
                new ApiService(1L, "www.falabella.com", "falabella"),
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
        assertEquals(1L, producto.getClass());
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
    public void testObtenerProductosBaratos() {
        when(productoRepository.findProductoMenor(30000)).thenReturn(List.of(createProducto()));
        List<Producto> productosBaratos = productoService.obtenerProductoMenor(30000);
        assertNotNull(productosBaratos);
        assertEquals(1, productosBaratos.size());
    }

    @Test
    public void testObtenerProductosCaros() {
        when(productoRepository.findProductoMayor(10000)).thenReturn(List.of(createProducto()));
        List<Producto> productosCaros = productoService.obtenerProductoMayor(10000);
        assertNotNull(productosCaros);
        assertEquals(1, productosCaros.size());
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
        productoService.delete(1L);
        verify(productoRepository, times(1)).deleteById(1L);
    }
}
