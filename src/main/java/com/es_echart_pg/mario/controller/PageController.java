package com.es_echart_pg.mario.controller;

import com.es_echart_pg.mario.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {


    @Autowired
    ContentService contentService;

    @GetMapping({"/index","/"})
    public String indexPage(){
        return "index.html";
    }


    @ResponseBody
    @GetMapping("/getData")
    public Boolean getData(@RequestParam(name = "keyword") String keyword) throws Exception {
        System.out.println(contentService);
        Boolean flag = contentService.parseContent(keyword);
        return flag;
    }

}
