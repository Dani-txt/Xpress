package Grupo1.Xpress.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import Grupo1.Xpress.Controller.CategoriaProductoControllerV2;
import Grupo1.Xpress.Modelo.CategoriaProducto;

@Component
public class CategoriaProductoModelAssembler implements RepresentationModelAssembler<CategoriaProducto, EntityModel<CategoriaProducto>>{
    @SuppressWarnings("null")

    @Override
    public EntityModel<CategoriaProducto> toModel(CategoriaProducto categoriaProducto) {
        return EntityModel.of(categoriaProducto,
            linkTo(methodOn(CategoriaProductoControllerV2.class).getCategoriaProductoById(categoriaProducto.getId())).withSelfRel(),
            linkTo(methodOn(CategoriaProductoControllerV2.class).getAllCategoriaProducto()).withRel("Categoria de productos"),
            linkTo(methodOn(CategoriaProductoControllerV2.class).updateCategoriaProducto(categoriaProducto.getId(), categoriaProducto)).withRel("actualizar"),
            linkTo(methodOn(CategoriaProductoControllerV2.class).deleteCategoriaProducto(categoriaProducto.getId())).withRel("eliminar"),
            linkTo(methodOn(CategoriaProductoControllerV2.class).patchCategoriaProducto(categoriaProducto.getId(), categoriaProducto)).withRel("actualizar-ApiService")
        );
    }
}
