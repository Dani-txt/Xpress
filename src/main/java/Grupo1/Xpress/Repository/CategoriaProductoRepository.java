package Grupo1.Xpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Grupo1.Xpress.Modelo.CategoriaProducto;

@Repository
public interface CategoriaProductoRepository extends JpaRepository <CategoriaProducto, Long>{

    CategoriaProducto findById(long id);
    CategoriaProducto findByNombre(String nombre);

}
