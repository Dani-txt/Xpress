package Grupo1.Xpress.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI CustomOpenAPI(){
    return new OpenAPI().info(
      new Info()
      .title("Api administradora del catalogo de productos Xpress.")
      .version("1.2")
      .description("""
                    API RESTful para gestionar productos, marcas, categorías y servicios externos (APIService) de la plataforma Xpress.\n
                    Equipo de desarrollo:
                    - Daniel Nuñez: contacto@xpress.cl
                    - Antonio Cornejo: contacto2@xpress.cl
                    - Sebastián Vargas: contacto3@xpress.cl
                    """)
      .termsOfService("https://xpress.cl/terminos")
      .contact(new Contact()
        .name("Equipo Xpress")
        .email("contactoEquipo@xpress.cl")
        .url("https://xpress.cl"))
    );
  }
}
