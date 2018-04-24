package com.webshop.controller;

import com.webshop.ProductRepository;
import com.webshop.ProductService;
import com.webshop.builders.ProductBuilder;
import com.webshop.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest{
        private static final Long CHECKED_ITEM_ID = Long.valueOf(1);
        private static final Product CHECKED_ITEM = new ProductBuilder()
                .id(CHECKED_ITEM_ID)
                .name("first product")
                .description("first product description")
                .build();
        private static final Product UNCHECKED_ITEM = new ProductBuilder()
                .id(Long.valueOf(2))
                .name("second product description")
                .description("second product description")
                .build();
        private static final Product NEW_ITEM = new ProductBuilder()
                .name("first product")
                .description("first product description")
                .build();
        @InjectMocks
        private ProductService productService;
        @Mock
        private ProductRepository productRepository;
        private ArgumentCaptor<Product> anyProduct = ArgumentCaptor.forClass(Product.class);

        @Test
        public void whenFindingProductsItShouldReturnAllProducts() {
            // Given that the repository returns CHECKED_ITEM and UNCHECKED_ITEM
            given(productRepository.findAll()).willReturn(Arrays.asList(CHECKED_ITEM, UNCHECKED_ITEM));
            // When looking for all items
            assertThat(productService.getAllProducts())
                    // Then it should return the CHECKED_ITEM and UNCHECKED_ITEM
                    .containsOnly(CHECKED_ITEM, UNCHECKED_ITEM);
        }

        @Test
        public void whenAddingProductItShouldReturnTheSavedProduct() {
            // Given that a NEW_ITEM is saved and flushed, a CHECKED_ITEM is returned
            given(productRepository.save(NEW_ITEM)).willReturn(CHECKED_ITEM);
            // When adding a NEW_ITEM
            assertThat(productService.addProduct(NEW_ITEM))
                    // Then it should return the CHECKED_ITEM
                    .isSameAs(CHECKED_ITEM);
        }

        @Test
        public void whenAddingProductItShouldMakeSureNoIDIsPassed() {
            // Given that a CHECKED_ITEM is added
            productService.addProduct(CHECKED_ITEM);
            // Verify that when the item is saved
            verify(productRepository).save(anyProduct.capture());
            // It should have an empty ID
            assertThat(anyProduct.getValue().getId()).isNull();
        }

        @Test
        public void whenUpdatingProductItShouldReturnTheSavedProduct() {
            // Given that CHECKED_ITEM is returned when one is requested with CHECKED_ITEM_ID
            given(productRepository.findById(CHECKED_ITEM_ID)).willReturn(java.util.Optional.ofNullable(CHECKED_ITEM));
            // Given that a CHECKED_ITEM is saved and flushed, a CHECKED_ITEM is returned
            given(productRepository.save(CHECKED_ITEM)).willReturn(CHECKED_ITEM);
            // When updating a CHECKED_ITEM
            assertThat(productService.updateProduct(CHECKED_ITEM, CHECKED_ITEM_ID))
                    // Then it should return the CHECKED_ITEM
                    .isSameAs(CHECKED_ITEM);
        }

        @Test
        public void whenUpdatingProductItShouldUseTheGivenID() {
            // Given that CHECKED_ITEM is returned when one is requested with CHECKED_ITEM_ID
            given(productRepository.findById(CHECKED_ITEM_ID)).willReturn(java.util.Optional.ofNullable(CHECKED_ITEM));
            // Given that a CHECKED_ITEM with CHECKED_ITEM_ID is updated
            productService.updateProduct(NEW_ITEM, CHECKED_ITEM_ID);
            // Verify that when the item is saved
            verify(productRepository).save(anyProduct.capture());
            // It should have the given CHECKED_ITEM_ID
            assertThat(anyProduct.getValue().getId()).isEqualTo(CHECKED_ITEM_ID);
        }

        @Test
        public void whenDeletingAnItemItShouldUseTheRepository() {
            // Given that an item with CHECKED_ITEM_ID is removed
            productService.deleteProduct(CHECKED_ITEM_ID);
            // Verify that the repository is used to delete the item
            verify(productRepository).deleteById(CHECKED_ITEM_ID);
        }
}
