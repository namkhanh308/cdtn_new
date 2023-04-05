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

}
