package org.example;

// ==== IMPORTS ====
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
// ===================

// ==== AVISO ====
// Antes de executar o programa recomendo que exclua o arquivo produtos.json
// Para visualizar a criação do arquivo ao executar o programa
public class Main {
    // Metodo principal
    // Esse metodo executara todo o processo de scraping
    public static void main(String[] args) {
        // ==== SCRAPING ====
        // Try catch para evitar erros
        try {
            // Conectando ao site e obtendo o documento HTML
            Document doc = Jsoup.connect("https://infosimples.com/vagas/desafio/commercia/product.html")
                    .timeout(0)
                    .get();

            // Extraindo os dados do sites
            String title = doc.select("#product_title").text();
            String brand = doc.select(".brand").text();
            String description = doc.select(".proddet > p").text();
            String url = doc.location();

            // Obtendo as listas de categorias, produtos, propriedades e reviews
            List<String> categories = getCategories(doc);
            List<Products> products = getProducts(doc);
            List<Properties> properties = getProperties(doc);
            List<Reviews> reviews = getReviews(doc);

            float averageScore = getAverageScore(reviews);

            // Criando o objeto de coleção
            ScrapingCollection Scraping = new ScrapingCollection(title, brand, categories, description, products, properties, reviews, averageScore, url);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(Scraping);

            // Escrevendo o JSON em um arquivo
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

    // Metodo para obter a ArrayList das categorias
    // Esse metodo ira retornar uma lista com as categorias do produto
    public static List<String> getCategories(Document doc){
        List<String> categories = new ArrayList<>();
        Elements quantCategories = doc.select(".current-category > a");
        for (Element category : quantCategories){
            categories.add(category.text());
        }
        return categories;
    }

    // Metodo para obter o ArrayList dos produtos
    // Esse metodo ira retornar uma lista com os produtos do site
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

    // Metodo para obter o ArrayList das propriedades
    // Esse metodo ira retornar uma lista com as propriedades do produto
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

    // Metodo para obter o ArrayList das reviews
    // Esse metodo ira retornar uma lista com as reviews do produto
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

    // Metodo para calcular a media das reviews
    // Esse metodo ira retornar a media das reviews do produto
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

    // Metodo para converter o preço
    // Esse metodo ira converter o preço do produto para o formato correto
    public static Float convertNumber(String txtPrice){
        return Float.parseFloat(txtPrice
                .replace("R$", "")
                .replace(",", ".")
        );
    }

    // Metodo para formatar a media
    // Esse metodo ira formatar a media das reviews para o formato correto
    public static float numberFormat(float calcAverage){
        String formatted = String.format("%.1f", calcAverage).replace(",", ".");
        return Float.parseFloat(formatted);
    }
}