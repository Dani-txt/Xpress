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

import Grupo1.Xpress.Modelo.ApiTienda;
import Grupo1.Xpress.Repository.ApiTiendaRepository;
import Grupo1.Xpress.Service.ApiTiendaService;

@ActiveProfiles("test") 
@SpringBootTest
public class ApiTiendaServiceTest {

    @Autowired
    private ApiTiendaService apiTiendaService;

    @MockBean
    private ApiTiendaRepository apiTiendaRepository;

    private ApiTienda createApiTienda() {
        return new ApiTienda(1L, "www.falabella.cl", "falabella");
    }

    @Test
    public void testFindAll() {
        when(apiTiendaRepository.findAll()).thenReturn(List.of(createApiTienda()));
        List<ApiTienda> apiTienda = apiTiendaService.findAll();
        assertNotNull(apiTienda);
        assertEquals(1, apiTienda.size());
    }

    @Test
    public void testFindById() {
        when(apiTiendaRepository.findById(1L)).thenReturn(createApiTienda());
        ApiTienda apiTienda = apiTiendaService.findById(1L);
        assertNotNull(apiTienda);
        assertEquals("falabella", apiTienda.getNombre());
    }

    @Test
    public void testSave() {
        ApiTienda apiTienda = createApiTienda();
        when(apiTiendaRepository.save(apiTienda)).thenReturn(apiTienda);
        ApiTienda savedApiTienda = apiTiendaService.save(apiTienda);
        assertNotNull(savedApiTienda);
        assertEquals("falabella", savedApiTienda.getNombre());
    }

    @Test
    public void testPatchApiTienda() {
        ApiTienda existingApiTienda = createApiTienda();

        ApiTienda patchData = new ApiTienda();
        patchData.setNombre("Api Actualizada");

        when(apiTiendaRepository.findById(1L)).thenReturn(existingApiTienda);
        when(apiTiendaRepository.save(any(ApiTienda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ApiTienda updatedApiTienda = apiTiendaService.patchApiTienda(1L, patchData);
        assertNotNull(updatedApiTienda);
        assertEquals("Api Actualizada", updatedApiTienda.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(apiTiendaRepository).deleteById(1L);
        apiTiendaService.delete(1L);
        verify(apiTiendaRepository, times(1)).deleteById(1L);
    }
}