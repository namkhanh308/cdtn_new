package com.cdtn.kltn.service;

import com.cdtn.kltn.common.UltilsPage;
import com.cdtn.kltn.dto.base.response.BaseResponseData;
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
import com.cdtn.kltn.entity.Property;
import com.cdtn.kltn.entity.PropertyInfo;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.image.ImageRepository;
import com.cdtn.kltn.repository.property.PropertyRepository;
import com.cdtn.kltn.repository.propertyinfo.PropertyInfoRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Transactional
    public BaseResponseData createProperty(CreatePropertyDTO createPropertyDTO) {
        try {
            Property property = null;
            PropertyInfo propertyInfo = null;
            List<Image> list = null;

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
                return new BaseResponseData(200, "Success", null);
            } else {
                Long indexImg = imageService.getIndex();
                //Thay đổi image
                List<Image> currentList = imageService.findAllByPropertyCode(createPropertyDTO.getCodeProperty());
                List<Image> imageListDelete = imageMapperProperty.updateListDelete(
                        createPropertyDTO.getImageList(),
                        currentList);
                List<Image> newList = imageMapperProperty.updateList(createPropertyDTO.getImageList(),
                        currentList, indexImg);

                //Thay đổi property
                property = propertyRepository.findByCodeProperty(createPropertyDTO.getCodeProperty())
                        .orElseThrow(() -> new StoreException("Property not found with id " +
                                createPropertyDTO.getCodeProperty()));
                propertyMapper.updateProperty(createPropertyDTO, property);

                //Thay đổi propertyInfor
                propertyInfo = propertyInfoRepository.findByCodeProperty(createPropertyDTO.getCodeProperty()).get();
                propertyInfoMapper.updatePropertyInfo(createPropertyDTO, propertyInfo);

                //lưu image
                imageRepository.deleteAll(imageListDelete);
                imageRepository.saveAll(newList);

                //Lưu property
                propertyRepository.save(property);

                //Lưu propertyInfo
                propertyInfoRepository.save(propertyInfo);

                return new BaseResponseData(200, "Success", null);
            }
        } catch (Exception e) {
            return new BaseResponseData(500, "Error", e.getMessage());
        }
    }

    public BaseResponseData findAllPropertyManager(PropertySearchDTO propertySearchDTO) {
        // Lấy ra offset, page
        PagingResponeDTO pagingResponeDTO = UltilsPage.paging(propertySearchDTO);
        // Lấy ra danh sách theo điều kiện tìm kiếm
        List<PropertyDataSearchRespone> propertyDatumSearchRespones = propertyRepository.findAllPropertyManager
                (propertySearchDTO.getCodeProperty(),
                        propertySearchDTO.getNameProperty(),
                        propertySearchDTO.getCodeTypeProperty(),
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
        pagingResponeDTO.setData(propertyDatumSearchRespones);

        return !propertyDatumSearchRespones.isEmpty() ?
                new BaseResponseData(200, "Danh sách tài sản hiển thị thành công", pagingResponeDTO) :
                new BaseResponseData(500, "Danh sách tài sản hiển thị tất bại", null);
    }

    public Long getIndex() {
        return Objects.isNull(propertyRepository.getIndex()) ? 0 : propertyRepository.getIndex();
    }

    public BaseResponseData findPropertyDetail(String codeProperty) {
        //Lấy ra thông tin tài sản
        Optional<PropertyDetailDataRespone> propertyDetailDataResponeList =
                propertyRepository.findPropertyByCodeProperty(codeProperty);
        if (propertyDetailDataResponeList.isPresent()) {
            // Set lại các thuộc tính response
            CreatePropertyDTO createPropertyDTO =
                    propertyMapper.setDataPropertyDetailDTO(propertyDetailDataResponeList.get());
            // Lấy ra danh sách ảnh theo codeProperty
            List<Image> imageList = imageService.findAllByPropertyCode(codeProperty);
            // Biến đổi lại list ảnh theo đối tượng respone
            List<PropertyImageDTO> imageDTOS = imageList.stream().map(
                    image -> PropertyImageDTO.builder()
                            .codeImage(image.getCodeImage())
                            .url(image.getUrl())
                            .build()).toList();
            createPropertyDTO.setImageList(imageDTOS);
                return new BaseResponseData(200, "Chi tiết tài sản hiển thị thành công", createPropertyDTO);
        }
        return new BaseResponseData(200, "Chi tiết tài sản hiển thị tất bại", null);
    }
}

