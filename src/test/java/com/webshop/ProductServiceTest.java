package com.webshop;


import com.webshop.model.Product;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testProductAddict(){
        Product product = new Product();
        product.setName("test");
        product.setDescription("test destric");

        productService.addProduct(product);

        System.out.println(productService.getAllProducts());

        Assertions.assertThat(productService.getAllProducts()).hasSize(1);
    }
}
