package Grupo1.Xpress.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration

public class SwaggerConfig {

  @Bean
  public OpenAPI CustomOpenAPI(){
    return new OpenAPI().info(
      new Info()
      .title("Api administración de Empresa Xpress")
      .version("1.1")
      .description("Con esta API se puede adminitrar los productos de la empresa de componentes Xpress, incluyendo agregar, actualizar y eliminar por id")
    );

  }
}
