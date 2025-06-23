package Grupo1.Xpress.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.Xpress.Modelo.Oferta;
import Grupo1.Xpress.Repository.OfertaRepository;
import Grupo1.Xpress.Repository.ProductoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OfertaService {
    
    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todas las ofertas
    public List<Oferta> findAll() {
        return ofertaRepository.findAll();
    }

    // Buscar oferta por ID
    public Oferta findById(Long id) {
        return ofertaRepository.findById(id).orElse(null);
    }

    // Buscar oferta por descuento
    public Oferta findByDescuento(Double descuento) {
        return ofertaRepository.findByDescuento(descuento);
    }

    // Guardar nueva oferta
    public Oferta save(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    // Actualizar una oferta
    public Oferta actualizar(Long id, Oferta oferta) {
        Oferta of = ofertaRepository.findById(id).orElse(null);
        if (of != null) {
            of.setDescuento(oferta.getDescuento());
            return ofertaRepository.save(of);
        } else {
            return null;
        }
    }

    // Patch a una oferta (solo modificar descuento)
    public Oferta patchOferta(Long id, Oferta ofertaParcial) {
        Optional<Oferta> ofertaOptional = ofertaRepository.findById(id);
        if (ofertaOptional.isPresent()) {
            Oferta ofertaToUpdate = ofertaOptional.get();
            if (ofertaParcial.getDescuento() != null) {
                ofertaToUpdate.setDescuento(ofertaParcial.getDescuento());
            }
            return ofertaRepository.save(ofertaToUpdate);
        } else {
            return null;
        }
    }

    //eliminar marca
    public void deleteById(Long id) {
        //1ero se busca a la api por su id
        Oferta oferta = ofertaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        //Se elimina la oferta del producto
        productoRepository.deleteByOferta(oferta);
        //se elimina la oferta
        ofertaRepository.delete(oferta);
    }
}

