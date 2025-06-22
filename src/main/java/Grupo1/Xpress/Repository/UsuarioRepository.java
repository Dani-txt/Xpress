package Grupo1.Xpress.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Grupo1.Xpress.Modelo.RolUsuario;
import Grupo1.Xpress.Modelo.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByRolUsuarioId(Long rolId);
    Long countByRolUsuario(RolUsuario rolUsuario);

}
