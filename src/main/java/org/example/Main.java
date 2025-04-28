package org.example;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            Document doc = Jsoup.connect("https://infosimples.com/vagas/desafio/commercia/product.html")
                    .timeout(0)
                    .get();

            String title = doc.select("#product_title").text();
            String brand = doc.select(".brand").text();
            String description = doc.select(".proddet > p").text();
            String url = doc.location();

            List<String> categories = new ArrayList<>();
            List<Products> products = new ArrayList<>();
            List<Properties> properties = new ArrayList<>();
            List<Reviews> reviews = new ArrayList<>();

            float avarageScore;
            float totalScore = 0;

            Elements quantCategories = doc.select(".current-category > a");
            for (Element category : quantCategories){
                categories.add(category.text());
                System.out.println(category.text());
            }

//            for (int i = 0; i < quantCategories.size(); i++){
//                String categoria = quantCategories.get(i).text();
//                categories.add(categoria);
//            }

            Elements cardContainer = doc.select(".card-container");
            for (Element card : cardContainer){
                String name = card.select(".prod-nome").text();
                Float current_price;
                Float old_price;
                boolean available = true;

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
                }

                products.add(new Products(name, current_price, old_price, available));
            }

            Elements tables = doc.select(".pure-table");
            tables = tables.getFirst().select("tbody tr");
            for (Element column : tables){
                String  label = column.select("td").getFirst().text();
                String value = column.select("td").get(1).text();

                properties.add(new Properties(label, value));
            }

            Elements analysisBoxes = doc.select(".analisebox");
            for (Element box : analysisBoxes){
                String userName = box.select(".analiseusername").text();
                String date = box.select(".analisedate").text();
                String text = box.select("p").text();
                int score;

                //  ★★★★☆
                String stars = box.select(".analisestars").text().replace("☆", "");
                score = stars.length();
                totalScore += score;

                reviews.add(new Reviews(userName, date, score, text));

                System.out.println("Nome Usuário: " + userName);
                System.out.println("Data: " + date);
                System.out.println("Estrelas: " + stars);
                System.out.println("Quantidade de estrelas: " + score);
                System.out.println("Descrição: " + text);

            }

            if(!reviews.isEmpty()){
                float calcAverage = totalScore / reviews.size();
                String formatted = String.format("%.1f", calcAverage).replace(",", ".");
                avarageScore =  Float.parseFloat(formatted);
            } else {
                avarageScore = 0.0f;
            }

            ScrapingCollection Scrapping = new ScrapingCollection(title, brand, categories, description, products, properties, reviews, avarageScore, url);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(Scrapping);

            try {
                FileWriter writer = new FileWriter("src/main/java/org/example/produtos.json");
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(json);
            System.out.println("Informações extraidas com sucesso ✅");
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Não foi possivel obter o link da pagina HTML ❌");
        }

    }
}
