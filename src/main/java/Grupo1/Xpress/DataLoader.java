package Grupo1.Xpress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import Grupo1.Xpress.Modelo.ApiTienda;
import Grupo1.Xpress.Modelo.CategoriaProducto;
import Grupo1.Xpress.Modelo.Marca;
import Grupo1.Xpress.Modelo.Oferta;
import Grupo1.Xpress.Modelo.Producto;
import Grupo1.Xpress.Modelo.RolUsuario;
import Grupo1.Xpress.Modelo.Usuario;
import Grupo1.Xpress.Repository.ApiTiendaRepository;
import Grupo1.Xpress.Repository.CategoriaProductoRepository;
import Grupo1.Xpress.Repository.MarcaRepository;
import Grupo1.Xpress.Repository.OfertaRepository;
import Grupo1.Xpress.Repository.ProductoRepository;
import Grupo1.Xpress.Repository.RolUsuarioRepository;
import Grupo1.Xpress.Repository.UsuarioRepository;
import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private ApiTiendaRepository apiTiendaRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Crear roles de usuario
        String[] rolesUsuarios={
            "Administrador",
            "Cliente",
            "Desarrollador",
        };
        for (int i = 0; i < 3; i++) {
            RolUsuario rol = new RolUsuario();
            rol.setNombre(faker.options().option(rolesUsuarios));
            rolUsuarioRepository.save(rol);
        }

        List<RolUsuario> roles = rolUsuarioRepository.findAll();

        // Crear usuarios
        for (int i = 0; i < 10; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().firstName());
            usuario.setApellido(faker.name().lastName());
            usuario.setCorreo(faker.internet().emailAddress());
            usuario.setContrasenia(faker.internet().password());
            usuario.setRolUsuario(roles.get(random.nextInt(roles.size())));
            usuarioRepository.save(usuario);
        }

        // Crear categorías
        String[] categoriasProductos={
            "Audio",
            "Tecnología",
            "Notebook",
            "Tablet",
            "Celulares",
            "Consolas",
            "Videojuegos",
            "Accesorios",
            "Electrodomésticos",
            "Electrónica",
        };
        for (int i = 0; i < 10; i++) {
            CategoriaProducto categoria = new CategoriaProducto();
            categoria.setNombre(faker.options().option(categoriasProductos));
            categoriaProductoRepository.save(categoria);
        }

        // Crear marcas
        String [] nombresMarcas ={
            "Nvidia",
            "Intel",
            "AMD",
            "Nintendo",
            "PlayStation",
            "Microsoft",
        };
        for (int i = 0; i < 10; i++) {
            Marca marca = new Marca();
            marca.setNombre(faker.options().option(nombresMarcas));
            marcaRepository.save(marca);
        }

        // Crear ofertas
        Double[] nuevasOfertas={
            10.0,
            15.0,
            20.0,
            25.0,
            30.0,
            35.0,
            40.0,
            45.0,
            50.0
        };
        for (int i = 0; i < 5; i++) {
            Oferta oferta = new Oferta();
            oferta.setDescuento(faker.options().option(nuevasOfertas));
            ofertaRepository.save(oferta);
        }

        // Crear APIs
        String[] nombresAPI = {
            "Falabella",
            "MercadoLibre",
            "Amazon"
        };

        String[] urlApi={
            "https://api.falabella.com",
            "https://api.mercadolibre.com",
            "https://api.amazon.com"
        };

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < nombresAPI.length; i++) {
            indices.add(i);
        }

        // Paso 2: barajar la lista
        Collections.shuffle(indices);
        
        for (int i : indices) {
            ApiTienda api = new ApiTienda();
            api.setNombre(nombresAPI[i]);
            api.setUrl(urlApi[i]);
            apiTiendaRepository.save(api);
        }

        List<CategoriaProducto> categorias = categoriaProductoRepository.findAll();
        List<Marca> marcas = marcaRepository.findAll();
        List<Oferta> ofertas = ofertaRepository.findAll();
        List<ApiTienda> apis = apiTiendaRepository.findAll();
        // Crear productos
        String[] nombreProductos={
            "Audifonos",
            "Tablet",
            "Laptop",
            "Celular",
            "Consola",
            "Monitor",
            "Teclado",
            "Mouse",
            "Impresora",
            "Smarthwatch",
            "Cámara",
            "Procesador",
            "Memoria RAM",
            "Almacenamiento",
        };
        for (int i = 0; i < 30; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.options().option(nombreProductos));
            producto.setDescripcion(faker.lorem().sentence());
            producto.setPrecio(faker.number().numberBetween(10000, 200000));
            producto.setDisponibilidad(faker.bool().bool());
            producto.setCategoriaProducto(categorias.get(random.nextInt(categorias.size())));
            producto.setMarca(marcas.get(random.nextInt(marcas.size())));
            producto.setOferta(ofertas.get(random.nextInt(ofertas.size())));
            producto.setApiTienda(apis.get(random.nextInt(apis.size())));
            productoRepository.save(producto);
        }
    }
}

