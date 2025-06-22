package Grupo1.Xpress.service;

import java.util.List;
import java.util.Optional;

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

import Grupo1.Xpress.Modelo.RolUsuario;
import Grupo1.Xpress.Repository.RolUsuarioRepository;
import Grupo1.Xpress.Service.RolUsuarioService;

@ActiveProfiles("test")
@SpringBootTest
public class RolUsuarioServiceTest {

    @Autowired
    private RolUsuarioService RolUsuarioService;

    @MockBean
    private RolUsuarioRepository RolUsuarioRepository;

    private RolUsuario createRolUsuario(){
        return new RolUsuario(1L, "Cliente");
    }

    @Test
    public void testFindAll(){
        when(RolUsuarioRepository.findAll()).thenReturn(List.of(createRolUsuario()));
        List<RolUsuario> rolUsuario = RolUsuarioService.findAll();
        assertNotNull(rolUsuario);
        assertEquals(1, rolUsuario.size());
    }

    @Test
    public void testFindById() {
        when(RolUsuarioRepository.findById(1L)).thenReturn(Optional.of(createRolUsuario()));
        RolUsuario rolUsuario = RolUsuarioService.findById(1L);
        assertNotNull(rolUsuario);
        assertEquals("Cliente", rolUsuario.getNombre());
    }

    @Test
    public void testSave() {
        RolUsuario rolUsuario = createRolUsuario();
        when(RolUsuarioRepository.save(rolUsuario)).thenReturn(rolUsuario);
        RolUsuario savedRolUsuario = RolUsuarioService.save(rolUsuario);
        assertNotNull(savedRolUsuario);
        assertEquals("Cliente", savedRolUsuario.getNombre());
    }

    @Test
    public void testPatchProducto() {
        RolUsuario existingRolUsuario = createRolUsuario();
        RolUsuario patchData = new RolUsuario();
        patchData.setNombre("Rol actualizado");

        //when(RolUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(existingRolUsuario));
        when(RolUsuarioRepository.findById(1L)).thenReturn(Optional.of(existingRolUsuario));
        when(RolUsuarioRepository.save(any(RolUsuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RolUsuario patchedRolUsuario = RolUsuarioService.patchRolUsuario(1L, patchData);

        assertNotNull(patchedRolUsuario);
        assertEquals("RolUsuario actualizada", patchedRolUsuario.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(RolUsuarioRepository).deleteById(1L);
        RolUsuarioService.deleteById(1L);
        verify(RolUsuarioRepository, times(1)).deleteById(1L);
    }

}
