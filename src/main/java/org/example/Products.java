package org.example;

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

    public String getName() {
        return name;
    }

    public Float getCurrent_price() {
        return current_price;
    }

    public Float getOld_price() {
        return old_price;
    }

    public boolean isAvailable() {
        return available;
    }

}
