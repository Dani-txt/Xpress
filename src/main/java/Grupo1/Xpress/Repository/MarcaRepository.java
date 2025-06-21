package Grupo1.Xpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Grupo1.Xpress.Modelo.Marca;

@Repository
public interface MarcaRepository extends JpaRepository <Marca, Long>{

    Marca findByNombre(String nombre);

}
