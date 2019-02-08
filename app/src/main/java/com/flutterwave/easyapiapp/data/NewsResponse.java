package com.flutterwave.easyapiapp.data;



import java.util.List;

public class NewsResponse {

    public NewsResponse(String status, Integer totalResults){
        this.status = status;
        this.totalResults = totalResults;

    }

    private String status;
    private Integer totalResults;
    private List<Article> articles = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
