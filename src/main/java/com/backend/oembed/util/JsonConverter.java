package com.backend.oembed.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter {


    // Youtube html 특수문자 escaper
    public String youtubeHtmlEscaper(String JsonStr) {
        String result = "";
        String[] escape = JsonStr.split("\"html\":\"");
        escape[1] = escape[1].replace("\"", "\\\"");
        escape[1] = escape[1].substring(0, escape[1].length() - 3);
        escape[1] += "\"}";
        result += escape[0];
        result += "\"html\":\"";
        result += escape[1];

        return result;
    }
}
