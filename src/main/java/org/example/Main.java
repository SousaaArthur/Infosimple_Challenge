package org.example;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String title;
        String brand;
        List<String> categories = new ArrayList<>();
        String description;

        try {
            Document doc = Jsoup.connect("https://infosimples.com/vagas/desafio/commercia/product.html")
                    .timeout(0)
                    .get();

            title = doc.select("#product_title").text();
            brand = doc.select(".brand").text();

            Elements quantCategories = doc.select(".current-category > a");

            description = doc.select(".proddet > p").text();


            for (int i = 0; i < quantCategories.size(); i++){
                String categoria = quantCategories.get(i).text();
                categories.add(categoria);
                System.out.println(categoria);
            }

                System.out.println(
                   "Titulo: " + title +
                   "\nMarca: " + brand +
                   "\nDescrição: " + description
            );

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Não foi possivel obter o link da pagina HTML ❌");
        }

    }
}