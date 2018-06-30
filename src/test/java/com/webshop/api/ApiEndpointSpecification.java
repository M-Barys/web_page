package com.webshop.api;

public class ApiEndpointSpecification {
    public static String categoriesEndpoint = "/categories";
    public static String categoriesTreeEndpoint = "/categories/tree";
    public static String categoryByIDEndpoint = "/categories/{id}";
    public static String categoryByIDRelationEndpoint = "/categories/{mainCategoryId}/relationships/categories";
    public static String categoryByIDAddProductEndpoint = "/categories/{id}/addProduct";
    public static String categoryByIDAddDeleteEndpoint = "/categories/{id}/deleteProduct";

    public static String productEndpoint = "/products";
    public static String productByIDEndpoint = "/products/{id}";
    public static String productByIDAddPictureEndpoint = "/products/{id}/addPicture";
    public static String productByIDDeletePictureEndpoint = "/products/{id}/deletePicture";

    public static String pictureEndpoint = "/pictures";
    public static String pictureByIDEndpoint = "/pictures/{id}";
}