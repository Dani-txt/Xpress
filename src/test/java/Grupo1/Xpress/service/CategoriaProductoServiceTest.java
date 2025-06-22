package Grupo1.Xpress.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import Grupo1.Xpress.Modelo.CategoriaProducto;
import Grupo1.Xpress.Repository.CategoriaProductoRepository;
import Grupo1.Xpress.Service.CategoriaProductoService;

@ActiveProfiles("test") 
@SpringBootTest
public class CategoriaProductoServiceTest {

    @Autowired
    private CategoriaProductoService categoriaProductoService;

    @MockBean
    private CategoriaProductoRepository categoriaProductoRepository;

    private CategoriaProducto createCategoriaProducto(){
        return new CategoriaProducto(1L, "Electrodomestico");
    }

    @Test
    public void testFindAll(){
        when(categoriaProductoRepository.findAll()).thenReturn(List.of(createCategoriaProducto()));
        List<CategoriaProducto> categorias = categoriaProductoService.findAll();
        assertNotNull(categorias);
        assertEquals(1, categorias.size());
    }

    @Test
    public void testFindById() {
        when(categoriaProductoRepository.findById(1L)).thenReturn(createCategoriaProducto());
        CategoriaProducto categoriaProducto = categoriaProductoService.findById(1L);
        assertNotNull(categoriaProducto);
        assertEquals("Electrodomestico", categoriaProducto.getNombre());
    }

    @Test
    public void testSave() {
        CategoriaProducto categoriaProducto = createCategoriaProducto();
        when(categoriaProductoRepository.save(categoriaProducto)).thenReturn(categoriaProducto);
        CategoriaProducto savedCategoriaProducto = categoriaProductoService.save(categoriaProducto);
        assertNotNull(savedCategoriaProducto);
        assertEquals("Electrodomestico", savedCategoriaProducto.getNombre());
    }

    @Test
    public void testPatchCategoriaProducto() {
        CategoriaProducto existingCategoriaProducto = createCategoriaProducto();

        CategoriaProducto patchData = new CategoriaProducto();
        patchData.setNombre("Categoria actualizada");

        when(categoriaProductoRepository.findById(1L)).thenReturn(existingCategoriaProducto);
        when(categoriaProductoRepository.save(any(CategoriaProducto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CategoriaProducto updatedCategoriaProducto = categoriaProductoService.patchCategoriaProducto(1L, patchData);

        assertNotNull(updatedCategoriaProducto);
        assertEquals("Categoria actualizada", updatedCategoriaProducto.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(categoriaProductoRepository).deleteById(1L);
        categoriaProductoService.deleteById(1L);
        verify(categoriaProductoRepository, times(1)).deleteById(1L);
    }
}
