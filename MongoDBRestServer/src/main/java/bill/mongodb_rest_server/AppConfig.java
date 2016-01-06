package bill.mongodb_rest_server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import bill.mongodb_rest_server.srv.ResponseBuilder;

@Configuration
@ComponentScan(basePackages="bill.mongodb_rest_server")
@PropertySource("classpath:/config.properties")
//@ImportResource("classpath:/properties-config.xml")
public class AppConfig {

    @Value("${responseDelay}")
    private int responseDelay;

    @Bean
    public ResponseBuilder responseBuilder() {
        return new ResponseBuilder();
    }

}

