package com.cdtn.kltn.service;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.common.UtilsPage;
import com.cdtn.kltn.dto.news.mapper.NewsMapper;
import com.cdtn.kltn.dto.news.request.CreateNewsDTO;
import com.cdtn.kltn.dto.news.request.CustomerNewsSearchDTO;
import com.cdtn.kltn.dto.news.request.ManagerNewsSearchDTO;
import com.cdtn.kltn.dto.news.request.PushTopDTO;
import com.cdtn.kltn.dto.news.respone.CustomerNewsDetailResponse;
import com.cdtn.kltn.dto.news.respone.CustomerNewsForCodeCate;
import com.cdtn.kltn.dto.news.respone.CustomerNewsResponse;
import com.cdtn.kltn.dto.news.respone.ManagerNewsSearchRespone;
import com.cdtn.kltn.dto.property.request.CreatePropertyDTO;
import com.cdtn.kltn.entity.*;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.accountlever.AccountsLeverRepository;
import com.cdtn.kltn.repository.client.ClientRepository;
import com.cdtn.kltn.repository.districs.DistricsRepository;
import com.cdtn.kltn.repository.news.CustomNewsRepositoryImp;
import com.cdtn.kltn.repository.news.NewsRepository;
import com.cdtn.kltn.repository.property.PropertyRepository;
import com.cdtn.kltn.repository.province.ProvinceRepository;
import com.cdtn.kltn.repository.user.UserRepository;
import com.cdtn.kltn.repository.wards.WardsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PropertyService propertyService;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    private final NewsMapper newsMapper;

    @Transactional
    public void saveNews(CreateNewsDTO createNewsDTO) {
        Property property = propertyRepository.
                findByCodeProperty(createNewsDTO.getCodeProperty())
                .orElseThrow(() -> new StoreException("Không tìm thấy tài sản"));
        AccountsLever accountsLever = accountsLeverRepository.findByCodeClient(property.getCodeClient())
                .orElseThrow(() -> new StoreException("Không tìm cấp tài khoản"));
        //thêm mới
        if (Boolean.FALSE.equals(checkCountNewsPosted(property, accountsLever, createNewsDTO))) {
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
            if (news.getStatusNews().equals(Enums.StatusNews.DANGHOATDONG.getCode())) {
                throw new StoreException("Tin đang đăng không thể chỉnh sửa. " +
                        "Hãy hủy tin rồi thực hiện lại");
            }
            if (property.getStatusProperty().equals(Enums.StatusProperty.DACHINHSUA.getCode()) ||
                    property.getStatusProperty().equals(Enums.StatusProperty.MOITAO.getCode())) {
                news.setNameNews(createNewsDTO.getNameNews());
                news.setDateCreate(createNewsDTO.getDateCreate());
                if (news.getDateExpiration().isAfter(accountsLever.getEndDate())) {
                    news.setDateExpiration(accountsLever.getEndDate());
                }else{
                    news.setDateExpiration(createNewsDTO.getDateExpiration());
                }
                news.setStatusNews(Enums.StatusNews.DANGHOATDONG.getCode());
                newsRepository.save(news);

                property.setStatusProperty(Enums.StatusProperty.DANGCHOTHUE.getCode());
                propertyRepository.save(property);
            } else {
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


    public News findNewsDetail(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new StoreException("Không tìm thấy tin nào"));
    }

    public CustomerNewsDetailResponse findNewsDetailCustomer(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new StoreException("Không tìm thấy tin nào"));
        CreatePropertyDTO property = propertyService.findPropertyDetail(news.getCodeProperty());
        User user = userRepository.findUserByCodeClient(property.getCodeClient());
        Client client = clientRepository.findByCodeClient(property.getCodeClient())
                .orElseThrow(() -> new StoreException("Không tìm thấy client nào"));
        return CustomerNewsDetailResponse.builder().news(news).createPropertyDTO(property).user(user).client(client).build();
    }

    public Page<ManagerNewsSearchRespone> findAllNewsManager(ManagerNewsSearchDTO managerNewsSearchDTO) {
        return customNewsRepositoryImp.findAllNewsManager(managerNewsSearchDTO);
    }

    public Boolean checkCountNewsPosted(Property property, AccountsLever accountsLever, CreateNewsDTO createNewsDTO) {
        Integer countNews = newsRepository.findCountNewsActiveByCodeClient(property.getCodeClient());
        if (Objects.isNull(createNewsDTO.getId())) {
            return countNews < accountsLever.getCountNewsUpload();
        } else {
            return countNews <= accountsLever.getCountNewsUpload();
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

    public Page<?> findAllNewsCustomer(CustomerNewsSearchDTO customerNewsSearchDTO) {
        Pageable pageable = UtilsPage.getPage("DESC", "id", customerNewsSearchDTO.getPage(), customerNewsSearchDTO.getSize());
        return newsRepository.getPageCustomer(customerNewsSearchDTO.getNameSearch(),
                customerNewsSearchDTO.getProvinceCode(),
                customerNewsSearchDTO.getDistrictCode(),
                customerNewsSearchDTO.getCodeTypeProperty(),
                customerNewsSearchDTO.getCodeCateTypePropertyCategory(),
                customerNewsSearchDTO.getPriceStart(),
                customerNewsSearchDTO.getPriceEnd(),
                customerNewsSearchDTO.getAreaMinRange(),
                customerNewsSearchDTO.getAreaMaxRange(),
                customerNewsSearchDTO.getTotalRoom(),
                customerNewsSearchDTO.getRangeDaySearch(),
                pageable
        );

    }

    public List<CustomerNewsResponse> findNewSame(String codeTypeProperty, String codeCateTypePropertyCategory, String provinceCode, Long id) {
        return newsRepository.getNewsSame(codeTypeProperty,codeCateTypePropertyCategory,provinceCode, id);
    }

    public List<CustomerNewsForCodeCate> findNewByCodeCate() {
        return newsRepository.findNewsOrderCodeCategory();
    }

    public void plusViewForNews(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new StoreException("Không tìm thấy tin"));
        Long viewCurrent = (news.getView() == null) ? 0 : news.getView();
        news.setView(viewCurrent + 1);
        newsRepository.save(news);
    }

    public List<?> outstandingProject() {
        return newsRepository.outstandingProject();
    }

    public List<?> statisticsByPrice(String provinceCode, Long month, Long year, String codeCategoryTypeProperty ) {
        return newsRepository.statisticsByPrice(provinceCode,month,year,codeCategoryTypeProperty);
    }

    public List<?> statisticsByDistrict(String provinceCode, Long month, Long year, String codeCategoryTypeProperty ) {
        return newsRepository.statisticsByDistrict(provinceCode,month,year,codeCategoryTypeProperty);
    }
}
