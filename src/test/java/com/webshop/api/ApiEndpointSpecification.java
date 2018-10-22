package com.webshop.api;

public class ApiEndpointSpecification {
    public static String categoriesEndpoint = "/api/categories";
    public static String categoriesTreeEndpoint = "/api/categories/tree";
    public static String categoryByIDEndpoint = "/api/categories/{id}";
    public static String categoryByIDRelationEndpoint = "/api/categories/{mainCategoryId}/relationships/categories";
    public static String categoryByIDAddProductEndpoint = "/api/categories/{id}/addProduct";
    public static String categoryByIDAddDeleteEndpoint = "/api/categories/{id}/deleteProduct";

    public static String productEndpoint = "/api/products";
    public static String productByIDEndpoint = "/api/products/{id}";
    public static String productByIDAddPictureEndpoint = "/api/products/{id}/addPicture";
    public static String productByIDDeletePictureEndpoint = "/api/products/{id}/deletePicture";

    public static String pictureEndpoint = "/api/pictures";
    public static String pictureByIDEndpoint = "/api/pictures/{id}";
    public static String pictureFileRetrievalJPG = "/api/pictures/{id}/image.jpg";
}