package org.example.model;

import java.util.List;

// Objetos de coleção dos dados
public class ScrapingCollection {
    private String title;
    private String brand;
    private List<String> categories;
    private String description;
    private List<Products> products;
    private List<Properties> properties;
    private List<Reviews> reviews;
    private float reviews_avarage_score;
    private String url;

    public ScrapingCollection(String title, String brand, List<String> categories, String description, List<Products> products, List<Properties> properties, List<Reviews> reviews, float reviews_avarage_score, String url) {
        this.title = title;
        this.brand = brand;
        this.categories = categories;
        this.description = description;
        this.products = products;
        this.properties = properties;
        this.reviews = reviews;
        this.reviews_avarage_score = reviews_avarage_score;
        this.url = url;
    }
}
