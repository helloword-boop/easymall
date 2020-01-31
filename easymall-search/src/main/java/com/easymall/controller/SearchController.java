package com.easymall.controller;

import com.easymall.common.pojo.Product;
import com.easymall.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search/manage")
public class SearchController {
    @Autowired
    private SearchService searchService;
    @RequestMapping("create")
    public String createIndex(String indexName){
        try {
            searchService.createIndex(indexName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    @RequestMapping("query")
    public List<Product> search(String query, int page, int rows) {
        return searchService.search(query, page, rows);
    }
}
