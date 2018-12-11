package com.webshop.api.GrpcTest;

import category.*;
import com.google.common.collect.ImmutableList;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CategoryClientTest {

    volatile Long categoryId = null;
    volatile Long productId = null;
    StreamObserver<ApiOperation> response;

    @Test
    public void streamTest() throws InterruptedException {
        final CountDownLatch finishLatch = new CountDownLatch(1);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082)
                .usePlaintext()
                .build();

        CategoryServiceGrpc.CategoryServiceStub stub
                = CategoryServiceGrpc.newStub(channel);

        StreamObserver<ApiOperationResult> jakObsluzyc = new StreamObserver<ApiOperationResult>() {

            @Override
            public void onNext(ApiOperationResult value) {
                switch (value.getOperationCase()) {
                    case CREATEDCATEGORYID:
                        categoryId = value.getCreatedCategoryId();

                        ApiOperation productToSend = ApiOperation.newBuilder().setAddProduct(Product.newBuilder()
                                .setData(ProductData.newBuilder()
                                        .setSlug("test")
                                        .setStatus(Status.live)
                                        .build())
                                .setInfo(ProductInfo.newBuilder()
                                        .setName("Frezarka CNC")
                                        .setDescription("Frezarka")
                                        .build())
                                .build())
                                .build();

                        response.onNext(productToSend); // Dodaj product
                        break;
                    case UPDATEDCATEGORY:
                        categoryId = value.getUpdatedCategory();
                        break;
                    case ADDEDPRODUCT:
                        productId = value.getAddedProduct();
                        ApiOperation addProductToCategory = ApiOperation.newBuilder().setAddProductToCategory(CategoryProduct.newBuilder()
                                .setId(categoryId)
                                .setPid(productId)
                                .build())
                                .build();

                        response.onNext(addProductToCategory);
                        break;
                    case ADDEDPRODUCTTOCATEGORY:
                        if (value.getAddedProductToCategory()) {
                            System.out.print("Product added successfully");
                        }
                        ApiOperation deleteProductToCategory = ApiOperation.newBuilder().setDeleteProductToCategory(CategoryProduct.newBuilder()
                                .setId(categoryId)
                                .setPid(productId)
                                .build())
                                .build();

                        response.onNext(deleteProductToCategory);
                        break;
                    case DELETEDPRODUCTTOCATEGORY:
                        if (value.getDeletedProductToCategory()) {
                            System.out.print("Product deleted successfully");
                        }
                        onCompleted();
                        break;
                    case OPERATION_NOT_SET:
                        break;
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                System.out.print(" Ok, completed");
                finishLatch.countDown();

            }
        };
        response = stub.streamingApiOperations(jakObsluzyc);

        ApiOperation toSend = ApiOperation.newBuilder().setCreateCategory(Category.newBuilder()
                .setData(CategoryData.newBuilder()
                        .setSlug("test")
                        .setStatus(Status.live)
                        .build())
                .setInfo(CategoryInfo.newBuilder()
                        .setName("Frezarki CNC")
                        .setDescription("Frezarki CNC")
                        .build())
                .addProducts(Product.newBuilder().setPid(1).build())
                .build())
                .build();


        response.onNext(toSend);

        if (!finishLatch.await(50, TimeUnit.SECONDS)) {
            throw new RuntimeException("recordRoute cannot finish within 1 minutes");
        }

    }

//    @Test
//    public void sendRequest() {
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082)
//                .usePlaintext()
//                .build();
//
//        CategoryCreateServiceGrpc.CategoryCreateServiceBlockingStub stub
//                = CategoryCreateServiceGrpc.newBlockingStub(channel);
//
//        Category categoryResponse = stub.createCategory(Category.newBuilder()
//                .setData(CategoryData.newBuilder()
//                        .setSlug("test")
//                        .setStatus(Status.live)
//                        .build())
//                .setInfo(CategoryInfo.newBuilder()
//                        .setName("Frezarki CNC")
//                        .setDescription("Frezarki CNC")
//                        .build())
//                .build());
//
//        Category retrievedCategory = stub.getCategory(categoryResponse);
//        Assertions.assertThat(retrievedCategory).isEqualTo(categoryResponse);
//
//        Category deletedCategoryResponse = stub.deleteCategory(categoryResponse);
//        Assertions.assertThat(deletedCategoryResponse).isEqualTo(categoryResponse);
//
//        channel.shutdown();
//    }
}