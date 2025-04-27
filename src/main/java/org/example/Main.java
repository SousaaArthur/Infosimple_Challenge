package org.example;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            String title;
            String brand;
            List<String> categories = new ArrayList<>();
            String description;

            List<Products> products = new ArrayList<>();
            String name;
            Float current_price;
            Float old_price;
            boolean available = true;


            Document doc = Jsoup.connect("https://infosimples.com/vagas/desafio/commercia/product.html")
                    .timeout(0)
                    .get();

            title = doc.select("#product_title").text();
            brand = doc.select(".brand").text();
            description = doc.select(".proddet > p").text();

            Elements quantCategories = doc.select(".current-category > a");
            Elements cardContainer = doc.select(".card-container");

            for (int i = 0; i < quantCategories.size(); i++){
                String categoria = quantCategories.get(i).text();
                categories.add(categoria);
            }

            for (Element card : cardContainer){
                name = card.select(".prod-nome").text();
                String txtCurrentPrice = card.select(".prod-pnow").text();
                String txtOldPrice = card.select(".prod-pold").text();

                if(!txtCurrentPrice.trim().isEmpty()){
                    current_price = Float.parseFloat(txtCurrentPrice
                            .replace("R$", "")
                            .replace(",", ".")
                    );
                } else {
                    current_price = null;
                }

                if(!txtOldPrice.trim().isEmpty()){
                    old_price = Float.parseFloat(txtOldPrice
                            .replace("R$","")
                            .replace(",", ".")
                    );
                } else {
                    old_price = null;
                }

                if(card.parent().hasClass("not-avaliable")){
                    available = false;
                }  else {
                    available = true;
                }

                products.add(new Products(name, current_price, old_price, available));
            }

            System.out.println("Informações extraidas com sucesso ✅");

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Não foi possivel obter o link da pagina HTML ❌");
        }

    }
}