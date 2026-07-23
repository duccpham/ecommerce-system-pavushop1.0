package com.shop.pavushop.enums;

public enum OrderStatus {

    PENDING("Đang giao dịch"),

    PAID("Đã thanh toán");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}