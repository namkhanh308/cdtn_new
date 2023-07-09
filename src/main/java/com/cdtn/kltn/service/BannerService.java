package com.cdtn.kltn.service;

import com.cdtn.kltn.common.StreamUtil;
import com.cdtn.kltn.entity.Banner;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.Banner.BannerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    @Transactional
    public void addBanner(List<Banner> bannerList){
        if(bannerList.isEmpty()){
            throw new StoreException("Danh sách banner trống");
        }else {
            bannerList.forEach(banner -> {banner.setDateUpdated(LocalDateTime.now());});
            bannerRepository.saveAll(bannerList);
        }
    }

    @Transactional
    public void updateBanner(List<Banner> bannerList){
        if(bannerList.isEmpty()){
            throw new StoreException("Danh sách banner trống");
        }
        bannerList.forEach(banner -> {
            if (Objects.isNull(banner)) {
                throw new StoreException("Danh sách banner trống id");
            }
            bannerRepository.findById(banner.getId()).orElseThrow(() -> new StoreException("Không tìm thấy banner có id = " + banner.getId()));
        });

        Set<Long> setId = StreamUtil.mapApplyToSet(bannerList, Banner::getId);
        bannerRepository.deleteAllById(setId);

        bannerRepository.saveAll(bannerList);
    }

    @Transactional
    public void deleteBanner(Long id){
        bannerRepository.findById(id).orElseThrow(() -> new StoreException("Không tìm thấy banner có id = " + id));
        bannerRepository.deleteById(id);

    }
}
