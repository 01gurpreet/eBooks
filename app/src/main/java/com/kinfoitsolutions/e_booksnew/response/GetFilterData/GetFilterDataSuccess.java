package com.kinfoitsolutions.e_booksnew.response.GetFilterData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.intellij.lang.annotations.Language;

import java.util.List;

public class GetFilterDataSuccess {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("authors")
    @Expose
    private List<Authors> authors = null;
    @SerializedName("languages")
    @Expose
    private List<Languages> languages = null;
    @SerializedName("booktypes")
    @Expose
    private List<Booktypes> booktypes = null;

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

    public List<Authors> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Authors> authors) {
        this.authors = authors;
    }

    public List<Languages> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Languages> languages) {
        this.languages = languages;
    }

    public List<Booktypes> getBooktypes() {
        return booktypes;
    }

    public void setBooktypes(List<Booktypes> booktypes) {
        this.booktypes = booktypes;
    }
}
