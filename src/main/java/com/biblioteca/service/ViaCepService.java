package com.biblioteca.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ViaCepService {
    public static class Result {
        public final String street;
        public final String neighborhood;
        public final String city;
        public final String state;

        public Result(String street, String neighborhood, String city, String state) {
            this.street = street;
            this.neighborhood = neighborhood;
            this.city = city;
            this.state = state;
        }
    }

    public static Result lookup(String cep) throws IOException, InterruptedException {
        String clean = cep.replaceAll("\\D", "");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://viacep.com.br/ws/" + clean + "/json/"))
                .GET()
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) throw new IOException("ViaCEP returned status " + resp.statusCode());
        String body = resp.body();
        if (body.contains("\"erro\": true")) throw new IOException("CEP não encontrado");

        String logradouro = extract(body, "logradouro");
        String bairro = extract(body, "bairro");
        String localidade = extract(body, "localidade");
        String uf = extract(body, "uf");

        return new Result(logradouro, bairro, localidade, uf);
    }

    private static String extract(String json, String key) {
        String pattern = "\"" + key + "\":\s*\"";
        int idx = json.indexOf(pattern);
        if (idx == -1) return null;
        int start = idx + pattern.length();
        int end = json.indexOf('\"', start);
        if (end == -1) return null;
        return json.substring(start, end);
    }
}
