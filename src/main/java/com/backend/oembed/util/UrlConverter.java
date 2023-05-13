package com.backend.oembed.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class UrlConverter {

    @Autowired
    private ProviderChecker providerChecker;


    public String getConvertedUrl(String url) throws MalformedURLException {
        URL host = new URL(url);
        String getHost = host.getHost();
        String provider = providerChecker.checkProvider(getHost);
        String convUrl = "";
        convUrl += provider+"?url=";
        String tempURL = url
                .replace(":", "%3A")
                .replace("?", "%3F")
                .replace("=", "%3D")
                .replace("www.", "");
        if(provider.contains("vimeo")) convUrl += tempURL;
        else convUrl += tempURL + "&format=json";
        return convUrl;
    }

}
