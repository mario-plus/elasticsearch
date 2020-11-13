package com.es_echart_pg.mario.service;


import com.alibaba.fastjson.JSON;
import com.es_echart_pg.mario.domain.BookContent;
import com.es_echart_pg.mario.pageUtil.ReptileData;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    ReptileData reptileData;

    public Boolean parseContent(String keywords) throws Exception{
        List<BookContent> bookContents = reptileData.getData(keywords);
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("3m");
        bookContents.forEach(bookContent -> {
            bulkRequest.add(new IndexRequest("").source(JSON.toJSONString(bookContent), XContentType.JSON));
        });
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }

}
