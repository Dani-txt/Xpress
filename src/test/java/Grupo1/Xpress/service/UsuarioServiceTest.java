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

import Grupo1.Xpress.Modelo.RolUsuario;
import Grupo1.Xpress.Modelo.Usuario;
import Grupo1.Xpress.Repository.UsuarioRepository;
import Grupo1.Xpress.Service.UsuarioService;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository UsuarioRepository;

    private Usuario createUsuario() {
        RolUsuario rol = new RolUsuario();
        rol.setId(1L);
        return new Usuario(1L,
                "Daniel",
                "Diaz",
                "123456Abcdef",
                "Da.nunezd@duocuc.cl",
                rol);
    }

    @Test
    public void testFindAll() {
        when(UsuarioRepository.findAll()).thenReturn(List.of(createUsuario()));
        List<Usuario> usuarios = usuarioService.findAll();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testFindById() {
        when(UsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createUsuario()));
        Usuario usuario = usuarioService.findById(1L);
        assertNotNull(usuario);
        assertEquals("Daniel", usuario.getNombre());
    }

    @Test
    public void testFindByRol() {
        Usuario usuario = createUsuario();
        Long rolId = usuario.getRolUsuario().getId();

        when(UsuarioRepository.findByRolUsuarioId(rolId)).thenReturn(List.of(usuario));

        List<Usuario> usuarios = usuarioService.findByRolUsuarioId(rolId);
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals(rolId, usuarios.get(0).getRolUsuario().getId());
    }

    @Test
    public void testSave() {
        Usuario usuario = createUsuario();
        when(UsuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario savedUsuario = usuarioService.save(usuario);
        assertNotNull(savedUsuario);
        assertEquals("Daniel", savedUsuario.getNombre());
    }

    @Test
    public void testPatchUsuario() {
        Usuario existingUsuario = createUsuario();
        Usuario patchData = new Usuario();
        patchData.setNombre("Usuario actualizado");

        when(UsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(existingUsuario));
        when(UsuarioRepository.save(any(Usuario.class))).thenReturn(existingUsuario);

        Usuario patchedUsuario = usuarioService.patchUsuario(1L, patchData);
        assertNotNull(patchedUsuario);
        assertEquals("Usuario actualizado", patchedUsuario.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(UsuarioRepository).deleteById(1L);
        usuarioService.delete(1L);
        verify(UsuarioRepository, times(1)).deleteById(1L);
    }


}
