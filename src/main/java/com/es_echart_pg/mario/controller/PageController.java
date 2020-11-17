package com.es_echart_pg.mario.controller;

import com.es_echart_pg.mario.pageUtil.ReptileData;
import com.es_echart_pg.mario.service.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {


    final ContentService contentService;
    final ReptileData reptileData;
    public PageController(ContentService contentService, ReptileData reptileData) {
        this.contentService = contentService;
        this.reptileData = reptileData;
    }

    @GetMapping({"/index","/"})
    public String indexPage(){
        return "index.html";
    }


    /*
    * 将爬到的数据放入es中，入库
    * */
    @ResponseBody
    @GetMapping("/putDataToEs")
    public Boolean putDataToEs(@RequestParam(name = "keyword") String keyword) throws Exception {
        Boolean flag = contentService.parseContent(keyword);
        return flag;
    }

    /*
    * 从es中搜索数据
    * */
    @ResponseBody
    @GetMapping("/searchDataFormEs")
    public List<Map<String,Object>> searchDataFormEs(@RequestParam(name = "keyword") String keyword) {
        System.out.println("----------------------------");
        List<Map<String, Object>> stringObjectMap = null;
        try {
            stringObjectMap = contentService.getStringObjectMap(keyword);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringObjectMap;
    }
}



