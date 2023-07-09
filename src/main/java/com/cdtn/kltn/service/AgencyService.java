package com.cdtn.kltn.service;

import com.cdtn.kltn.common.StreamUtil;
import com.cdtn.kltn.common.UtilsPage;
import com.cdtn.kltn.dto.agency.request.AgencyRequest;
import com.cdtn.kltn.entity.Agency;
import com.cdtn.kltn.entity.Districs;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.Agency.AgencyRepository;
import com.cdtn.kltn.repository.districs.DistricsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AgencyService {

    private final AgencyRepository agencyRepository;

    private final DistricsRepository districsRepository;

    public void saveAgency(Agency agencyRequest){
        if (!Objects.isNull(agencyRequest.getId())) {
            Optional<Agency> agency = agencyRepository.findById(agencyRequest.getId());
            if (agency.isEmpty()) {
                throw new StoreException("Không tìm thấy agency");
            }
        }
        agencyRequest.setDateCreate(LocalDateTime.now());
        agencyRepository.save(agencyRequest);
    }

    public Page<Agency> findAllAgency(String nameSearch, String provinceCode, int page, int size){
        Pageable pageable = UtilsPage.getPage("DESC", "id", page, size);
        List<Districs> districsList = districsRepository.findAll();
        Map<Long, Districs> mapDistrictsById = StreamUtil.toMap(districsList, Districs::getId);
        Page<Agency> agencyPage = agencyRepository.findAllAgency(nameSearch, provinceCode, pageable);
        return agencyPage.map(agency -> {
            String[] array = agency.getDistrict1st().split(",");
            StringBuilder districtsName1st =  new StringBuilder();
            for (String element : array) {
                Districs districs = mapDistrictsById.getOrDefault(Long.valueOf(element), null);
                if(Objects.nonNull(districs)){
                    districtsName1st.append(districs.getDistrictName());
                    districtsName1st.append(",");
                }
            }
            agency.setDistrictName1st(districtsName1st.toString());
            return agency;
        });
    }

    @Transactional
    public void deleteAgency(Long id){
        Agency agency = agencyRepository.findById(id).orElseThrow(() -> new StoreException("Không tìm thấy agency"));
        agencyRepository.delete(agency);
    }
}
