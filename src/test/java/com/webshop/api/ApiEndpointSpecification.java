package com.webshop.api;

public class ApiEndpointSpecification {
    public static String categoriesEndpoint = "/categories";
    public static String categoriesTreeEndpoint = "/categories/tree";
    public static String categoryByIDEndpoint = "/categories/{id}";
    public static String categoryByIDRelationEndpoint = "/categories/{mainCategoryId}/relationships/categories";

}
