package com.backend.oembed.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.io.FileReader;

@Component
public class ProviderChecker {

    public String checkProvider(String host) {
        if(host.contains("youtu")) return "youtube";
        else if(host.contains("vimeo")) return "vimeo";
        else if(host.contains("twitter")) return "twitter";
        else if(host.contains("insta")) return "instagram";
        return null;
    }

}
