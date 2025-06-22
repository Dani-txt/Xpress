package Grupo1.Xpress.Modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name= "producto")
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable=false)
    private String nombre;

    @Column(nullable=false)
    private Boolean disponibilidad;

    @Column(length=9 ,nullable=false)
    private Integer precio;

    @Column(nullable=false)
    private String descripcion;

    @ManyToOne
    @JoinColumn (name = "categoria_producto_id", nullable = false)
    private CategoriaProducto categoriaProducto;

    @ManyToOne
    @JoinColumn(name="api_service_id", nullable=false)
    private ApiService apiService;

    @ManyToOne
    @JoinColumn(name="Marca_id", nullable=false)
    private Marca marca;

    @ManyToOne
    @JoinColumn(name="Oferta_id", nullable=true)
    private Oferta oferta;
}