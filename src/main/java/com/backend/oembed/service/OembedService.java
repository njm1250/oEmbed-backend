package com.backend.oembed.service;

import com.backend.oembed.util.JsonConverter;
import com.backend.oembed.util.UnicodeDecoder;
import com.backend.oembed.util.UrlConverter;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Component
public class OembedService {
    private static final Logger logger = LoggerFactory.getLogger(OembedService.class);

    private final UrlConverter urlConverter;
    private final UnicodeDecoder unicodeDecoder;
    private final JsonConverter jsonConverter;

    private static final int MAX_RETRY = 5; // 최대 재시도 횟수
    private static final int RETRY_INTERVAL_MS = 1000; // 재시도 간격

    @Autowired
    public OembedService(UrlConverter urlConverter, UnicodeDecoder unicodeDecoder, JsonConverter jsonConverter) {
        this.urlConverter = urlConverter;
        this.unicodeDecoder = unicodeDecoder;
        this.jsonConverter = jsonConverter;
    }

    public String getAPIData(String url) throws Exception {
        logger.info("STARTED...");

        String convURL = urlConverter.getConvertedUrl(url);
        if (convURL == null) {
            logger.info("Unsupported or Invalid URL");
            return "FAILED";
        }
        logger.info("Converted URL: {}", convURL);

        // vimeo만 알수없는 이유로 가끔 실패해서 재시도 로직 구현
        int retryCount = 0;
        while (retryCount < MAX_RETRY) {
            try {
                StringBuffer sb = new StringBuffer();
                URL uri = new URL(convURL);
                HttpURLConnection con = (HttpURLConnection) uri.openConnection();
                con.setConnectTimeout(5000);
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-type", "application/json");
                con.setDoOutput(true);

                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                }
                con.disconnect();

                String jsonStr = unicodeDecoder.UniToKor(sb.toString()); // 유니코드 한글깨짐 현상
                // YOUTUBE 만 HTML 부분이 특수문자 escape 처리가 안되어 있음.
                if (url.contains("youtu")) {
                    String youtubeStr = jsonConverter.youtubeHtmlEscaper(jsonStr);
                    logger.info("SUCCESS");
                    logger.info("return: {}", youtubeStr);
                    return youtubeStr;
                }

                logger.info("return: {}", jsonStr);
                return jsonStr;
            } catch (IOException | JSONException e) {
                retryCount++;
                if (retryCount == MAX_RETRY) {
                    logger.error("Failed to fetch API data. {}", e.getMessage());
                    break;
                }
                logger.info("Retry after {}ms...", RETRY_INTERVAL_MS);
                Thread.sleep(RETRY_INTERVAL_MS);
            }
        }
        return "FAILED";
    }
}

