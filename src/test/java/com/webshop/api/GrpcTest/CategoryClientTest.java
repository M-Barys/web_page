package com.webshop.api.GrpcTest;

import category.*;
import com.webshop.repositories.CategoryRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryClientTest {


//    @Test
//    public void sendRequest() {
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082)
//                .usePlaintext()
//                .build();
//
//        CategoryCreateServiceGrpc.CategoryCreateServiceBlockingStub stub
//                = CategoryCreateServiceGrpc.newBlockingStub(channel);
//
//        CategoryCreated categoryResponse = stub.createCategory(Category.newBuilder()
//                .setId(1L)
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
//        CategoryCreated deletedCategoryResponse = stub.deleteCategory(Category.newBuilder()
//                .setId(1L)
//                .build());
//
//
//        channel.shutdown();
//    }
}