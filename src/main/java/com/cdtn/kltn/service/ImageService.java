package com.cdtn.kltn.service;

import com.cdtn.kltn.entity.Image;
import com.cdtn.kltn.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    List<Image> findAllByPropertyCode(String propertyCode) {
        return imageRepository.findAllByPropertyCode(propertyCode);
    }

    public Long getIndex() {
        return Objects.isNull(imageRepository.getIndex()) ? 0 : imageRepository.getIndex();
    }
}
