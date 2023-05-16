package com.backend.oembed.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class OembedProviderService {

    private static Gson gson;

    @Autowired
    public OembedProviderService(Gson gson) {
        this.gson = gson;
    }

    public String getOembedApiUrl(String providerName) throws IOException {
        // provider.json 파일 읽기
        ClassPathResource resource = new ClassPathResource("providers/provider.json");
        InputStream inputStream = resource.getInputStream();
        Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        // JSON 파일 파싱하기
        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

        // provider_name과 일치하는 url찾기
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            String currentProviderName = jsonObject.get("provider_name").getAsString();
            if (currentProviderName.equalsIgnoreCase(providerName)) {
                JsonArray endpoints = jsonObject.getAsJsonArray("endpoints");
                for (JsonElement endpoint : endpoints) {
                    String oembedUrl = endpoint.getAsJsonObject().getAsJsonPrimitive("url").getAsString(); // sonElement String으로 가져오기
                    return oembedUrl;
                }
            }
        }
        return null;
    }

}
