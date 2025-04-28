package org.example.model;

public class Products {
    private String name;
    private Float current_price;
    private Float old_price;
    private boolean available;

    public Products(String name, Float current_price, Float old_price, boolean available) {
        this.name = name;
        this.current_price = current_price;
        this.old_price = old_price;
        this.available = available;
    }

}
