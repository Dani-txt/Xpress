package Grupo1.Xpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Grupo1.Xpress.Modelo.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository <Oferta, Long>{

    Oferta findByDescuento(Double descuento);

}
