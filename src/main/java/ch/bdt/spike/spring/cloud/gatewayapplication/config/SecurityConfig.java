package ch.bdt.spike.spring.cloud.gatewayapplication.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {
    @Value("${spring.application.version}")
    private String version;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        log.info("SecurityConfig building security filter chain - v" + version);
        http
                // Désactive CSRF pour simplifier (à revoir en production)
                .csrf(csrf -> csrf.disable())

                // 1. Autoriser/Sécuriser les requêtes
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated())

                // 2. Activer l'authentification OAuth2/OIDC (avec redirection vers Keycloak)
                // Ceci active le filtre qui intercepte les requêtes non authentifiées
                // et les redirige vers l'URI d'autorisation Keycloak.
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }

    //@Bean
    //public HttpSessionEventPublisher httpSessionEventPublisher() {
    //    return new HttpSessionEventPublisher();
    //}
    //
    //@Bean
    //public String myDummyBean() {
    //    throw new RuntimeException("Dummy bean");
    //}


}
