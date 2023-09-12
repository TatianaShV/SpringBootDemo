package ru.netology.springbootdemo;

import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    @Container
    private final GenericContainer<?> devapp = new GenericContainer<>("devapp:1.0")
            .withExposedPorts(8080);
    @Container
    private final GenericContainer<?> prodapp = new GenericContainer<>("prodapp:1.0")
            .withExposedPorts(8081);


    @Test
    void contextLoads() {
        Integer devappPort = devapp.getMappedPort(8080);
        Integer prodappPort = prodapp.getMappedPort(8081);
        ResponseEntity<String> forEntityDev = restTemplate.getForEntity("http://localhost:" + devapp.getMappedPort(8080), String.class);
        System.out.println(forEntityDev.getBody());
        ResponseEntity<String> forEntityProd = restTemplate.getForEntity("http://localhost:" + prodapp.getMappedPort(8081), String.class);
        System.out.println(forEntityProd.getBody());
    }

}