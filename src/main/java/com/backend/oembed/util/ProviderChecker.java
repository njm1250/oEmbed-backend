package com.backend.oembed.util;

import org.springframework.stereotype.Component;

@Component
public class ProviderChecker {

    public String checkProvider(String host) {
        if(host.contains("youtube") || host.contains("youtu")) return "https://www.youtube.com/oembed";
        else if(host.contains("vimeo")) return "https://vimeo.com/api/oembed.json";
        else if(host.contains("twitter")) return "https://publish.twitter.com/oembed";
        return "";
    }
}
