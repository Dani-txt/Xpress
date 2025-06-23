package Grupo1.Xpress.Modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private  Long id;

    @Column(length=50, nullable=false)
    private String nombre;

    @Column(length=50, nullable=false)
    private String apellido;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length= 20,nullable=false)
    private String contrasenia;

    @Column(length=50, nullable=false)
    private String correo;

    @ManyToOne
    @JoinColumn(name="rol_usuario_id", nullable=false)
    private RolUsuario rolUsuario;

}