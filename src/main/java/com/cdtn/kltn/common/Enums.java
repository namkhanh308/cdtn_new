package com.cdtn.kltn.common;

import com.cdtn.kltn.exception.StoreException;

import java.util.Objects;
import java.util.stream.Stream;

public class Enums {

    public enum Status {
        ACTIVE(1, "ĐANG SỬ DỤNG"),
        UNACTIVE(2,"CHƯA KÍCH HOẠT"),
        BLOCK(3, "KHÓA");
        private final Integer code;
        private final String name;

        Status(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
    public  enum LoanType {
        TENANT(1, "NGƯỜI THUÊ"),
        LOAN(2,"NGƯỜI CHO THUÊ"),
        LOANANDTENANT(3,"NGƯỜI CHO THUÊ VÀ NGƯỜI THUÊ");
        private final Integer code;
        private final String name;

        LoanType(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }


    public  enum TypePropertyCategory {
        TATCA(1, "Tất cả"),
        NHA(2,"Nhà"),
        CANHO(3,"Căn hộ"),
        DAT(4,"Đất"),
        MATBANG(5,"Mặt bằng");
        private final Integer code;
        private final String name;
        TypePropertyCategory(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public  enum TypeLoanOrBuy {
        LOAN(1, "Thuê"),
        BUY(2,"Bán");
        private final Integer code;
        private final String name;
        TypeLoanOrBuy(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }


    public  enum  StatusProperty{
        MOITAO(1, "Tạo mới"),
        DACHINHSUA(2, "Đã chỉnh sửa"),
        DANGCHOTHUE(3,"Đang cho thuê"),
        DACHOTHUE(4,"Đã cho thuê"),
        HUY(5,"Đã hủy");
        private final Integer code;
        private final String name;
        StatusProperty(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public  enum  LawCategory{
        SOHONG(1, "Sổ hồng"),
        SODO(2,"Sổ đỏ"),
        KHONGSO(3,"Chưa có sổ");

        private final Integer code;
        private final String name;
        LawCategory(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public enum TypeAccountLever {
        MIENPHI(0, "Miễn phí",1,0),
        TIETKIEM(1, "Tiết kiệm",20,100000),
        TIEUCHUAN(2,"Tiêu chuẩn",50,150000),
        CAOCAP(3,"Cao cấp",100,200000);

        private final Integer code;
        private final String name;
        private final Integer countNewsUpload;
        private final Integer denominations;

        TypeAccountLever(Integer code, String name, Integer countNewsUpload, Integer denominations) {
            this.code = code;
            this.name = name;
            this.countNewsUpload = countNewsUpload;
            this.denominations = denominations;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public Integer getCountNewsUpload() { return countNewsUpload;}

        public Integer getDenominations() {return denominations;}

        public static String checkName(Integer code) {
            if (Objects.nonNull(code)) {
                return Stream.of(TypeAccountLever.values())
                        .filter(color -> color.getCode().equals(code))
                        .map(TypeAccountLever::getName)
                        .findFirst()
                        .orElseThrow(() -> new StoreException("TypeAccount not found with code " + code));
            }
            throw new StoreException("TypeAccount status is null");
        }

        public static Integer checkDenominations(Integer code) {
            if (Objects.nonNull(code)) {
                return Stream.of(TypeAccountLever.values())
                        .filter(color -> color.getCode().equals(code))
                        .map(TypeAccountLever::getDenominations)
                        .findFirst()
                        .orElseThrow(() -> new StoreException("TypeAccount not found with code: " + code));
            }
            throw new StoreException("TypeAccount.checkDenominations is null");
        }

        public static Integer checkCountNewsUpload(Integer code) {
            if (Objects.nonNull(code)) {
                return Stream.of(TypeAccountLever.values())
                        .filter(color -> color.getCode().equals(code))
                        .map(TypeAccountLever::getCountNewsUpload)
                        .findFirst()
                        .orElseThrow(() -> new StoreException("TypeAccount not found with code: " + code));
            }
            throw new StoreException("TypeAccount.checkCountNewsUpload status is null");
        }


    }




}
