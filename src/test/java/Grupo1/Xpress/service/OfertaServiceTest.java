package Grupo1.Xpress.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import Grupo1.Xpress.Modelo.Oferta;
import Grupo1.Xpress.Repository.OfertaRepository;
import Grupo1.Xpress.Service.OfertaService;

@ActiveProfiles("test")
@SpringBootTest
public class OfertaServiceTest {

    @Autowired
    private OfertaService ofertaService;

    @MockBean
    private OfertaRepository ofertaRepository;

    private Oferta createOferta() {
        return new Oferta(1L, 20.0);
    }

    @Test
    public void testFindAll() {
        when(ofertaRepository.findAll()).thenReturn(List.of(createOferta()));
        List<Oferta> ofertas = ofertaService.findAll();
        assertNotNull(ofertas);
        assertEquals(1, ofertas.size());
    }

    @Test
    public void testFindById() {
        when(ofertaRepository.findById(1L)).thenReturn(Optional.of(createOferta()));
        Oferta oferta = ofertaService.findById(1L);
        assertNotNull(oferta);
        assertEquals(20.0, oferta.getDescuento());
    }

    @Test
    public void testFindByIdNotFound() {
        when(ofertaRepository.findById(1L)).thenReturn(Optional.empty());
        Oferta oferta = ofertaService.findById(1L);
        assertNull(oferta);
    }

    @Test
    public void testFindByDescuento() {
        when(ofertaRepository.findByDescuento(20.0)).thenReturn(createOferta());
        Oferta oferta = ofertaService.findByDescuento(20.0);
        assertNotNull(oferta);
        assertEquals(20.0, oferta.getDescuento());
    }

    @Test
    public void testSave() {
        Oferta oferta = createOferta();
        when(ofertaRepository.save(oferta)).thenReturn(oferta);
        Oferta saved = ofertaService.save(oferta);
        assertNotNull(saved);
        assertEquals(20.0, saved.getDescuento());
    }

    @Test
    public void testDelete() {
        doNothing().when(ofertaRepository).deleteById(1L);
        ofertaService.delete(1L);
        verify(ofertaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testActualizar() {
        Oferta existente = createOferta();
        Oferta actualizado = new Oferta(1L, 30.0);

        when(ofertaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(ofertaRepository.save(any(Oferta.class))).thenReturn(actualizado);

        Oferta result = ofertaService.actualizar(1L, actualizado);
        assertNotNull(result);
        assertEquals(30.0, result.getDescuento());
    }

    @Test
    public void testActualizarNotFound() {
        when(ofertaRepository.findById(1L)).thenReturn(Optional.empty());
        Oferta result = ofertaService.actualizar(1L, createOferta());
        assertNull(result);
    }

    @Test
    public void testPatchOferta() {
        Oferta existente = createOferta();
        Oferta patchData = new Oferta();
        patchData.setDescuento(25.0);

        when(ofertaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(ofertaRepository.save(any(Oferta.class))).thenAnswer(i -> i.getArgument(0));

        Oferta result = ofertaService.patchOferta(1L, patchData);
        assertNotNull(result);
        assertEquals(25.0, result.getDescuento());
    }

    @Test
    public void testPatchOfertaNotFound() {
        when(ofertaRepository.findById(1L)).thenReturn(Optional.empty());
        Oferta result = ofertaService.patchOferta(1L, new Oferta());
        assertNull(result);
    }

    @Test
    public void testPatchOfertaNoChanges() {
        Oferta existente = createOferta();
        Oferta patchData = new Oferta();

        when(ofertaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(ofertaRepository.save(any(Oferta.class))).thenAnswer(i -> i.getArgument(0));

        Oferta result = ofertaService.patchOferta(1L, patchData);
        assertNotNull(result);
        assertEquals(20.0, result.getDescuento());
    }
}