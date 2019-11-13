package com.wxmlabs.hellogradle.springboot2;

import com.wxmlabs.hellogradle.Echo;
import com.wxmlabs.hellogradle.EchoImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class App extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(App.class);
        return super.configure(builder);
    }

    @Bean
    public Echo echo() {
        return new EchoImpl();
    }
}
