package com.cdtn.kltn.common;

public class Enums {

    public static enum Status {
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
    public static enum LoanType {
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


    public static enum TypePropertyCategory {
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

    public static enum TypeLoanOrBuy {
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


    public static enum  StatusProperty{
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

    public static enum  LawCategory{
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


}
