package com.kinfoitsolutions.e_booksnew.models;

import java.io.Serializable;

public class CheckBoxModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int id;


    private boolean isSelected;


    public CheckBoxModel() {
    }

    public CheckBoxModel(String name,int id, boolean isSelected) {
        this.name = name;
        this.id = id;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
