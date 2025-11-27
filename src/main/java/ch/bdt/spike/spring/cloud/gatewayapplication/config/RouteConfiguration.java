package ch.bdt.spike.spring.cloud.gatewayapplication.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
@Slf4j
class RouteConfiguration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        log.info("CustomRouteLocator building routes");

        // La méthode .routes() démarre la construction des routes.
        return builder.routes()
                      // --- Route 1: /get (Proxifie vers http://httpbin.org/get) ---
                      .route("get",
                             r -> r.path("/get") // Predicate: Intercepte les requêtes PATH qui correspondent à /get
                                   .uri("http://httpbin.org"))    // URI: Route la requête vers cette URI
                      // --- Route 2: /cc/** (Proxifie vers http://localhost:8081 après suppression du préfixe) ---
                      .route("currency-converter",
                             r -> r.path("/cc/**") // Predicate: Intercepte les requêtes PATH qui correspondent à /cc/...
                                   .filters(f -> f.stripPrefix(1)) // Filter: Supprime le premier segment de l'URI (/cc) avant de router
                                   // Équivalent à .before(stripPrefix(1))
                                   .uri("http://localhost:8081"))  // URI: Route la requête modifiée vers ce service
                      .build();
    }

    //@Bean
    //public RouterFunction<ServerResponse> myRoutes() {
    //    // Route 1: /get
    //    RouterFunction<ServerResponse> route1 = route("tutu-get")
    //            .route(RequestPredicates.GET("/get"), http())
    //            .before(uri("http://httpbin.org"))
    //            .build();
    //
    //    // Route 2: /cc/**
    //    RouterFunction<ServerResponse> route2 = route("tutu-cc")
    //            .route(RequestPredicates.path("/cc/**"), http())
    //            .before(stripPrefix(1))
    //            .before(uri("http://localhost:8081"))
    //            .build();
    //
    //    // Combinaison des routes
    //    return route1.and(route2);
    //}


}