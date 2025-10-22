package com.ebac.modulo61.config;

import com.ebac.modulo61.model.DataBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public DataBase dataBase() {
        return new DataBase();
    }
}
