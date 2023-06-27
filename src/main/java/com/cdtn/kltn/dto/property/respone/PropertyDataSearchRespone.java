package com.cdtn.kltn.dto.property.respone;

import jakarta.persistence.Transient;

public interface PropertyDataSearchRespone {
     String getCodeProperty();
     String getNameProperty();
     String getAddressView();
     String getUrl();
     String getLastDateUpdate();
     String getNameTypeProperty();
     Integer getTotalRecord();
     String getTotal();
     String getStatus();
}
