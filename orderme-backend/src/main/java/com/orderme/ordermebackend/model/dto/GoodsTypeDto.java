package com.orderme.ordermebackend.model.dto;

import lombok.Builder;

@Builder
public class GoodsTypeDto implements AbstractDto {

    private String title;

    private String description;

    private String imageLink;

    public GoodsTypeDto() {
    }

    public GoodsTypeDto(String title, String description, String imageLink) {
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
