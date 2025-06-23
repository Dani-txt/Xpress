package Grupo1.Xpress.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.Xpress.Modelo.Usuario;
import Grupo1.Xpress.Repository.FavoritoRepository;
import Grupo1.Xpress.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FavoritoRepository favoritoRepository;

    public List<Usuario>findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> findByRolUsuarioId(Long rolId) {
        return usuarioRepository.findByRolUsuarioId(rolId);
    }

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario usuario){
        Usuario us = usuarioRepository.findById(id).orElse(null);
        if (us != null) {
            us.setNombre(usuario.getNombre());
            us.setApellido(usuario.getApellido());
            us.setCorreo(usuario.getCorreo());
            us.setContrasenia(usuario.getContrasenia());
            us.setRolUsuario(usuario.getRolUsuario());
            return usuarioRepository.save(us);
        } else {
            return null;
        }
    }

    public Usuario patchUsuario(Long id, Usuario usuarioParcial){
        Optional<Usuario> usuarioPOptional = usuarioRepository.findById(id);
        if(usuarioPOptional.isPresent()){
            Usuario usuarioToUpdate = usuarioPOptional.get();

            if(usuarioParcial.getNombre() !=null){
                usuarioToUpdate.setNombre(usuarioParcial.getNombre());
            }
            if(usuarioParcial.getApellido() !=null){
                usuarioToUpdate.setApellido(usuarioParcial.getApellido());
            }
            if(usuarioParcial.getCorreo() !=null){
                usuarioToUpdate.setCorreo(usuarioParcial.getCorreo());
            }
            if(usuarioParcial.getContrasenia() !=null){
                usuarioToUpdate.setContrasenia(usuarioParcial.getContrasenia());
            }
            if(usuarioParcial.getRolUsuario()!=null){
                usuarioToUpdate.setRolUsuario(usuarioParcial.getRolUsuario());
            }
            return usuarioRepository.save(usuarioToUpdate);
        }else{
            return null;
        }
    }

    public void eliminarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // 1. Elimina los favoritos del usuario
        favoritoRepository.deleteByUsuarioId(id);
        // 2. Elimina el usuario
        usuarioRepository.deleteById(id);
    }

    

}