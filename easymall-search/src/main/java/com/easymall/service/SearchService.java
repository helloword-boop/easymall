package com.easymall.service;

import com.easymall.common.pojo.Product;
import com.easymall.common.utils.MapperUtil;
import com.easymall.mapper.SearchMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    private SearchMapper searchMapper;
    @Autowired
    private TransportClient transportClient;
    public void createIndex(String indexName) throws JsonProcessingException {
        /*IndicesExistsResponse response = transportClient.admin().indices().prepareExists(indexName).get();
        if (response.isExists()) {
            return;
        }
        transportClient.admin().indices().prepareCreate(indexName).get();*/
        List<Product> products = searchMapper.queryAllProduct();
        for (Product product : products) {
            String productJson = MapperUtil.MP.writeValueAsString(product);
            transportClient.prepareIndex(indexName,"product",product.getProductId()).setSource(productJson).get();
        }
    }

    public List<Product> search(String text, int page, int rows) {
        MatchQueryBuilder query = QueryBuilders.matchQuery("productName", text);
//        TermQueryBuilder query = QueryBuilders.termQuery("productName", text);
       int start=(page-1)*rows;
        SearchRequestBuilder request = transportClient.prepareSearch("easymall");
        request.setQuery(query).setFrom(start).setSize(rows);
        SearchResponse response=request.get();
        SearchHit[] hits = response.getHits().getHits();
        List<Product> productList = new ArrayList<>();
        for (SearchHit hit : hits) {
            String productJson=hit.getSourceAsString();
            try {
                Product product = MapperUtil.MP.readValue(productJson, Product.class);
                productList.add(product);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
        return productList;
    }
}
