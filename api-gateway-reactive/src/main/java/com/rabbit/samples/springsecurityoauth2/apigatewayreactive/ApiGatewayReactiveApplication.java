package com.rabbit.samples.springsecurityoauth2.apigatewayreactive;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayReactiveApplication {

    @Value("${gateway.auth.hybris.url}")
    private String hybrisUrl;

    @Value("${gateway.auth.pattern}")
    private String pattern;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayReactiveApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){

        return builder.routes()

                .route(
                        spec -> spec.path("/products/**")
                                .and()
                                .uri("http://localhost:8081/products"))
                .route(
                        spec -> spec.path("/oauth/**")
                                .and()
                                .uri("http://localhost:8082/oauth"))
                .route(
                        spec -> spec.path(pattern)
                                .and()
                                .uri(hybrisUrl))
                // .route(
                //         spec -> spec.path("/greenocc/**")
                //                 .and()
                //                 .uri("http://localhost:9001/greenocc"))
                .build();

    }


}
