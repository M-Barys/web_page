package com.webshop;

import category.Category;
import category.CategoryCreateServiceGrpc;
import category.CategoryCreated;
import com.webshop.model.Status;
import com.webshop.model.instance.data.CategoryData;
import com.webshop.services.CategoryService;
import io.grpc.stub.StreamObserver;

public class CategoryCreateServiceImpl extends CategoryCreateServiceGrpc.CategoryCreateServiceImplBase {

 private final CategoryService categoryService;

    public CategoryCreateServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void createCategory(Category request, StreamObserver<CategoryCreated> responseObserver) {


        com.webshop.model.instance.Category cat = com.webshop.model.instance.Category.builder()
                .data(CategoryData.builder().status(Status.live).build())
                .build();
        com.webshop.model.instance.Category created = categoryService.addCategory(cat,false);

        String category = new StringBuilder()
                .append(created.getId())
                .append(request.getData())
                .append(request.getInfo())
                .toString();



        CategoryCreated response = CategoryCreated.newBuilder()
                .setRespose(category)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

}

