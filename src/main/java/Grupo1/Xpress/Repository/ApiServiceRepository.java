package Grupo1.Xpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Grupo1.Xpress.Modelo.ApiService;

@Repository
public interface ApiServiceRepository extends JpaRepository <ApiService, Long>{

    ApiService findById(long id);
    ApiService findByNombre(String nombre);

}
