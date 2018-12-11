package com.webshop.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.Streams;
import com.webshop.model.PictureFileType;
import com.webshop.model.entity.CategoryEntity;
import com.webshop.model.entity.PictureEntity;
import com.webshop.model.entity.ProductEntity;
import com.webshop.model.instance.Category;
import com.webshop.model.instance.PictureRef;
import com.webshop.model.instance.Product;
import com.webshop.model.mapping.CategoryMapping;
import com.webshop.model.mapping.CategoryMappingWithDefault;
import com.webshop.model.mapping.CategoryMappingWithLanguage;
import com.webshop.repositories.CategoryRepository;
import com.webshop.repositories.PictureRepository;
import com.webshop.repositories.ProductRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryMappingWithDefault mapping;

    @Autowired
    private CategoryMappingWithLanguage categoryMappingWithLanguage;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PictureRepository pictureRepository;

    public List<Category> getAllCategories() {
        return Streams.stream(categoryRepository.findAll())
                .map(e -> mapping.fromEntity(e))
                .collect(Collectors.toList());
    }

    public Category getCategory(Long id) {
        Optional<CategoryEntity> found = categoryRepository.findById(id);
        return mapping.fromEntity(found.get());
    }

    public Category addCategory(Category category) {
        return addCategory(category, true);
    }

    @Transactional
    public Category addCategory(Category category, boolean onRequest) {
        Preconditions.checkArgument(category.getId() == null, "A new product can not have a ID setup");

        CategoryMapping mappingToUse;
        if (onRequest) {
            mappingToUse = categoryMappingWithLanguage;
        } else {
            mappingToUse = mapping;
        }
        CategoryEntity entity = mappingToUse.createEntity(category);
        CategoryEntity stored = categoryRepository.save(entity);
        return mappingToUse.fromEntity(stored);

    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category updateCategory(Category category) {
        Preconditions.checkArgument(category.getId() != null, "Only update products already inserted with an valid ID");
        CategoryEntity oldValue = categoryRepository.findById(category.getId()).get();
        CategoryEntity updated = mapping.updateEntity(oldValue, category);
        CategoryEntity stored = categoryRepository.save(updated);
        return mapping.fromEntity(stored);
    }

    @Transactional
    public Category addProductToCategory(Long id, Long productID) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).get();
        List<ProductEntity> newProductList = categoryEntity.getProductEntities();
        newProductList.add(productRepository.findById(productID).get());
        categoryEntity.setProductEntities(newProductList);
        return mapping.fromEntity(categoryRepository.save(categoryEntity));
    }

    @Transactional
    public Category deleteProduct(Long id, Long productID) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).get();
        List<ProductEntity> newProductList = categoryEntity.getProductEntities();
        newProductList.remove(productRepository.findById(productID).get());
        categoryEntity.setProductEntities(newProductList);
        return mapping.fromEntity(categoryRepository.save(categoryEntity));
    }

    public Category setupCategoryPicture(Long id, Long pictureID) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).get();
        categoryEntity.setPictureEntity(pictureRepository.findById(pictureID).get());
        return mapping.fromEntity(categoryRepository.save(categoryEntity));
    }

    public Category deleteCategoryPicture(Long cid) {
        CategoryEntity categoryEntity = categoryRepository.findById(cid).get();
        categoryEntity.setPictureEntity(null);
        return mapping.fromEntity(categoryRepository.save(categoryEntity));
    }

}
