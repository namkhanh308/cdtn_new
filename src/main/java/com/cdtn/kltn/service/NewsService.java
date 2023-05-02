package com.cdtn.kltn.service;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.dto.news.mapper.NewsMapper;
import com.cdtn.kltn.dto.news.request.CreateNewsDTO;
import com.cdtn.kltn.dto.news.request.ManagerNewsSearchDTO;
import com.cdtn.kltn.dto.news.request.PushTopDTO;
import com.cdtn.kltn.dto.news.respone.ManagerNewsSearchRespone;
import com.cdtn.kltn.entity.*;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.accountlever.AccountsLeverRepository;
import com.cdtn.kltn.repository.districs.DistricsRepository;
import com.cdtn.kltn.repository.news.CustomNewsRepositoryImp;
import com.cdtn.kltn.repository.news.NewsRepository;
import com.cdtn.kltn.repository.property.PropertyRepository;
import com.cdtn.kltn.repository.province.ProvinceRepository;
import com.cdtn.kltn.repository.wards.WardsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final PropertyRepository propertyRepository;
    private final DistricsRepository districsRepository;
    private final WardsRepository wardsRepository;
    private final ProvinceRepository provinceRepository;
    private final AccountsLeverRepository accountsLeverRepository;
    private final CustomNewsRepositoryImp customNewsRepositoryImp;

    private final NewsMapper newsMapper;

    @Transactional
    public void saveNews(CreateNewsDTO createNewsDTO) {
        Property property = propertyRepository.
                findByCodeProperty(createNewsDTO.getCodeProperty())
                .orElseThrow(() -> new StoreException("Không tìm thấy tài sản"));
        AccountsLever accountsLever = accountsLeverRepository.findByCodeClient(property.getCodeClient())
                .orElseThrow(() -> new StoreException("Không tìm cấp tài khoản"));
        //thêm mới
        Integer countNews = newsRepository.findCountNewsActiveByCodeClient(property.getCodeClient());
        if (countNews >= accountsLever.getCountNewsUpload()) {
            accountsLever.setStatus(0);
            accountsLeverRepository.save(accountsLever);
            throw new StoreException("Số lượt đăng của bạn đã đạt giới hạn." +
                    "Xin vui lòng mua thêm lượt");
        }
        if (Objects.isNull(createNewsDTO.getId())) {
            if (property.getStatusProperty().equals(Enums.StatusProperty.MOITAO.getCode()) ||
                    property.getStatusProperty().equals(Enums.StatusProperty.DACHINHSUA.getCode())
            ) {
                String address = getAddress(property);
                News news = newsMapper.createNews(createNewsDTO, address);
                if (news.getDateExpiration().isAfter(accountsLever.getEndDate())) {
                    news.setDateExpiration(accountsLever.getEndDate());
                }
                newsRepository.save(news);
                property.setStatusProperty(Enums.StatusProperty.DANGCHOTHUE.getCode());
                propertyRepository.save(property);
            } else {
                throw new StoreException("Tài sản đang ở trạng thái không hợp lệ để tạo");
            }

        } else {
            News news = newsRepository.findById(createNewsDTO.getId())
                    .orElseThrow(() -> new StoreException("Không tìn thấy tin nào"));
            if(news.getStatusNews().equals(Enums.StatusNews.DANGHOATDONG.getCode())){
                throw new StoreException("Tin đang đăng không thể chỉnh sửa. " +
                        "Hãy hủy tin rồi thực hiện lại");
            }
            if (property.getStatusProperty().equals(Enums.StatusProperty.DACHINHSUA.getCode()) ||
                            property.getStatusProperty().equals(Enums.StatusProperty.MOITAO.getCode())){
                news.setNameNews(createNewsDTO.getNameNews());
                news.setDateCreate(createNewsDTO.getDateCreate());
                if (news.getDateExpiration().isAfter(accountsLever.getEndDate())) {
                    news.setDateExpiration(accountsLever.getEndDate());
                }
                news.setStatusNews(Enums.StatusNews.DANGHOATDONG.getCode());
                newsRepository.save(news);

                property.setStatusProperty(Enums.StatusProperty.DANGCHOTHUE.getCode());
                propertyRepository.save(property);
            }else {
                throw new StoreException("Tài sản đã được tạo tin rồi");
            }
        }
    }

    @Transactional
    public void deleteNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new StoreException("Không tìm thấy tin này"));
        news.setStatusNews(Enums.StatusNews.DAXOA.getCode());
        Property property = propertyRepository.findByCodeProperty(news.getCodeProperty())
                .orElseThrow(() -> new StoreException("Không tìm thấy tài sản này"));
        property.setStatusProperty(Enums.StatusProperty.DACHINHSUA.getCode());
        newsRepository.save(news);
        propertyRepository.save(property);

        AccountsLever accountsLever = accountsLeverRepository.findByCodeClient(property.getCodeClient())
                .orElseThrow(() -> new StoreException("Không tìm cấp tài khoản"));
        Integer countNews = newsRepository.findCountNewsActiveByCodeClient(property.getCodeClient());

        if (countNews >= accountsLever.getCountNewsUpload()) {
            accountsLever.setStatus(0);
        } else {
            accountsLever.setStatus(1);
        }
        accountsLeverRepository.save(accountsLever);

    }

    @Transactional
    @Scheduled(fixedRate = 60000) // 60000ms = 1 phút
    public void autoRunChangeDateExpirationNews() {
        System.out.println("autoRunChangeDateExpirationNews" + LocalDateTime.now());
        List<News> list = newsRepository.findAllByStatusNews(1);
        for (News newItem : list) {
            // check hết đẩy top
            if (Objects.nonNull(newItem.getTimeUpTopEnd()) && LocalDateTime.now().isAfter(newItem.getTimeUpTopEnd())) {
                newItem.setTimeUpTopEnd(null);
                newItem.setTimeUpTopStart(null);
                newItem.setStatusUpTop(Enums.StatusUpTop.HETHAN.getCode());
            }
            // check tin hết thời gian
            if (LocalDateTime.now().isAfter(newItem.getDateExpiration())) {
                newItem.setStatusNews(Enums.StatusNews.HETHAN.getCode());
                try {
                    Property property = propertyRepository.findByCodeProperty(newItem.getCodeProperty())
                            .orElseThrow(() -> new StoreException("Không tìm thấy tài sản"));
                    property.setStatusProperty(Enums.StatusProperty.DACHINHSUA.getCode());
                    propertyRepository.save(property);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        newsRepository.saveAll(list);
    }

    @Transactional
    public void pushTopNews(PushTopDTO pushTopDTO) {
        News news = newsRepository.findById(pushTopDTO.getId()).
                orElseThrow(() -> new StoreException("Không tìm thấy tin"));
        if (!news.getStatusNews().equals(Enums.StatusNews.DANGHOATDONG.getCode())) {
            throw new StoreException("Trạng thái tin không phù hợp để đẩy duyệt");
        } else {
            news = newsMapper.pushTopNews(pushTopDTO, news);
            newsRepository.save(news);
        }
    }


    public String getAddress(Property property) {
        StringBuilder addressName = new StringBuilder();
        Optional<Province> province = provinceRepository.findByProvinceCode(property.getProvinceCode());
        Optional<Districs> districs = districsRepository.findByDistrictCode(property.getDistrictCode());
        Optional<Wards> wards = wardsRepository.findByWardsCode(property.getWardsCode());
        if (province.isEmpty() || districs.isEmpty() || wards.isEmpty()) {
            return null;
        } else {
            addressName.append(wards.get().getCodeName());
            addressName.append(",");
            addressName.append(districs.get().getDistrictName());
            addressName.append(",");
            addressName.append(province.get().getProvinceName());
        }
        return addressName.toString();
    }

    public CreateNewsDTO findNewsDetail(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new StoreException("Không tìm thấy tin nào"));
        return newsMapper.createNewsDetailRespones(news);
    }

    public List<ManagerNewsSearchRespone> findAllNewsManager(ManagerNewsSearchDTO managerNewsSearchDTO) {
        return customNewsRepositoryImp.findAllNewsManager(managerNewsSearchDTO).getContent();
    }

}
