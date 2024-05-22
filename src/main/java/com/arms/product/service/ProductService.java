package com.arms.product.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import com.arms.product.model.Product;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class ProductService {
    @Inject
    RestClient restClient; 

    public void index(Product product) throws IOException {
        Request request = new Request(
                "PUT",
                "/products/_doc/" + product.getProductId()); 
        request.setJsonEntity(JsonObject.mapFrom(product).toString()); 
        restClient.performRequest(request); 
    }

    // public Product get(String id) throws IOException {
    //     Request request = new Request(
    //             "GET",
    //             "/products/_doc/" + id);
    //     Response response = restClient.performRequest(request);
    //     String responseBody = EntityUtils.toString(response.getEntity());
    //     JsonObject json = new JsonObject(responseBody); 
    //     return json.getJsonObject("_source").mapTo(Product.class);
    // }

    // public List<Product> searchByColor(String color) throws IOException {
    //     return search("color", color);
    // }

    public List<Product> searchByName(String prroductName) throws IOException {
        return search("name", prroductName);
    }

    private List<Product> search(String term, String match) throws IOException {
        Request request = new Request(
                "GET",
                "/products/_search");
        //construct a JSON query like {"query": {"match": {"<term>": "<match"}}
        JsonObject termJson = new JsonObject().put(term, match);
        JsonObject matchJson = new JsonObject().put("match", termJson);
        JsonObject queryJson = new JsonObject().put("query", matchJson);
        request.setJsonEntity(queryJson.encode());
        Response response = restClient.performRequest(request);
        String responseBody = EntityUtils.toString(response.getEntity());

        JsonObject json = new JsonObject(responseBody);
        JsonArray hits = json.getJsonObject("hits").getJsonArray("hits");
        List<Product> results = new ArrayList<>(hits.size());
        for (int i = 0; i < hits.size(); i++) {
            JsonObject hit = hits.getJsonObject(i);
            Product product = hit.getJsonObject("_source").mapTo(Product.class);
            results.add(product);
        }
        return results;
    }
}