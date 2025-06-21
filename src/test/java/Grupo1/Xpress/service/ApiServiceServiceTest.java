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

import Grupo1.Xpress.Modelo.ApiService;
import Grupo1.Xpress.Repository.ApiServiceRepository;
import Grupo1.Xpress.Service.ApiServiceService;

@SpringBootTest
public class ApiServiceServiceTest {

    @Autowired
    private ApiServiceService apiServiceService;

    @MockBean
    private ApiServiceRepository apiServiceRepository;

    private ApiService createApiService() {
        return new ApiService(1L, "www.falabella.cl", "falabella");
    }

    @Test
    public void testFindAll() {
        when(apiServiceRepository.findAll()).thenReturn(List.of(createApiService()));
        List<ApiService> apiService = apiServiceService.findAll();
        assertNotNull(apiService);
        assertEquals(1, apiService.size());
    }

    @Test
    public void testFindById() {
        when(apiServiceRepository.findById(1L)).thenReturn(createApiService());
        ApiService apiService = apiServiceService.findById(1L);
        assertNotNull(apiService);
        assertEquals("falabella", apiService.getNombre());
    }

    @Test
    public void testSave() {
        ApiService apiService = createApiService();
        when(apiServiceRepository.save(apiService)).thenReturn(apiService);
        ApiService savedApiService = apiServiceService.save(apiService);
        assertNotNull(savedApiService);
        assertEquals("falabella", savedApiService.getNombre());
    }

    @Test
    public void testPatchApiService() {
        ApiService existingApiService = createApiService();

        ApiService patchData = new ApiService();
        patchData.setNombre("Api Actualizada");

        when(apiServiceRepository.findById(1L)).thenReturn(existingApiService);
        when(apiServiceRepository.save(any(ApiService.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ApiService updatedApiService = apiServiceService.patchApiService(1L, patchData);
        assertNotNull(updatedApiService);
        assertEquals("Api Actualizada", updatedApiService.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(apiServiceRepository).deleteById(1L);
        apiServiceService.delete(1L);
        verify(apiServiceRepository, times(1)).deleteById(1L);
    }
}