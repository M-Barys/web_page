package com.webshop.api.GrpcTest;

import category.*;
import com.google.common.collect.ImmutableList;
import com.webshop.repositories.CategoryRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.assertj.AssertableWebApplicationContext;

import java.util.List;
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

        CategoryCreateServiceGrpc.CategoryCreateServiceStub stub
                = CategoryCreateServiceGrpc.newStub(channel);


        StreamObserver<ApiOperationResult> jakObsluzyc = new StreamObserver<ApiOperationResult>() {
            @Override
            public void onNext(ApiOperationResult value) {
                switch(value.getOperationCase()){
                    case CREATEDCATEGORYID:
                        categoryId = value.getCreatedCategoryId();
                        response.onNext(null); // Dodaj product
                        break;
                    case UPDATEDCATEGORY:
                        break;
                    case ADDEDPRODUCT:
                        break;
                    case ADDEDPRODUCTTOCATEGORY:
                        System.out.print(" Hurra");
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

        ApiOperation toSend = null;
        response.onNext(toSend);
        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
            throw new RuntimeException("recordRoute can not finish within 1 minutes");
        }



    }

    @Test
    public void sendRequest() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082)
                .usePlaintext()
                .build();

        CategoryCreateServiceGrpc.CategoryCreateServiceBlockingStub stub
                = CategoryCreateServiceGrpc.newBlockingStub(channel);

        Category categoryResponse = stub.createCategory(Category.newBuilder()
                .setData(CategoryData.newBuilder()
                        .setSlug("test")
                        .setStatus(Status.live)
                        .build())
                .setInfo(CategoryInfo.newBuilder()
                        .setName("Frezarki CNC")
                        .setDescription("Frezarki CNC")
                        .build())
                .build());

        Category retrievedCategory = stub.getCategory(categoryResponse);
        Assertions.assertThat(retrievedCategory).isEqualTo(categoryResponse);

        Category deletedCategoryResponse = stub.deleteCategory(categoryResponse);
        Assertions.assertThat(deletedCategoryResponse).isEqualTo(categoryResponse);


        channel.shutdown();
    }
}