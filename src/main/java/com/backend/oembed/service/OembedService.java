package com.backend.oembed.service;

import com.backend.oembed.util.UnicodeDecoder;
import com.backend.oembed.util.UrlConverter;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class OembedService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UrlConverter urlConverter = new UrlConverter();
    private final UnicodeDecoder unicodeDecoder = new UnicodeDecoder();


    public String getAPIData(String url) throws IOException {
        logger.info("STARTED...");
        String convURL = urlConverter.getConvertedUrl(url);
        logger.info("Converted URL : {}", convURL);
        StringBuffer sb = new StringBuffer();
        URL uri = new URL(convURL);
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setConnectTimeout(5000);
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-type", "application/json");
        con.setDoOutput(true);
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            while(br.ready()) {
                sb.append(br.readLine());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        String jsonStr = unicodeDecoder.UniToKor(sb.toString());
        //JSONObject json = new JSONObject(jsonStr);
        logger.info("return value : {}", jsonStr);
        return jsonStr;
    }


}
