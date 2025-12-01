package ch.bdt.spike.spring.cloud.gatewayapplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
    @Bean
    public CommandLineRunner applicationRunner(@Value("${spring.application.version}") String version) {
        return args -> {
            log.info("GatewayApplication started, v" + version);
        };
    }


}
