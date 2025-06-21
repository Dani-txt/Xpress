package Grupo1.Xpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Grupo1.Xpress.Modelo.RolUsuario;

@Repository
public interface RolUsuarioRepository extends JpaRepository<RolUsuario, Long>{
    RolUsuario findBynombre (String nombre);
}
