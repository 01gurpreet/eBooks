package com.kinfoitsolutions.e_booksnew.models;

/**
 * Created by wolfsoft5 on 15/10/18.
 */

public class CategorySelectModelClass {

    String title,sub_title;
    boolean isSelected = false;

    public CategorySelectModelClass(String title, String sub_title) {
        this.title = title;
        this.sub_title = sub_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
