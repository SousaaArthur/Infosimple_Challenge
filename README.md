# 🚀 Infosimples - Take-Home Coding Challenge

Este projeto é a solução para o desafio técnico da **Infosimples**.

Consiste na criação de um **web scraper** que extrai informações de um produto de uma página web e gera um arquivo **JSON** estruturado com os dados solicitados.

---
## 🎯 Relatório Técnico
Este código foi inteiramente desenvolvido por mim, utilizando todos os conhecimentos adquiridos ao longo dos meus estudos e práticas.

Durante o desenvolvimento, apliquei princípios de código limpo para facilitar a leitura, manter a organização e garantir a legibilidade do projeto.

Além disso, para reforçar meu aprendizado utlizei consultas e boas práticas de uso das bibliotecas, utilizei as seguintes referências:

### 📚 Documentações oficiais:
[Documentação oficial do JSoup](https://jsoup.org/apidocs/)

[JSoup na Oracle](https://www.oracle.com/corporate/features/jsoup-html-parsing-library.html)

[Documentação oficial do GSon](https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.5/com/google/gson/Gson.html)

### 🌐 Sites de consulta:
[DevMedia](https://www.devmedia.com.br/como-converter-objetos-java-para-ou-de-json-com-a-biblioteca-gson/28091) - Como converter objetos Java para JSON com Gson

[StackOverflow](https://stackoverflow.com/) - Comunidade de soluções e dúvidas técnicas

---

## 📜 Sobre o desafio

O objetivo do teste técnico era:

- Acessar a página:  
  [https://infosimples.com/vagas/desafio/commercia/product.html](https://infosimples.com/vagas/desafio/commercia/product.html)
- Extrair as seguintes informações:
  - **title**: Título principal do produto
  - **brand**: Marca do produto
  - **categories**: Lista de categorias
  - **description**: Descrição detalhada
  - **products (skus)**: Lista de variações do produto contendo nome, preço atual, preço antigo e disponibilidade
  - **properties**: Lista de propriedades (ex: material, tamanho, peso)
  - **reviews**: Lista de avaliações (nome, data, nota e texto)
  - **reviews_average_score**: Nota média das avaliações
  - **url**: URL da página do produto

Todo o conteúdo extraído devera ser salvo em um arquivo **JSON**.

---

## 🛠️ Tecnologias utilizadas

- **Java 24**
- **JSoup** - para realizar o web scraping
- **Gson** - para gerar e estruturar o arquivo JSON
- **Maven** - para gerenciamento de dependências

---

## 🧩 Estrutura do projeto

```
    |_org/example // Pasta do projeto
    |  |_model
    |  |_Products.java // Modelo de dados para os produtos (skus)
    |  |_produtos.json // Modelo de dados para propriedades do produto
    |  |_Properties.java // Modelo de dados para avaliações
    |  |_Reviews.java // Classe que agrupa todas as informações extraídas /output/
    |_Main.java // Classe principal que realiza a extração
    |_ScrapingCollection.java // Arquivo JSON gerado com os dados do produto
```

---

## ⚡ Como executar

1. Clone este repositório:
   ```bash
   git clone https://github.com/SousaaArthur/Infosimple_Challenge.git
   cd Infosimple_Challenge
   ```
2. Execute o projeto usando sua IDE de preferência (IntelliJ, Eclipse, etc) ou via terminal Maven:
   ```bash
   mvn compile
   mvn exec:java -Dexec.mainClass="org.example.Main"
   ```
3. O arquivo ``InformationCollected.json`` será gerado no caminho especificado no código.
---
## 📂 Arquivo gerado
O JSON gerado segue exatamente o modelo especificado no desafio, contendo todos os campos e estrutura exigidos.

Exemplo de estrutura final:
   ```json
  {
    "title": "Rubber Duck MK Ultra",
    "brand": "Duck Makers Inc.",
    "categories": ["Commercia", "Health & Care", "Bath", "Rubber Ducks"],
    "description": "...",
    "products": [...],
    "properties": [...],
    "reviews": [...],
    "reviews_average_score": 3.3,
    "url": "https://infosimples.com/vagas/desafio/commercia/product.html"
  }
   ```
---
## 🧠 Observações
- Campos que não possuem valores (por exemplo, current_price ou old_price em produtos indisponíveis) não são incluídos no JSON, mantendo o arquivo leve e limpo.
- Foi implementado controle para evitar divisão por zero no cálculo da média das avaliações.
- Tratamentos de erro (try-catch) foram utilizados para garantir a execução segura mesmo em casos de problemas de conexão ou leitura de elementos.
---
⚡ Contato
Caso queira entrar em contato:
- LinkedIn: [LinkedIn](https://www.linkedin.com/in/arthurrsousa/)
- GitHub: [GitHub](https://github.com/SousaaArthur)
---

## 🙏 Agradecimento

Agradeço à equipe da **Infosimples** pela oportunidade de participar deste desafio técnico.  
Foi uma experiência incrível colocar em prática minhas habilidades de web scraping, organização de dados e desenvolvimento em Java.

Estou sempre em busca de novos desafios para evoluir, aprender e contribuir para projetos que valorizam a tecnologia e a inovação.

Muito obrigado pela atenção!

---
