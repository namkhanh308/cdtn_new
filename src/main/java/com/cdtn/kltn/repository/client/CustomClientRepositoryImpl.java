package com.cdtn.kltn.repository.client;

import com.cdtn.kltn.dto.client.respone.HomeClientResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomClientRepositoryImpl implements CustomClientRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public HomeClientResponse homeClient(String codeClient) {
        String sql = """
        select c.full_name as fullName,
           u.user_name as email,
           al.account_type_lever as lever,
           c.money as money,
           coalesce(vp.totalProperty,'0') as postingProperty,
           coalesce(v1.totalNews,'0') as postingNews,
           coalesce(v2.totalNews,'0') as expiredNews,
           coalesce(v3.totalNews,'0') as completeNews,
           CONCAT(coalesce(v4.totalNews,'0'),'/',al.count_news_upload) as allNews
        from users u join client c on u.id = c.user_id
                join viewproperty vp on c.code_client = vp.code_client
                left join viewnews v1 on c.code_client = v1.code_client and v1.status_news = '1'
                left join viewnews v2 on c.code_client = v2.code_client and v2.status_news = '2'
                left join viewnews v4 on c.code_client = v4.code_client and v4.status_news != '4'
                left join viewnews v3 on c.code_client = v3.code_client and v3.status_news = '3'
                join accounts_lever al on c.code_client = al.code_client """;
        // Set where
        sql += createHomeClientQuery(codeClient);
        Query query = entityManager.createNativeQuery(sql);
        // Set parameter cho where
        setParamHomeClientQuery(codeClient,query);
        // Trả về kết quả
        Object[] resultArray = (Object[]) query.getSingleResult();
        // Trả về mapping kết quả
        return mappingResult(resultArray);
    }

    private String createHomeClientQuery(String codeClient){
        String whereSql = "";
        if(Strings.isNotBlank(codeClient)){
            whereSql += whereSql.isEmpty() ? " WHERE " : (whereSql +"AND ");
            whereSql = (whereSql +"c.code_client = :codeClient ");
        }
        return whereSql;
    }

    private void setParamHomeClientQuery(String codeClient, Query query){
        if(StringUtils.isNotBlank(codeClient)){
            query.setParameter("codeClient", codeClient);
        }
    }
    private HomeClientResponse mappingResult(Object[] resultArray){
        if (resultArray != null) {
            HomeClientResponse homeClientResponse = new HomeClientResponse();
            homeClientResponse.setFullName(String.valueOf(resultArray[0]));
            homeClientResponse.setEmail(String.valueOf(resultArray[1]));
            homeClientResponse.setLever(String.valueOf(resultArray[2]));
            homeClientResponse.setMoney(String.valueOf(resultArray[3]));
            homeClientResponse.setPostingProperty(String.valueOf(resultArray[4]));
            homeClientResponse.setPostingNews(String.valueOf(resultArray[5]));
            homeClientResponse.setExpiredNews(String.valueOf(resultArray[6]));
            homeClientResponse.setCompleteNews(String.valueOf(resultArray[7]));
            homeClientResponse.setAllNews(String.valueOf(resultArray[8]));
            return homeClientResponse;
        }else {
            return null;
        }
    }
}