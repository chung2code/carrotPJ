package com.example.demo;

import com.example.demo.controller.user.ProductController;
import com.example.demo.domain.user.entity.Product;
import com.example.demo.domain.user.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testReadProduct() throws Exception {
        Long Pno = 3L; // replace with actual product id

        // create a mock product for testing
        Product mockProduct = new Product();
        mockProduct.setPno(Pno);
        // set other properties of the product...

        when(productService.getProductOne(Pno)).thenReturn(mockProduct);

        MockHttpServletRequestBuilder requestBuilder = get("/read").param("Pno", String.valueOf(Pno))
                .cookie(new Cookie("reading", "true"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("boardDto"))
                .andExpect(view().name("user/product/read"));

        verify(productService, times(1)).getProductOne(Pno);
    }
}
