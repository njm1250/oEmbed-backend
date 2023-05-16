package com.backend.oembed.util;

import com.backend.oembed.service.OembedProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class UrlConverter {

    @Autowired
    private ProviderChecker providerChecker;

    private final OembedProviderService oembedProviderService;

    @Autowired
    public UrlConverter(OembedProviderService oembedProviderService) {
        this.oembedProviderService = oembedProviderService;
    }

    public String getConvertedUrl(String url) throws IOException {
        URL host = new URL(url);
        String getHost = host.getHost();
        String provider = providerChecker.checkProvider(getHost);
        String convUrl = oembedProviderService.getOembedApiUrl(provider) + "?url=";
        String tempURL = url
                .replace(":", "%3A")
                .replace("?", "%3F")
                .replace("=", "%3D")
                .replace("www.", "");
        if (provider.contains("vimeo")) {
            String vimeoApiUrl = "https://vimeo.com/api/oembed.json?url=";
            convUrl = vimeoApiUrl + tempURL;
        }
        else convUrl += tempURL + "&format=json";
        return convUrl;
    }


}
