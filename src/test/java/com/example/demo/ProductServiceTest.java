package com.example.demo;

import com.example.demo.domain.user.entity.Product;
import com.example.demo.domain.user.repository.ProductRepository;
import com.example.demo.domain.user.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductOne() {
        Long Pno = 1L; // replace with actual product id

        // create a mock product for testing
        Product mockProduct = new Product();
        mockProduct.setPno(Pno);
        // set other properties of the product...

        when(productRepository.findById(Pno)).thenReturn(Optional.of(mockProduct));

        Product returnedProduct = productService.getProductOne(Pno);

        assertNotNull(returnedProduct, "Returned null product");
        assertEquals(mockProduct, returnedProduct, "The returned product does not match the expected");

        verify(productRepository, times(1)).findById(Pno);

        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        assertNull(productService.getProductOne(2L), "Expected null when no matching product found");
        verify(productRepository, times(1)).findById(2L);
    }
}
