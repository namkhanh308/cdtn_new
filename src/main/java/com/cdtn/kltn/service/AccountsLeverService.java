package com.cdtn.kltn.service;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.dto.accountlevel.request.AccountsLeverSwitchDTO;
import com.cdtn.kltn.entity.AccountsLever;
import com.cdtn.kltn.entity.Client;
import com.cdtn.kltn.entity.News;
import com.cdtn.kltn.entity.Property;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.accountlever.AccountsLeverRepository;
import com.cdtn.kltn.repository.client.ClientRepository;
import com.cdtn.kltn.repository.news.NewsRepository;
import com.cdtn.kltn.repository.property.PropertyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountsLeverService {

    private final AccountsLeverRepository accountsLeverRepository;
    private final ClientRepository clientRepository;
    private final NewsRepository newsRepository;
    private final PropertyRepository propertyRepository;

    @Transactional
    @Scheduled(fixedRate = 60000) // 60000ms = 1 phút
    public void autoRunAccountReset() {
        List<AccountsLever> accountsLeverList = accountsLeverRepository.findAll();
        for (AccountsLever accountsLever : accountsLeverList) {
            if (LocalDateTime.now().isAfter(accountsLever.getStartDate())
                    && LocalDateTime.now().isBefore(accountsLever.getEndDate())) {
                //check với số tin hiện tại
                Integer countUpload = newsRepository.findCountNewsActiveByCodeClient(accountsLever.getCodeClient());
                if(countUpload >= accountsLever.getCountNewsUpload()){
                    accountsLever.setStatus(0);
                }else {
                    accountsLever.setStatus(1);
                }
                //end
            } else {
                //Lấy ra danh sách tin của client đó
                List<News> newsList = newsRepository.findAllByClientCode(accountsLever.getCodeClient());
                //Sửa lại status của tin
                newsList.forEach(news -> news.setStatusNews(Enums.StatusNews.HETHAN.getCode()));
                //Save lại danh sách tin
                newsRepository.saveAll(newsList);
                // Lấy ra danh sách tài sản của tin
                List<Property> propertyList = newsList.stream()
                        .map(news -> propertyRepository.findByCodeProperty(news.getCodeProperty())
                                .orElseThrow(() -> new StoreException("Không tồn tại tài sản")))
                        .toList();
                //Sửa lại status của tài sản
                propertyList.forEach(property -> property.setStatusProperty(Enums.StatusProperty.DACHINHSUA.getCode()));
                //Save lại danh sách tài sản
                propertyRepository.saveAll(propertyList);
                //Set lại thuộc tính của cấp tài khoản
                accountsLever.setStatus(1);
                accountsLever.setAccountTypeLever(Enums.TypeAccountLever.TIETKIEM.getCode());
                accountsLever.setStartDate(LocalDateTime.now());
                accountsLever.setEndDate(LocalDateTime.now().plusDays(1));
                accountsLever.setCountNewsUpload(Enums.TypeAccountLever.MIENPHI.getCountNewsUpload());
            }
        }
        accountsLeverRepository.saveAll(accountsLeverList);
    }

    @Transactional
    public void switchAccountLever(AccountsLeverSwitchDTO accountsLeverSwitchDTO) {
        //lấy ra giá trị loại tài khoản
        Integer denominations = Enums.TypeAccountLever.checkDenominations(accountsLeverSwitchDTO.getAccountTypeLever());
        //Lấy ra thông tin loại tài khoản hiện tại của người dùng
        AccountsLever accountsLever = accountsLeverRepository.findByCodeClient(accountsLeverSwitchDTO.getCodeClient())
                .orElseThrow(() -> new StoreException("Cấp tài khoản không tồn tại"));
        //Kiểm tra thông tin tài khoản tiền của client
        Client client = clientRepository.findByCodeClient(accountsLeverSwitchDTO.getCodeClient())
                .orElseThrow(() -> new StoreException("Tài khoản không tồn tại: "));
        //Kiểm tra thông tin tài khoản của client
        if (Integer.parseInt(client.getMoney()) >= (denominations * accountsLeverSwitchDTO.getCountMonth())) {
            // kiểm tra gói hiện tại còn hiệu lực không
            if (LocalDateTime.now().isAfter(accountsLever.getStartDate())
                    && LocalDateTime.now().isBefore(accountsLever.getEndDate())) {
                // kiểm tra gói hiện tại đang đăng ký là gì:
                if (accountsLever.getAccountTypeLever().equals(accountsLeverSwitchDTO.getAccountTypeLever())) {
                    accountsLever.setStartDate(LocalDateTime.now());
                    accountsLever.setEndDate(accountsLever.
                            getEndDate().plusDays(30L * accountsLeverSwitchDTO.getCountMonth()));
                } else {
                    accountsLever.setStartDate(LocalDateTime.now());
                    accountsLever.setEndDate(LocalDateTime.now().
                            plusDays(30L * accountsLeverSwitchDTO.getCountMonth()));
                }
            } else {
                accountsLever.setStartDate(LocalDateTime.now());
                accountsLever.setEndDate(LocalDateTime.now().plusDays(30L * accountsLeverSwitchDTO.getCountMonth()));
            }
            //set lại thuộc tính khác của accountLever
            accountsLever.setAccountTypeLever(accountsLever.getAccountTypeLever() - denominations);
            accountsLever.setStatus(1);
            accountsLever.setAccountTypeLever(accountsLeverSwitchDTO.getAccountTypeLever());
            accountsLever.setCountNewsUpload(Enums.TypeAccountLever.
                    checkCountNewsUpload(accountsLeverSwitchDTO.getAccountTypeLever()));
            //save lại thông tin cấp tài khoản
            accountsLeverRepository.save(accountsLever);
            //save lại thông tin tiền của client
            client.setMoney(String.valueOf(Integer.parseInt(client.getMoney()) - (denominations * accountsLeverSwitchDTO.getCountMonth())));
            clientRepository.save(client);
        } else {
            throw new StoreException("Số dư trong tài khoản hiện không đủ để chuyển " +
                    "đổi sang gói tài khoản này");
        }
    }
}

