package com.muj.se.codeerrordetection.client.impl;

import com.muj.se.codeerrordetection.client.ErrorAnalyzerClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ErrorAnalyzerClientImpl implements ErrorAnalyzerClient {

    @Override
    public String processCodeForErrors(String payload) throws IOException, InterruptedException {
        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://127.0.0.1:8000/detect-errors"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        return response.body();
    }
}
