package com.gateway.api_gateway.Config;

import com.gateway.api_gateway.Filters.AuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@AllArgsConstructor
public class GatewayConfig {

    private final AuthFilter authFilter;

    @Bean
    @Profile("security")
    public RouteLocator routeLocatorSecurity(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route -> route
                        .path("/api/clientes/**")
                        .filters(filter -> {
                                    filter.circuitBreaker(config -> config
                                            .setName("gateway-cb")
                                            .setFallbackUri("forward:/api/fallback/clientes")
                                    );
                                    filter.filter(this.authFilter);
                                    return filter;
                                }
                        )
                        .uri("lb://sommiersys-facturacion"))

                .route(route -> route
                        .path("/api/facturas_cabecera/**")
                        .filters(filter -> {
                                    filter.circuitBreaker(config -> config
                                            .setName("gateway-cb")
                                            .setFallbackUri("forward:/api/fallback/facturas_cabecera")
                                    );
                                    filter.filter(this.authFilter);
                                    return filter;
                                }
                        )
                        .uri("lb://sommiersys-facturacion"))

                .route(route -> route
                        .path("/api/productos/**")
                        .filters(filter -> {
                                    filter.circuitBreaker(config -> config
                                            .setName("gateway-cb")
                                            .setFallbackUri("forward:/api/fallback/productos")
                                    );
                                    filter.filter(this.authFilter);
                                    return filter;
                                }
                        )
                        .uri("lb://sommiersys-facturacion"))

                .route(route -> route
                        .path("/api/proveedores/**")
                        .filters(filter -> {
                                    filter.circuitBreaker(config -> config
                                            .setName("gateway-cb")
                                            .setFallbackUri("forward:/api/fallback/proveedores")
                                    );
                                    filter.filter(this.authFilter);
                                    return filter;
                                }
                        )
                        .uri("lb://sommiersys-facturacion"))

                .route(route -> route
                        .path("/api/deliveries/**")
                        .filters(filter -> {
                                    filter.circuitBreaker(config -> config
                                            .setName("gateway-cb")
                                            .setFallbackUri("forward:/api/fallback/deliveries")
                                    );
                                    filter.filter(this.authFilter);
                                    return filter;
                                }
                        )
                        .uri("lb://sommiersys-delivery"))

                .route(route -> route
                        .path("/api/auth/**")
                        .uri("lb://somiersys-security"))
                .build();
    }

}
