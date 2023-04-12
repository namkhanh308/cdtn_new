package com.cdtn.kltn.service;

import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.dto.property.mapper.ImageMapperProperty;
import com.cdtn.kltn.dto.property.mapper.PropertyMapper;
import com.cdtn.kltn.dto.property.request.CreatePropertyDTO;
import com.cdtn.kltn.dto.propertyinfo.mapper.PropertyInfoMapper;
import com.cdtn.kltn.entity.Image;
import com.cdtn.kltn.entity.Property;
import com.cdtn.kltn.entity.PropertyInfo;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.image.ImageRepository;
import com.cdtn.kltn.repository.property.PropertyRepository;
import com.cdtn.kltn.repository.propertyinfo.PropertyInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    @Transactional
    public BaseResponseData createProperty(CreatePropertyDTO createPropertyDTO){
        try {
            Property property = null;
            PropertyInfo propertyInfo = null;
            List<Image> list = null;

            if(Objects.isNull(createPropertyDTO.getCodeProperty())){
                String codeProperty = String.valueOf(getIndex() + 1);
                Long index_img = imageService.getIndex();
                property = propertyMapper.createProperty(createPropertyDTO,codeProperty);
                propertyInfo = propertyInfoMapper.createPropertyInfo(createPropertyDTO,codeProperty);
                list = imageMapperProperty.createListImage(createPropertyDTO.getImageList(),codeProperty ,index_img);
                // Lưu
                propertyRepository.save(property);
                propertyInfoRepository.save(propertyInfo);
                imageRepository.saveAll(list);
                return new BaseResponseData(200, "Success", null);
            }else {
                Long index_img = imageService.getIndex();
                //Thay đổi image
                List<Image> currentList = imageService.findAllByPropertyCode(createPropertyDTO.getCodeProperty());
                List<Image> imageListDelete = imageMapperProperty.updateListDelete(
                                                        createPropertyDTO.getImageList(),
                                                        currentList);
                List<Image> newList = imageMapperProperty.updateList(createPropertyDTO.getImageList(),
                                                                    currentList,index_img);

                //Thay đổi property
                property = propertyRepository.findByCodeProperty(createPropertyDTO.getCodeProperty())
                        .orElseThrow(() -> new StoreException("Property not found with id " +
                                                                createPropertyDTO.getCodeProperty()));
                propertyMapper.updateProperty(createPropertyDTO,property);

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

    public Long getIndex(){
        return Objects.isNull(propertyRepository.getIndex()) ? 0 : propertyRepository.getIndex();
    }
}
