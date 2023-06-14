package com.cdtn.kltn.dto.news.respone;

public interface CustomerNewsResponse {
    Long getId();
    String getNameNews();
    String getAddress();
    String getAreaUser();
    String getBedCount();
    String getBathCount();
    String getPriceLoan();
    String getDateCreate();
    String getDateExpiration();
    String getIsPushTop();
    String getUrl();
    Long getTotalUrl();
}
