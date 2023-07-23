package com.cdtn.kltn.repository.rechargehistory;

import com.cdtn.kltn.dto.client.respone.StatisticsMoneyResponse;
import com.cdtn.kltn.entity.RechargeHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RechargeHistoryRepository extends JpaRepository<RechargeHistory, Long> {
    Page<RechargeHistory> findByCodeClient(String codeClient, Pageable pageable);

    @Query(value = """
                select YEAR(date_recharge) as year, MONTH(date_recharge) as month, sum(money) as money
                from recharge_history
                where YEAR(date_recharge) = ?1
                group by YEAR(date_recharge), MONTH(date_recharge)
            """, nativeQuery = true)
    List<StatisticsMoneyResponse> statisticsMoneyAdmin(String year);
}
