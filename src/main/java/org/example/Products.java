package org.example;

public class Products {
    private String name;
    private float current_price;
    private float old_price;
    private boolean available;

    public Products(String name, float current_price, float old_price, boolean available) {
        this.name = name;
        this.current_price = current_price;
        this.old_price = old_price;
        this.available = available;
    }
}
