# ElasticSearch

官方文档：https://www.elastic.co/guide/en/elasticsearch/reference/7.9/index.html

安装ElasticSearch

![image-20200902160008585](C:\Users\560273\AppData\Roaming\Typora\typora-user-images\image-20200902160008585.png)

安装kibana

​	![image-20200902160048446](C:\Users\560273\AppData\Roaming\Typora\typora-user-images\image-20200902160048446.png)

安装IK分词器

## 基本概念

**Index (索引)**：这里的 Index 是名词，一个 Index 就像是传统关系数据库的 Database，它是 Elasticsearch 用来存储数据的逻辑区域

**Document (文档)**：Elasticsearch 使用 JSON 文档来表示一个对象，就像是关系数据库中一个 Table 中的一行数据

**Type (类型)**：文档归属于一种 Type，就像是关系数据库中的一个 Table(**7.x版本就没有type的概念**)

**Field (字段)**：每个文档包含多个字段，类似关系数据库中一个 Table 的列

![image-20200902165238557](C:\Users\560273\AppData\Roaming\Typora\typora-user-images\image-20200902165238557.png)

**Node (节点)**：node 是一个运行着的 Elasticsearch 实例，一个 node 就是一个单独的 server

**Cluster (集群)**：cluster 是多个 node 的集合

**Shard (分片)**：数据分片，一个 index 可能会存在于多个 shard





java API

```java
SearchRequest searchRequest = new SearchRequest(); 
SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
searchRequest.source(searchSourceBuilder); 
```

searchRequest:

searchSourceBuilder:

QueryBuilders:

























