package Grupo1.Xpress.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Grupo1.Xpress.Modelo.CategoriaProducto;
import Grupo1.Xpress.Modelo.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

        @Query("""
                SELECT p
                FROM Producto p
                JOIN p.marca m
                WHERE m.nombre = :nombre
                ORDER BY p.precio ASC
                """)
        List<Producto> findProductoPorMarca(@Param("nombre") String nombre);

   /*  @Query("""
            SELECT p
            FROM Producto p
            WHERE p.precio <50000
            ORDER BY p.precio ASC
            """)
    List<Producto> findProductoBarato();
*/
        @Query("""
                SELECT p
                FROM Producto p
                WHERE p.precio <= :precio
                ORDER BY p.precio ASC
                """)
        List<Producto> findProductoMenor(@Param("precio") Integer precio);
/*
    @Query("""
            SELECT p
            FROM Producto p
            WHERE p.precio >50000
            ORDER BY p.precio DESC
            """)
    List<Producto> findProductoCaro();
*/
        @Query("""
                SELECT p
                FROM Producto p
                WHERE p.precio >= :precio
                ORDER BY p.precio ASC
                """)
        List<Producto> findProductoMayor(@Param("precio") Integer precio);
//http://localhost:8080/productos/baratos?precio=50000

        @Query("""
                SELECT p
                FROM Producto p
                JOIN p.marca m
                JOIN p.oferta o
                WHERE p.precio >= :precio AND m.nombre = :nombre AND o.descuento = :descuento AND p.disponibilidad = :disponibilidad
                ORDER BY p.precio ASC
                """)
        List<Producto> findFiltrodescuentoMarcaMayor(@Param("precio") Integer precio, @Param("nombre") String nombre, @Param("descuento") Double descuento, @Param("disponibilidad") Boolean disponibilidad);
        
        @Query("""
                SELECT p
                FROM Producto p
                JOIN p.marca m
                JOIN p.oferta o
                WHERE p.precio <= :precio AND m.nombre = :nombre AND o.descuento = :descuento AND p.disponibilidad = :disponibilidad
                ORDER BY p.precio ASC
                """)
        List<Producto> findFiltrodescuentoMarcaMenor(@Param("precio") Integer precio, @Param("nombre") String nombre, @Param("descuento") Double descuento, @Param("disponibilidad") Boolean disponibilidad);

        Producto findById(long id);
        Producto findByNombre(String nombre);
        List<Producto> findByDisponibilidad(Boolean disponibilidad);
        List<Producto> findByCategoriaProducto(CategoriaProducto categoriaProducto);

}
