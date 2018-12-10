package com.webshop;

import category.*;
import com.google.common.collect.ImmutableList;
import com.webshop.model.instance.data.CategoryData;
import com.webshop.model.instance.info.CategoryInfo;
import com.webshop.services.CategoryService;
import io.grpc.stub.StreamObserver;

import java.util.List;


public class CategoryCreateServiceImpl extends CategoryCreateServiceGrpc.CategoryCreateServiceImplBase {

    private final CategoryService categoryService;

    public CategoryCreateServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void createCategory(Category category, StreamObserver<Category> responseObserver) {
        com.webshop.model.instance.Category categoryInstance = buildCategoryInstance(category);
        com.webshop.model.instance.Category created = categoryService.addCategory(categoryInstance, false);
        sendResponse(created, responseObserver);
    }

    @Override
    public void getCategory(Category category, StreamObserver<Category> responseObserver) {
        com.webshop.model.instance.Category retrievedCategory = categoryService.getCategory(category.getId());
        sendResponse(retrievedCategory, responseObserver);
    }

    @Override
    public void updateCategory(Category category, StreamObserver<Category> responseObserver) {
        com.webshop.model.instance.Category categoryInstance = buildCategoryInstance(category);
        com.webshop.model.instance.Category updateCategory = categoryService.updateCategory(categoryInstance);
        sendResponse(updateCategory, responseObserver);
    }

    @Override
    public void deleteCategory(Category category, StreamObserver<Category> responseObserver) {
        com.webshop.model.instance.Category deletedCategory = categoryService.deleteCategoryPicture(category.getId());
        sendResponse(deletedCategory, responseObserver);

    }

    @Override
    public void addProductToCategory(CategoryProduct categoryProduct, StreamObserver<Category> responseObserver){
        com.webshop.model.instance.Category categoryWithProduct = categoryService.addProductToCategory(categoryProduct
                .getId(), categoryProduct.getPid());

    }

    private com.webshop.model.instance.Category buildCategoryInstance(Category request) {
        return com.webshop.model.instance.Category.builder()
                .data(CategoryData.builder()
                        .slug(request.getData().getSlug())
                        .status(mapStatus(request.getData().getStatus()))
                        .build())
                .info(CategoryInfo.builder()
                        .name(request.getInfo().getName())
                        .description(request.getInfo().getDescription())
                        .build())
                .products(ImmutableList.of())
                .picture(null)
                .build();
    }

    private com.webshop.model.Status mapStatus(Status status) {
        if (status.getNumber() == Status.live_VALUE) {
            return com.webshop.model.Status.live;
        }
        return com.webshop.model.Status.draft;
    }

    private void sendResponse(com.webshop.model.instance.Category retrievedCategory,
                              StreamObserver<Category> responseObserver) {

        Category.Builder response = Category.newBuilder()
                .setId(retrievedCategory.getId())
                .setData(category.CategoryData.newBuilder()
                        .setSlug(retrievedCategory.getData().getSlug())
                        .setStatus(mapResponseStatus(retrievedCategory.getData().getStatus()))
                        .build())
                        .setInfo(category.CategoryInfo.newBuilder()
                        .setName(retrievedCategory.getInfo().getName())
                        .setDescription(retrievedCategory.getInfo().getDescription()));

//        List<com.webshop.model.instance.Product> products = retrievedCategory.getProducts();
//        if( products != null) {
//            products.forEach(p -> {
//                response.addProducts(null);
//            });
//        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    private Status mapResponseStatus(com.webshop.model.Status status) {
        if (status.equals(com.webshop.model.Status.live)){
            return Status.live;
        }
        return Status.draft;
    }
}

