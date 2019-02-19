package com.kinfoitsolutions.e_booksnew.response.CategoryResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategorySuccess {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("categories")
    @Expose
    private List<CategoryPayload> categories = null;
    @SerializedName("total_books")
    @Expose
    private Integer totalBooks;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CategoryPayload> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryPayload> categories) {
        this.categories = categories;
    }

    public Integer getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(Integer totalBooks) {
        this.totalBooks = totalBooks;
    }
}
