package Grupo1.Xpress.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import Grupo1.Xpress.Controller.ProductoControllerV2;
import Grupo1.Xpress.Modelo.Producto;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

  @SuppressWarnings("null")

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
            linkTo(methodOn(ProductoControllerV2.class).getProductoById(producto.getId())).withSelfRel(),
            linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withRel("todos-los-productos"),
            linkTo(methodOn(ProductoControllerV2.class).updateProducto(producto.getId(), producto)).withRel("actualizar-producto"),
            linkTo(methodOn(ProductoControllerV2.class).patchProducto(producto.getId(), producto)).withRel("editar-producto"),
            linkTo(methodOn(ProductoControllerV2.class).deleteProducto(producto.getId())).withRel("eliminar-producto"),
            linkTo(methodOn(ProductoControllerV2.class).getMarca(producto.getMarca().getNombre())).withRel("productos-misma-marca"),
            linkTo(methodOn(ProductoControllerV2.class).getCategoria(producto.getCategoriaProducto().getNombre())).withRel("productos-misma-categoria"),
            linkTo(methodOn(ProductoControllerV2.class).getDisponibilidad(producto.getDisponibilidad())).withRel("productos-misma-disponibilidad")
        );
    }
}
