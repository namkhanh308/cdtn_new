package com.cdtn.kltn.service;

import com.cdtn.kltn.common.UtilsPage;
import com.cdtn.kltn.entity.RechargeHistory;
import com.cdtn.kltn.repository.rechargehistory.RechargeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryRechargeService {
    private final RechargeHistoryRepository historyRepository;

    public Page<RechargeHistory> findHistoryByClient(String clientCode, Integer page, Integer size) {
        Pageable pageable = UtilsPage.getPage("DESC", "id", page, size);
        return historyRepository.findByCodeClient(clientCode, pageable);
    }

    public List<?> statisticsMoneyAdmin(String year) {
        return historyRepository.statisticsMoneyAdmin(year);
    }


}
