package com.cdtn.kltn.service;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.dto.base.OptionDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonService {

    public BaseResponseData getLoanType(){
        List<OptionDTO> list = new ArrayList<>();
        for (Enums.LoanType s: Enums.LoanType.values()) {
            list.add(new OptionDTO(s.getCode().toString(), s.getName()));
        }
        return  new BaseResponseData(200, "Hiển thị tính chất thành công", list);
    }
}
