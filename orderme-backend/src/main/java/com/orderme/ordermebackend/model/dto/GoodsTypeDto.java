package com.orderme.ordermebackend.model.dto;

import lombok.Builder;

@Builder
public class GoodsTypeDto implements AbstractDto {

    private String title;

    private String description;

    public GoodsTypeDto() {
    }

    public GoodsTypeDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
