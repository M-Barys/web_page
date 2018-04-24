package com.webshop.controller;

import com.webshop.ProductRepository;
import com.webshop.ProductService;
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
public class ProductServiceTest {

    private Product getChecked(Long id) {
        return Product.builder()
                .id(id)
                .name("first product")
                .description("first product description")
                .build();
    }

    private Product getUnchecked() {
        return Product.builder()
                .id(Long.valueOf(2))
                .name("second product description")
                .description("second product description")
                .build();
    }

    private Product getNewItem() {
        return Product.builder()
                .name("first product")
                .description("first product description")
                .build();
    }

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    private ArgumentCaptor<Product> anyProduct = ArgumentCaptor.forClass(Product.class);

    @Test
    public void whenFindingProductsItShouldReturnAllProducts() {
        // Given that the repository returns CHECKED_ITEM and UNCHECKED_ITEM
        Product checked = getChecked(1L);
        Product unchecked = getUnchecked();
        given(productRepository.findAll()).willReturn(Arrays.asList(checked, unchecked));
        // When looking for all items
        assertThat(productService.getAllProducts())
                // Then it should return the CHECKED_ITEM and UNCHECKED_ITEM
                .containsOnly(checked, unchecked);
    }

    @Test
    public void whenAddingProductItShouldReturnTheSavedProduct() {
        // Given that a NEW_ITEM is saved and flushed, a CHECKED_ITEM is returned
        Product newItem = getNewItem();
        Product checked = getChecked(2L);
        given(productRepository.save(newItem)).willReturn(checked);
        // When adding a NEW_ITEM
        assertThat(productService.addProduct(newItem))
                // Then it should return the CHECKED_ITEM
                .isEqualTo(checked)
                .isEqualToComparingFieldByField(checked);

    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddingProductItShouldMakeSureNoIDIsPassed() {
        // Given that a CHECKED_ITEM is added
        Product checked = getChecked(3L);
        productService.addProduct(checked);
        // Then exception expected
    }

    @Test
    public void whenUpdatingProductItShouldReturnTheSavedProduct() {
        // Given that CHECKED_ITEM is returned when one is requested with CHECKED_ITEM_ID
        long id = 4L;
        Product checked = getChecked(id);
        // Given that a CHECKED_ITEM is saved and flushed, a CHECKED_ITEM is returned
        given(productRepository.save(checked)).willReturn(checked);
        // When updating a CHECKED_ITEM
        assertThat(productService.updateProduct(checked))
                // Then it should return the CHECKED_ITEM
                .isEqualTo(checked);
    }

    @Test
    public void whenUpdatingProductItShouldUseTheGivenID() {
        // Given that CHECKED_ITEM is returned when one is requested with CHECKED_ITEM_ID
        long id = 5L;
        Product checked = getChecked(id);
        // Given that a CHECKED_ITEM with CHECKED_ITEM_ID is updated
        productService.updateProduct(checked);
        // Verify that when the item is saved
        verify(productRepository).save(anyProduct.capture());
        // It should have the given CHECKED_ITEM_ID
        assertThat(anyProduct.getValue().getId()).isEqualTo(id);
    }

    @Test
    public void whenDeletingAnItemItShouldUseTheRepository() {
        // Given that an item with CHECKED_ITEM_ID is removed
        long id = 6L;
        productService.deleteProduct(id);
        // Verify that the repository is used to delete the item
        verify(productRepository).deleteById(id);
    }
}
