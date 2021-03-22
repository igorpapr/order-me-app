package com.orderme.ordermebackend.model.dto;

public class ShopDto implements AbstractDto {

    private String address;

    private String title;

    public ShopDto() {
    }

    public ShopDto(String address, String title) {
        this.address = address;
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
