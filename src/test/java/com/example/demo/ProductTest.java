package com.example.demo;

import com.example.demo.domain.user.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
    }

    @Test
    public void testDirPath() {
        String expectedDirPath = "/path/to/the/directory";
        product.setDirPath(expectedDirPath);

        assertEquals(expectedDirPath, product.getDirPath());
    }
}
