package com.webshop;

import category.*;
import com.google.common.collect.ImmutableList;
import com.webshop.model.instance.data.CategoryData;
import com.webshop.model.instance.data.ProductData;
import com.webshop.model.instance.info.CategoryInfo;
import com.webshop.services.CategoryService;
import com.webshop.services.ProductService;
import io.grpc.stub.StreamObserver;

import java.util.List;


public class CategoryServiceImpl extends CategoryServiceGrpc.CategoryServiceImplBase {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public StreamObserver<ApiOperation> streamingApiOperations(StreamObserver<ApiOperationResult> responseObserver) {
        return new StreamObserver<ApiOperation>() {

            @Override
            public void onNext(ApiOperation value) {
                switch (value.getOperationCase()) {
                    case CREATECATEGORY:
                        com.webshop.model.instance.Category categoryInstance = buildCategoryInstance(value.getCreateCategory());
                        com.webshop.model.instance.Category created = categoryService.addCategory(categoryInstance, false);
                        ApiOperationResult categoryToSend = ApiOperationResult.newBuilder().setCreatedCategoryId(created.getId()).build();
                        responseObserver.onNext(categoryToSend);
                        break;
                    case UPDATECATEGORY:
                        com.webshop.model.instance.Category updatedCategoryInstance = buildCategoryInstance(value.getCreateCategory());
                        com.webshop.model.instance.Category updatedCategory = categoryService.updateCategory(updatedCategoryInstance);
                        ApiOperationResult updatedCategoryToSend = ApiOperationResult.newBuilder().setUpdatedCategory(updatedCategory.getId()).build();
                        responseObserver.onNext(updatedCategoryToSend);
                        break;
                    case ADDPRODUCT:
                        com.webshop.model.instance.Product productInstance = buildProductInstance(value.getAddProduct());
                        com.webshop.model.instance.Product productCreated = productService.addProduct(productInstance, false);
                        ApiOperationResult productToSend = ApiOperationResult.newBuilder().setAddedProduct(productCreated.getId()).build();
                        responseObserver.onNext(productToSend);
                        break;
                    case ADDPRODUCTTOCATEGORY:
                        com.webshop.model.instance.Category categoryWithProduct = categoryService
                                .addProductToCategory(value.getAddProductToCategory().getId(), value.getAddProductToCategory().getPid());
                        ApiOperationResult categoryPicture = ApiOperationResult.newBuilder().setAddedProductToCategory(true).build();
                        responseObserver.onNext(categoryPicture);
                        break;
                    case DELETEPRODUCTTOCATEGORY:
                        com.webshop.model.instance.Category categoryWoProduct = categoryService
                                .deleteProduct(value.getDeleteProductToCategory().getId(), value.getDeleteProductToCategory().getPid());
                        ApiOperationResult categoryWoPicture = ApiOperationResult.newBuilder().setDeletedProductToCategory(true).build();
                        responseObserver.onNext(categoryWoPicture);
                        break;
                    case OPERATION_NOT_SET:
                        break;
                }

            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
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
    public void addProductToCategory(CategoryProduct categoryProduct, StreamObserver<Category> responseObserver) {
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
                //.picture(null)
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
                        .setDescription(retrievedCategory.getInfo().getDescription()))
                .setPicture(category.PictureUrlInfo.newBuilder()
                        .setPictureUrlPath(retrievedCategory.getPicture().getPictureUrlPath())
                        .setAlternative(retrievedCategory.getPicture().getPictureUrlPath()));

        List<com.webshop.model.instance.Product> products = retrievedCategory.getProducts();
        if (products != null) {
            products.forEach(p -> {
                response.addProducts(mapProduct(p));
            });
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    private Product mapProduct(com.webshop.model.instance.Product p) {
        Product productGrpc = Product.newBuilder()
                .setPid(p.getId())
                .build();
        return productGrpc;
    }

    private Status mapResponseStatus(com.webshop.model.Status status) {
        if (status.equals(com.webshop.model.Status.live)) {
            return Status.live;
        }
        return Status.draft;
    }

    private com.webshop.model.instance.Product buildProductInstance(Product addProduct) {

        return com.webshop.model.instance.Product.builder()
                .data(ProductData.builder()
                        .slug(addProduct.getData().getSlug())
                        .status(mapStatus(addProduct.getData().getStatus()))
                        .build())
                .info(com.webshop.model.instance.info.ProductInfo.builder()
                        .name(addProduct.getInfo().getName())
                        .description(addProduct.getInfo().getDescription())
                        //.parameters()
                        .build())
                //.products(ImmutableList.of())
                //.picture(null)
                .build();
    }
}