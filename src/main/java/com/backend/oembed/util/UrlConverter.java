package com.backend.oembed.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlConverter {

    private ProviderChecker providerChecker = new ProviderChecker();

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
