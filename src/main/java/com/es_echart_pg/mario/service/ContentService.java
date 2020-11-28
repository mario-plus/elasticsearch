package com.es_echart_pg.mario.service;


import com.alibaba.fastjson.JSON;
import com.es_echart_pg.mario.domain.BookContent;
import com.es_echart_pg.mario.pageUtil.ReptileData;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ContentService {

    //region 注入类
    private final RestHighLevelClient restHighLevelClient;
    private final ReptileData reptileData;
    public ContentService(RestHighLevelClient restHighLevelClient, ReptileData reptileData) {
        this.restHighLevelClient = restHighLevelClient;
        this.reptileData = reptileData;
    }
    //endregion

    //region 将数据插入es中
    public Boolean parseContent(String keywords) throws Exception{
        List<BookContent> bookContents = reptileData.getData(keywords);
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("3m");
        bookContents.forEach(bookContent -> {
            System.out.println(bookContent);
            bulkRequest.add(new IndexRequest("jd_goods").source(JSON.toJSONString(bookContent), XContentType.JSON));
        });
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }
    //endregion

    //region 查询数据
    public  List<Map<String, Object>> getStringObjectMap(String keyword) throws IOException {
        SearchRequest searchRequest = new SearchRequest("jd_goods");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("bookName", keyword);
        searchSourceBuilder.query(termQueryBuilder).timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchSourceBuilder.size(30);//默认十条
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        ArrayList list = new ArrayList();
        for (SearchHit searchHit:searchResponse.getHits().getHits()) {
            System.out.println(searchHit);
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
            list.add(sourceAsMap);
        }
        return list;
    }
    //endregion



}
