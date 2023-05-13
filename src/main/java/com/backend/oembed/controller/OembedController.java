package com.backend.oembed.controller;

import com.backend.oembed.service.OembedService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.io.IOException;

@Controller
public class OembedController {

    private final OembedService oembedService;


    public OembedController(OembedService oembedService) {
        this.oembedService = oembedService;
    }

    @GetMapping("/oembed")
    public String oembed() {
        return "oembed";
    }


    @GetMapping("/response")
    @ResponseBody
    public String response(@RequestParam("url") String url) throws IOException {
        String result = oembedService.getAPIData(url);
        System.out.println(result);
        return result;
    }



}
