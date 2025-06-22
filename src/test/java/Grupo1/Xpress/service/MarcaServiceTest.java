package Grupo1.Xpress.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import Grupo1.Xpress.Modelo.Marca;
import Grupo1.Xpress.Repository.MarcaRepository;
import Grupo1.Xpress.Service.MarcaService;

@ActiveProfiles("test")
@SpringBootTest
public class MarcaServiceTest {

    @Autowired
    private MarcaService marcaService;

    @MockBean
    private MarcaRepository marcaRepository;

    private Marca createMarca() {
        return new Marca(1L, "Samsung");
    }

    @Test
    public void testFindAll() {
        when(marcaRepository.findAll()).thenReturn(List.of(createMarca()));
        List<Marca> marcas = marcaService.findAll();
        assertNotNull(marcas);
        assertEquals(1, marcas.size());
    }

    @Test
    public void testFindById() {
        when(marcaRepository.findById(1L)).thenReturn(Optional.of(createMarca()));
        Marca marca = marcaService.findById(1L);
        assertNotNull(marca);
        assertEquals("Samsung", marca.getNombre());
    }

    @Test
    public void testFindByIdNotFound() {
        when(marcaRepository.findById(1L)).thenReturn(Optional.empty());
        Marca marca = marcaService.findById(1L);
        assertNull(marca);
    }

    @Test
    public void testFindByNombre() {
        when(marcaRepository.findByNombre("Samsung")).thenReturn(createMarca());
        Marca marca = marcaService.findByNombre("Samsung");
        assertNotNull(marca);
        assertEquals("Samsung", marca.getNombre());
    }

    @Test
    public void testSave() {
        Marca marca = createMarca();
        when(marcaRepository.save(marca)).thenReturn(marca);
        Marca saved = marcaService.save(marca);
        assertNotNull(saved);
        assertEquals("Samsung", saved.getNombre());
    }

    @Test
    public void testActualizar() {
        Marca existente = createMarca();
        Marca actualizado = new Marca(1L, "Samsung Actualizado");

        when(marcaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(marcaRepository.save(any(Marca.class))).thenReturn(actualizado);

        Marca result = marcaService.actualizar(1L, actualizado);
        assertNotNull(result);
        assertEquals("Samsung Actualizado", result.getNombre());
    }

    @Test
    public void testPatchMarca() {
        Marca existente = createMarca();
        Marca patchData = new Marca();
        patchData.setNombre("Samsung Parcheado");

        when(marcaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(marcaRepository.save(any(Marca.class))).thenAnswer(i -> i.getArgument(0));

        Marca result = marcaService.patchMarca(1L, patchData);
        assertNotNull(result);
        assertEquals("Samsung Parcheado", result.getNombre());
    }

    @Test
    public void testDelete() {
        doNothing().when(marcaRepository).deleteById(1L);
        marcaService.deleteById(1L);
        verify(marcaRepository, times(1)).deleteById(1L);
    }

}