package org.example;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.Products;
import org.example.model.Properties;
import org.example.model.Reviews;
import org.example.model.ScrapingCollection;
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

            List<String> categories = getCategories(doc);
            List<Products> products = getProducts(doc);
            List<Properties> properties = getProperties(doc);
            List<Reviews> reviews = getReviews(doc);

            float averageScore = getAverageScore(reviews);

            ScrapingCollection Scrapping = new ScrapingCollection(title, brand, categories, description, products, properties, reviews, averageScore, url);

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

    public static List<String> getCategories(Document doc){
        List<String> categories = new ArrayList<>();
        Elements quantCategories = doc.select(".current-category > a");
        for (Element category : quantCategories){
            categories.add(category.text());
        }
        return categories;
    }

    public static List<Products> getProducts(Document doc){
        List<Products> products = new ArrayList<>();

        // Extraindo produtos
        Elements cardContainer = doc.select(".card-container");
        for (Element card : cardContainer){
            String name = card.select(".prod-nome").text();
            Float current_price;
            Float old_price;
            boolean available = true;

            String txtCurrentPrice = card.select(".prod-pnow").text();
            String txtOldPrice = card.select(".prod-pold").text();

            if(!txtCurrentPrice.trim().isEmpty()){
                current_price = convertNumber(txtCurrentPrice);
            } else {
                current_price = null;
            }

            if(!txtOldPrice.trim().isEmpty()){
                old_price = convertNumber(txtOldPrice);
            } else {
                old_price = null;
            }

            if(card.parent().hasClass("not-avaliable")){
                available = false;
            }

            products.add(new Products(name, current_price, old_price, available));
        }
        return products;
    }

    public static List<Properties> getProperties(Document doc){
        List<Properties> properties = new ArrayList<>();
        Elements tables = doc.select(".pure-table");
        tables = tables.getFirst().select("tbody tr");
        for (Element column : tables){
            String  label = column.select("td").getFirst().text();
            String value = column.select("td").get(1).text();

            properties.add(new Properties(label, value));
        }

        return properties;
    }

    public static List<Reviews> getReviews(Document doc){
        List<Reviews> reviews = new ArrayList<>();
        Elements analysisBoxes = doc.select(".analisebox");
        for (Element box : analysisBoxes){
            String userName = box.select(".analiseusername").text();
            String date = box.select(".analisedate").text();
            String text = box.select("p").text();
            int score;

            //  ★★★★☆
            String stars = box.select(".analisestars").text().replace("☆", "");
            score = stars.length();

            reviews.add(new Reviews(userName, date, score, text));
        }
        return reviews;
    }

    public static float getAverageScore(List<Reviews> reviews){
        float averageScore;
        float totalScore = 0;

        for (Reviews review : reviews){
            totalScore += review.getScore();
        }

        if(!reviews.isEmpty()){
            float calcAverage = totalScore / reviews.size();
            averageScore =  numberFormat(calcAverage);
        } else {
            averageScore = 0.0f;
        }

        return averageScore;
    }

    public static Float convertNumber(String txtPrice){
        return Float.parseFloat(txtPrice
                .replace("R$", "")
                .replace(",", ".")
        );
    }

    public static float numberFormat(float calcAverage){
        String formatted = String.format("%.1f", calcAverage).replace(",", ".");
        return Float.parseFloat(formatted);
    }
}