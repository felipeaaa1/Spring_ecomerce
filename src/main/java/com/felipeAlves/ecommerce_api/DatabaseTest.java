package com.felipeAlves.ecommerce_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatabaseTest implements CommandLineRunner {
    private final DataSource dataSource;

    public DatabaseTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Testando conexão ao banco de dados...");
        System.out.println("URL: " + dataSource.getConnection().getMetaData().getURL());
        System.out.println("Driver: " + dataSource.getConnection().getMetaData().getDriverName());
    }
}
