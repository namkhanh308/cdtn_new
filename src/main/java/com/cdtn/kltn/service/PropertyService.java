package com.cdtn.kltn.service;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.common.UtilsPage;
import com.cdtn.kltn.dto.pagination.PagingResponeDTO;
import com.cdtn.kltn.dto.property.mapper.ImageMapperProperty;
import com.cdtn.kltn.dto.property.mapper.PropertyMapper;
import com.cdtn.kltn.dto.property.request.CreatePropertyDTO;
import com.cdtn.kltn.dto.property.request.PropertyImageDTO;
import com.cdtn.kltn.dto.property.request.PropertySearchDTO;
import com.cdtn.kltn.dto.property.respone.PropertyDataSearchRespone;
import com.cdtn.kltn.dto.property.respone.PropertyDetailDataRespone;
import com.cdtn.kltn.dto.propertyinfo.mapper.PropertyInfoMapper;
import com.cdtn.kltn.entity.Image;
import com.cdtn.kltn.entity.News;
import com.cdtn.kltn.entity.Property;
import com.cdtn.kltn.entity.PropertyInfo;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.image.ImageRepository;
import com.cdtn.kltn.repository.news.NewsRepository;
import com.cdtn.kltn.repository.property.PropertyRepository;
import com.cdtn.kltn.repository.propertyinfo.PropertyInfoRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final PropertyInfoRepository propertyInfoRepository;
    private final PropertyMapper propertyMapper;
    private final PropertyInfoMapper propertyInfoMapper;
    private final ImageMapperProperty imageMapperProperty;
    private final NewsRepository newsRepository;

    @Transactional
    public void createProperty(CreatePropertyDTO createPropertyDTO) {
        Property property;
        PropertyInfo propertyInfo;
        List<Image> list;

        //thêm mới
        if (Objects.isNull(createPropertyDTO.getCodeProperty())) {
            String codeProperty = String.valueOf(getIndex() + 1);
            Long indexImg = imageService.getIndex();
            property = propertyMapper.createProperty(createPropertyDTO, codeProperty);
            propertyInfo = propertyInfoMapper.createPropertyInfo(createPropertyDTO, codeProperty);
            list = imageMapperProperty.createListImage(createPropertyDTO.getImageList(), codeProperty, indexImg);
            // Lưu
            propertyRepository.save(property);
            propertyInfoRepository.save(propertyInfo);
            imageRepository.saveAll(list);
        } else {
            Long indexImg = imageService.getIndex();
            //Thay đổi image
            List<Image> currentList = imageService.findAllByPropertyCode(createPropertyDTO.getCodeProperty());
            List<Image> imageListDelete = imageMapperProperty.updateListDelete(
                    createPropertyDTO.getImageList(),
                    currentList);
            List<Image> newList = imageMapperProperty.updateList(createPropertyDTO.getImageList(),
                    currentList, indexImg, createPropertyDTO.getCodeProperty());

            //Thay đổi property
            property = propertyRepository.findByCodeProperty(createPropertyDTO.getCodeProperty())
                    .orElseThrow(() -> new StoreException("Property not found with id " +
                            createPropertyDTO.getCodeProperty()));
            propertyMapper.updateProperty(createPropertyDTO, property);

            //Thay đổi propertyInfor
            propertyInfo = propertyInfoRepository.
                    findByCodeProperty(createPropertyDTO.getCodeProperty()).orElseThrow(() -> new StoreException("Không tồn tài tài sản chi tiết"));
            propertyInfoMapper.updatePropertyInfo(createPropertyDTO, propertyInfo);

            //lưu image
            imageRepository.deleteAll(imageListDelete);
            imageRepository.saveAll(newList);

            //Lưu property
            propertyRepository.save(property);

            //Lưu propertyInfo
            propertyInfoRepository.save(propertyInfo);
        }
    }

    public PagingResponeDTO findAllPropertyManager(PropertySearchDTO propertySearchDTO) {
        // Lấy ra offset, page
        PagingResponeDTO pagingResponeDTO = UtilsPage.paging(propertySearchDTO);
        // Lấy ra danh sách theo điều kiện tìm kiếm
        List<PropertyDataSearchRespone> propertyDatumSearchRespones = propertyRepository.findAllPropertyManager
                (propertySearchDTO.getCodeProperty(),
                        propertySearchDTO.getNameProperty(),
                        propertySearchDTO.getCodeTypeProperty(),
                        propertySearchDTO.getCodeClient(),
                        pagingResponeDTO.getOffset(),
                        pagingResponeDTO.getRecords());
        //Lấy ra tổng số bản ghi - totalRecord
        pagingResponeDTO.setTotalRecord(propertyDatumSearchRespones.stream()
                .filter(property -> StringUtils.isEmpty(property.getCodeProperty()))
                .findFirst()
                .orElse(null)
                .getTotalRecord());
        //xóa bỏ hàng chứa totalRecord
        propertyDatumSearchRespones.removeIf(property -> StringUtils.isEmpty(property.getCodeProperty()));
        System.out.println(propertyDatumSearchRespones);
        pagingResponeDTO.setData(propertyDatumSearchRespones);
        if (propertyDatumSearchRespones.isEmpty()) {
            throw new StoreException("Danh sách tài sản hiển thị tất bại");
        }
        return pagingResponeDTO;
    }

    public Long getIndex() {
        return Objects.isNull(propertyRepository.getIndex()) ? 0 : propertyRepository.getIndex();
    }

    public CreatePropertyDTO findPropertyDetail(String codeProperty) {
        //Lấy ra thông tin tài sản
        PropertyDetailDataRespone propertyDetailDataResponeList =
                propertyRepository.findPropertyByCodeProperty(codeProperty)
                        .orElseThrow(() -> new StoreException("Chi tiết tài sản hiển thị tất bại"));
        // Set lại các thuộc tính response
        CreatePropertyDTO createPropertyDTO =
                propertyMapper.setDataPropertyDetailDTO(propertyDetailDataResponeList);
        // Lấy ra danh sách ảnh theo codeProperty
        List<Image> imageList = imageService.findAllByPropertyCode(codeProperty);
        // Biến đổi lại list ảnh theo đối tượng respone
        List<PropertyImageDTO> imageDTOS = imageList.stream().map(
                image -> PropertyImageDTO.builder()
                        .codeImage(image.getCodeImage())
                        .url(image.getUrl())
                        .build()).toList();
        createPropertyDTO.setImageList(imageDTOS);
        return createPropertyDTO;
    }

    @Transactional
    public void deleteProperty(String codeProperty) {
        Property property = propertyRepository.findByCodeProperty(codeProperty)
                .orElseThrow(() -> new StoreException("Tài sản không tồn tại"));
        if (Objects.equals(property.getStatusProperty(), Enums.StatusProperty.DANGCHOTHUE.getCode())) {
            throw new StoreException("Tài sản đang được cho thuê. Không thể xóa");
        } else {
            List<News> listNews = newsRepository.findAllByCodeProperty(codeProperty);
            listNews.forEach(news -> {
                news.setStatusNews(Enums.StatusNews.DAXOA.getCode());
            });
            property.setStatusProperty(Enums.StatusNews.DAXOA.getCode());
            propertyRepository.save(property);
            newsRepository.saveAll(listNews);
        }
    }
}

