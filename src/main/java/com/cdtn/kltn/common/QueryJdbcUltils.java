package com.cdtn.kltn.common;

import com.cdtn.kltn.exception.StoreException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class QueryJdbcUltils {
    public static String createWhereQuery(List<String> whereList) {

        StringBuilder where = new StringBuilder();

        if (!whereList.isEmpty()) {
            where.append("where ");
            where.append(whereList.get(0));

            for (int i = 1; i < whereList.size(); i++) {
                where.append(" and ").append(whereList.get(i));
            }
        }
        return where.toString();
    }

    public static String checkOrderSearch(String order) {

        return switch (order) {
            case "nameNews" -> "n.name_news ";
            case "dateCreate" -> "n.date_create ";
            case "dateExpiration" -> "n.date_expiration ";
            case "statusNews" -> "n.status_news ";
            case "codeTypeProperty" -> "p.code_type_property ";
            case "codeCateTypePropertyCategory" -> "p.code_cate_type_property_category ";
            default -> throw new StoreException("order không tồn tại");
        };
    }

    public static String convertLocalDateTimeISO(LocalDateTime iso){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return iso.format(formatter);
    }
}
