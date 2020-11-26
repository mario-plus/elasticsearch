# SearchRequest

SearchRequest用于与搜索文档、聚合、建议有关的任何操作，还提供了请求突出显示结果文档的方法。 

`SearchRequest searchRequest = new SearchRequest("accout");`

1.若是无参，表示不指定索引，查询整个库

searchRequest常用参数：



以逗号分隔的路由值列表，用于控制将对其执行搜索的碎片 
`searchRequest.routing("routing")`

设置indicatesoptions控制如何解析不可用的索引以及如何扩展通配符表达式
`searchRequest.indicesOptions(IndicesOptions.lenientExpandOpen())`

使用preference参数执行搜索，以首选本地碎片。默认情况下是在碎片之间随机化
`searchRequest.preference("_local")`

# SearchSourceBuilder

` SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();`

控制搜索行为的大多数选项都可以在SearchSourceBuilder上设置，它或多或少包含restapi的搜索请求正文中的选项

searchSourceBuilder常用方法：

查询条件(QueryBuilders q)
`query(QueryBuilder query)`

查询结果start
`from(int from)`

返回结果大小
`size(int size)`

查询结果最小得分
`minScore(float minScore)`

查询最长时间，超时
`timeout(TimeValue timeout)`

在查询结果上增加聚合
`aggregation(AggregationBuilder aggregation)`

在查询结果上增加高亮
`highlighter(HighlightBuilder highlightBuilder)`

根据字段排序，默认根据得分排序（有四种实现类，见源码）
`sort(String name, SortOrder order)`

# QueryBuilders

使用QueryBuilder对象创建搜索查询。对于Elasticsearch的查询DSL支持的每种搜索查询类型都存在一个QueryBuilder。

每一种搜索查询类型，都对应一个类，比如：TermQueryBuilder，FuzzyQueryBuilder，PrefixQueryBuilder...

而QueryBuilders对其进行封装成static静态方法，比如：

`QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
                                                 .fuzziness(Fuzziness.AUTO)
                                                 .prefixLength(3)
                                                 .maxExpansions(10);`
                                                 
以上方法就不一一分析，通举例说明其用法
1.sort()

`sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC)); 
sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC)); ` 

2.highlighter()

`HighlightBuilder highlightBuilder = new HighlightBuilder(); 
HighlightBuilder.Field highlightTitle =new HighlightBuilder.Field("title"); 
highlightTitle.highlighterType("unified");  
highlightBuilder.field(highlightTitle);
searchSourceBuilder.highlighter(highlightBuilder);`

突出显示搜索结果可以通过在SearchSourceBuilder上设置HighlightBuilder来实现。
通过添加一个或多个字段，可以为每个字段定义不同的突出显示行为

3.aggregation()

查询结果再次筛选，条件聚合

`SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_company").field("company.keyword");
aggregation.subAggregation(AggregationBuilders.avg("average_age").field("age"));
searchSourceBuilder.aggregation(aggregation);
`


RequestOptions.DEFAULT
设置客户端执行代码前，必须先拿到查询结果searchResponse

`SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);`

也可以设置异步监听

`client.searchAsync(searchRequest, RequestOptions.DEFAULT, listener);`

listener例如：

`ActionListener<SearchResponse> listener = new ActionListener<SearchResponse>() {
    @Override
    public void onResponse(SearchResponse searchResponse) {
    }
    @Override
    public void onFailure(Exception e) {
    }
};`











 