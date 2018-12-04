package com.webshop.api.data;

import category.Category;
import category.CategoryCreateServiceGrpc;
import category.CategoryCreated;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.Test;

public class ClientTest {

    @Test
    public void sendRequest(){
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082)
            .usePlaintext()
            .build();

    CategoryCreateServiceGrpc.CategoryCreateServiceBlockingStub stub
            = CategoryCreateServiceGrpc.newBlockingStub(channel);

        CategoryCreated categoryResponse = stub.createCategory(Category.newBuilder()
                .setId(1L)
                .setData("gRPC")
                .build());

        channel.shutdown();
}
}
