package Grupo1.Xpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Grupo1.Xpress.Modelo.ApiTienda;

@Repository
public interface ApiTiendaRepository extends JpaRepository <ApiTienda, Long>{

    ApiTienda findById(long id);
    ApiTienda findByNombre(String nombre);

}
