package Grupo1.Xpress.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.Xpress.Modelo.Favorito;
import Grupo1.Xpress.Repository.FavoritoRepository;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    public List<Favorito> findAll(){
        return favoritoRepository.findAll();
    }

    //busqueda x id
    public Favorito findById(Long id){
        return favoritoRepository.findById(id).orElse(null);
    }

    public List<Favorito> obtenerFavoritosPorUsuarioId(Long usuarioId) {
        return favoritoRepository.findByUsuarioId(usuarioId);
    }

    public Favorito save(Favorito favorito) {
        return favoritoRepository.save(favorito);
    }

        // Elimina todos los favoritos de un usuario
    public void eliminarFavoritosPorUsuarioId(Long usuarioId) {
        favoritoRepository.deleteByUsuarioId(usuarioId);
    }

    // Elimina todos los favoritos de un producto
    public void eliminarFavoritosPorProductoId(Long productoId) {
        favoritoRepository.deleteByProductoId(productoId);
    }

    public void deleteById(Long id) {
        Favorito favorito = favoritoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Favorito no encontrado"));

        favoritoRepository.delete(favorito);
    }
}