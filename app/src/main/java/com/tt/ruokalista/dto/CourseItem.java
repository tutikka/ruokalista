package com.tt.ruokalista.dto;

public class CourseItem {

    private String title_fi;

    private String title_en;

    private String category;

    private String price;

    private String properties;

    public CourseItem() {
    }

    public String getTitle_fi() {
        return title_fi;
    }

    public void setTitle_fi(String title_fi) {
        this.title_fi = title_fi;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

}
