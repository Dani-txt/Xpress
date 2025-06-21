package Grupo1.Xpress.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Grupo1.Xpress.Modelo.RolUsuario;
import Grupo1.Xpress.Repository.RolUsuarioRepository;

@Service
@Transactional
public class RolUsuarioService {
    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

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

    public void delete(Long id){
        rolUsuarioRepository.deleteById(id);
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

}
