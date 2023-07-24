package com.cdtn.kltn.dto.property.mapper;

import com.cdtn.kltn.dto.property.request.PropertyImageDTO;
import com.cdtn.kltn.entity.Image;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ImageMapperProperty {

    public List<Image> createListImage(List<PropertyImageDTO> imageList, String codeProperty, Long index) {
        List<Image> list = new ArrayList<>();
        for (int i = 0; i < imageList.size(); i++) {
            list.add(Image.builder().codeImage("IMAGE_" + (index + i + 1))
                    .propertyCode(codeProperty)
                    .url(imageList.get(i).getUrl())
                    .level(2)
                    .build());
        }
        return list;
    }

    public List<Image> updateListDelete(List<PropertyImageDTO> newList, List<Image> currentList) {
        List<Image> differentLists = currentList.stream()
                .filter(item1 -> newList.stream()
                        .noneMatch(item2 -> Objects.equals(item2.getCodeImage(), item1.getCodeImage())))
                .toList();
        for (Image image : differentLists) {
            currentList.removeIf(im -> im.getCodeImage().equals(image.getCodeImage()));
        }
        return differentLists;
    }

    public List<Image> updateList(List<PropertyImageDTO> newListDTO, List<Image> currentList, Long codeImage, String codeProperty) {
        List<Image> newList = new ArrayList<>();
        for (PropertyImageDTO propertyImageDTO : newListDTO) {
            Image image = checkContains(propertyImageDTO.getCodeImage(), currentList);
            if (Objects.isNull(image)) {
                newList.add(Image.builder()
                        .codeImage("IMAGE_" + (codeImage++))
                        .propertyCode(codeProperty)
                        .url(propertyImageDTO.getUrl())
                        .level(2)
                        .build());
            } else {
                image.setUrl(propertyImageDTO.getUrl());
                newList.add(image);
            }
        }
        return newList;
    }

    public Image checkContains(String codeProperty, List<Image> currentList) {
        for (Image image : currentList) {
            if (image.getCodeImage().equals(codeProperty)) {
                return image;
            }
        }
        return null;
    }


}
