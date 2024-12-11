package com.felipeAlves.ecommerce_api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EcommerceApiApplicationTests {

    @BeforeAll
    static void setup() {
        // Define uma chave API mockada para o ambiente de testes
        System.setProperty("SENDGRID_API_KEY", "test-key");
    }
    
	@Test
	void contextLoads() {
	}

}
