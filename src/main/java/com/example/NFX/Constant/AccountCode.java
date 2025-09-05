package com.example.NFX.Constant;

public enum AccountCode {
    ASSETS("10"),
    LIABILITIES("20"),
    EQUITY("30"),
    REVENUE("40"),
    EXPENSES("50"),

    CUSTOMER("11"),

    SUPPLIER("22"),

    CURRENT("1"),
    NON_CURRENT("0"),

    NORMAL("3");

    private final String code;

    AccountCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
