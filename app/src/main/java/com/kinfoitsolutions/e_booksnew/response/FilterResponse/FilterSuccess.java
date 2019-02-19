package com.kinfoitsolutions.e_booksnew.response.FilterResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FilterSuccess implements Serializable {


    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("books")
    @Expose
    private List<FilterPayload> books = null;

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

    public List<FilterPayload> getBooks() {
        return books;
    }

    public void setBooks(List<FilterPayload> books) {
        this.books = books;
    }
}
