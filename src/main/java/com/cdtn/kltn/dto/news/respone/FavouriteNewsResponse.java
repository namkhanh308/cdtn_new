package com.cdtn.kltn.dto.news.respone;

public interface FavouriteNewsResponse {
    Long getId();
    String getNameNews();
    String getAddress();
    Long getAreaUse();
    Long getBathCount();
    Long getLivingCount();
    Long getKitchenCount();
    Long getBedCount();
    String getUrl();
    String getPriceLoan();
}
