package Grupo1.Xpress.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Grupo1.Xpress.Modelo.Favorito;
import Grupo1.Xpress.Modelo.Producto;
import Grupo1.Xpress.Modelo.Usuario;


@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuario(Usuario usuario);
    List<Favorito> findByProducto(Producto producto);
    //void deleteByUsuario(Usuario usuario);
    //void deleteByProducto(Producto producto);
    void deleteByUsuarioId(Long usuarioId);
    void deleteByProductoId(Long productoId);
    // Alternativamente por el ID del usuario
    List<Favorito> findByUsuarioId(Long usuarioId);
    List<Favorito> findAll();
}