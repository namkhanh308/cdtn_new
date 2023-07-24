package com.cdtn.kltn.service;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.dto.accountlevel.respone.TypeAccountsRespone;
import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.dto.base.response.OptionDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonService {

    public BaseResponseData getLoanType() {
        List<OptionDTO> list = new ArrayList<>();
        for (Enums.LoanType s : Enums.LoanType.values()) {
            list.add(new OptionDTO(s.getCode().toString(), s.getName()));
        }
        return new BaseResponseData(200, "Hiển thị tính chất thành công", list);
    }

    public BaseResponseData getTypePropertyCategory() {
        List<OptionDTO> list = new ArrayList<>();
        for (Enums.TypePropertyCategory s : Enums.TypePropertyCategory.values()) {
            list.add(new OptionDTO(s.getCode().toString(), s.getName()));
        }
        return new BaseResponseData(200, "Hiển thị danh mục loại bất động sản thành công", list);
    }

    public BaseResponseData getLawCategory() {
        List<OptionDTO> list = new ArrayList<>();
        for (Enums.LawCategory s : Enums.LawCategory.values()) {
            list.add(new OptionDTO(s.getCode().toString(), s.getName()));
        }
        return new BaseResponseData(200, "Hiển thị danh mục loại pháp lý thành công", list);
    }

    public BaseResponseData getTypeAccount() {
        List<TypeAccountsRespone> list = new ArrayList<>();
        for (Enums.TypeAccountLever c : Enums.TypeAccountLever.values()) {
            list.add(new TypeAccountsRespone(c.getCode(), c.getName(), c.getCountNewsUpload(), c.getDenominations()));
        }
        return new BaseResponseData(200, "Hiển thị danh mục loại tài khoản thành công", list);
    }


    public BaseResponseData getStatusNews() {
        List<OptionDTO> list = new ArrayList<>();
        for (Enums.StatusNews c : Enums.StatusNews.values()) {
            list.add(new OptionDTO(c.getCode().toString(), c.getName()));
        }
        return new BaseResponseData(200, "Hiển thị danh mục trạng thái tin thành công", list);
    }


}
