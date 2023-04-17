package com.cdtn.kltn.service;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.entity.AccountsLever;
import com.cdtn.kltn.repository.accountlever.AccountsLeverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountsLeverService {

    private final AccountsLeverRepository accountsLeverRepository;

    @Scheduled(fixedRate = 60000) // 60000ms = 1 phút
    public void myMinuteTask() {
        List<AccountsLever> accountsLeverList = accountsLeverRepository.findAll();
        for (AccountsLever accountsLever: accountsLeverList) {
            if (LocalDateTime.now().isAfter(accountsLever.getStartDate())
                    && LocalDateTime.now().isBefore(accountsLever.getEndDate())) {
                //check với số tin hiện tại


                //end
            }
            else {
                //check với số tin hiện tại


                //end
                accountsLever.setStatus(1);
                accountsLever.setAccountTypeLever(Enums.TypeAccountLever.TIETKIEM.getCode());
                accountsLever.setStartDate(LocalDateTime.now());
                accountsLever.setEndDate(LocalDateTime.now().plusDays(1));
                accountsLever.setCountNewsUpload(Enums.TypeAccountLever.MIENPHI.getCountNewsUpload());
            }
        }
        accountsLeverRepository.saveAll(accountsLeverList);
    }



    public AccountsLever save(AccountsLever accountsLever) {
        return accountsLeverRepository.save(accountsLever);
    }
}
