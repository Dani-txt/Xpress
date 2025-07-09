package Grupo1.Xpress.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Grupo1.Xpress.Modelo.Favorito;
import Grupo1.Xpress.Modelo.RolUsuario;
import Grupo1.Xpress.Modelo.Usuario;
import Grupo1.Xpress.Repository.FavoritoRepository;
import Grupo1.Xpress.Repository.RolUsuarioRepository;
import Grupo1.Xpress.Repository.UsuarioRepository;

@Service
@Transactional
public class RolUsuarioService {
    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FavoritoRepository favoritoRepository;

    public List<RolUsuario>findAll(){
        return rolUsuarioRepository.findAll();
    }
    public RolUsuario findById(Long id){
        return rolUsuarioRepository.findById(id).orElse(null);
    }
    public RolUsuario findByNombre(String nombre){
        return rolUsuarioRepository.findBynombre(nombre);
    }
    public RolUsuario save(RolUsuario rolUsuario){
        return rolUsuarioRepository.save(rolUsuario);
    }

    public RolUsuario actualizar(Long id, RolUsuario rolUsuario){
        RolUsuario rolUs = rolUsuarioRepository.findById(id).orElse(null);
        if (rolUsuario != null) {
            rolUs.setNombre(rolUsuario.getNombre());
            return rolUsuarioRepository.save(rolUs);
        } else{
            return null;
        }
    }

    public RolUsuario patchRolUsuario(Long id, RolUsuario parcialRolUsuario){
        Optional<RolUsuario> rolUsuarioOptional = rolUsuarioRepository.findById(id);
        if (rolUsuarioOptional.isPresent()) {
            RolUsuario rolUsuarioToUpdate = rolUsuarioOptional.get();
            if (parcialRolUsuario.getNombre() != null) {
                rolUsuarioToUpdate.setNombre(parcialRolUsuario.getNombre());
                }
                return rolUsuarioRepository.save(rolUsuarioToUpdate);
            }else{
                return null;
            }
    }

    //eliminar rol
    public void deleteById(Long id) {
        // 1. Buscar el rol
        RolUsuario rol = rolUsuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // 2. Obtener todos los usuarios con ese rol
        List<Usuario> usuarios = usuarioRepository.findByRolUsuario(rol);

        // 3. Eliminar los favoritos de cada usuario, luego el usuario
        for (Usuario usuario : usuarios) {
            List<Favorito> favoritos = favoritoRepository.findByUsuario(usuario);
            for (Favorito favorito : favoritos) {
                favoritoRepository.delete(favorito);
            }
            usuarioRepository.delete(usuario);
        }

        // 4. Finalmente, eliminar el rol
        rolUsuarioRepository.delete(rol);
    }

}
