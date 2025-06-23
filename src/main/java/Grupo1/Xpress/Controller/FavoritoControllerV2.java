package Grupo1.Xpress.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Grupo1.Xpress.Modelo.Favorito;
import Grupo1.Xpress.Service.FavoritoService;

@RestController
@RequestMapping("/api/v2/favoritos")
public class FavoritoControllerV2 {

    @Autowired
    private FavoritoService favoritoService;

    @GetMapping
    public ResponseEntity<List<Favorito>> AllFavoritos (){
        List<Favorito> favoritos = favoritoService.findAll();
        if (favoritos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favoritos);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Favorito>> getFavoritosPorUsuario(@PathVariable Long usuarioId) {
        List<Favorito> favoritos = favoritoService.obtenerFavoritosPorUsuarioId(usuarioId);
        return ResponseEntity.ok(favoritos);
    }

    @GetMapping("/prodcuto/{prodcutoId}")
    public ResponseEntity<List<Favorito>> getFavoritosPorProducto(@PathVariable Long productoId) {
        List<Favorito> favoritos = favoritoService.obtenerFavoritosPorUsuarioId(productoId);
        return ResponseEntity.ok(favoritos);
    }

    @PostMapping
    public ResponseEntity<Favorito> guardar(@RequestBody Favorito favorito) {
        Favorito favoritoNuevo = favoritoService.save(favorito);
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritoNuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        try {
            favoritoService.eliminarFavoritoPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
