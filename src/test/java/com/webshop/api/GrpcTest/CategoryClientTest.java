package com.webshop.api.GrpcTest;

import category.*;
import com.google.common.collect.ImmutableList;
import com.webshop.repositories.CategoryRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.assertj.AssertableWebApplicationContext;

import java.util.List;

public class CategoryClientTest {


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