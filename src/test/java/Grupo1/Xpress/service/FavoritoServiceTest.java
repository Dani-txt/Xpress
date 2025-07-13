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
import org.springframework.context.annotation.Profile;

import Grupo1.Xpress.Modelo.Favorito;
import Grupo1.Xpress.Modelo.Producto;
import Grupo1.Xpress.Modelo.Usuario;
import Grupo1.Xpress.Repository.FavoritoRepository;
import Grupo1.Xpress.Service.FavoritoService;

@Profile("test")
@SpringBootTest
public class FavoritoServiceTest {
    @Autowired
    private FavoritoService favoritoService;

    @MockBean
    private FavoritoRepository favoritoRepository;

    private Favorito createFavorito(){
        return new Favorito(1L, new Usuario(), new Producto());
    }

    @Test
    public void testFindAll() {
        when(favoritoRepository.findAll()).thenReturn(List.of(createFavorito()));
    }

    @Test
    public void testFindById() {
        when(favoritoRepository.findById(1L)).thenReturn(java.util.Optional.of(createFavorito()));
        Favorito favorito = favoritoService.findById(1L);
        assertNotNull(favorito);
    }

    @Test
    public void testSave() {
        Favorito favorito = createFavorito();
        when(favoritoRepository.save(any(Favorito.class))).thenReturn(favorito);
        Favorito savedFavorito = favoritoService.save(favorito);
        assertNotNull(savedFavorito);
        assertEquals(1L, savedFavorito.getId());
    }

    @Test
    public void testDelete() {
        doNothing().when(favoritoRepository).deleteById(1L);
        favoritoService.deleteById(1L);
        verify(favoritoRepository, times(1)).deleteById(1L);
    }
}