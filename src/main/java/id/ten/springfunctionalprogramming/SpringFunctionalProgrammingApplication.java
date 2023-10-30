package id.ten.springfunctionalprogramming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.*;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringFunctionalProgrammingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFunctionalProgrammingApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> routes(PostHandler postHandler) {
        return route(GET("/posts"), postHandler::all)
                .andRoute(POST("/posts"), postHandler::create)
                .andRoute(GET("/posts/{id}"), postHandler::get)
                .andRoute(PUT("/posts/{id}"), postHandler::update)
                .andRoute(DELETE("/posts/{id}"), postHandler::delete);
    }
}
