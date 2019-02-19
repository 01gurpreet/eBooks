
package com.kinfoitsolutions.e_booksnew.response.GetAllBooksReponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllBooksSuccess {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("recommended")
    @Expose
    private List<RecommendedPayload> recommended = null;
    @SerializedName("top_50")
    @Expose
    private List<Top50Payload> top50 = null;

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

    public List<RecommendedPayload> getRecommended() {
        return recommended;
    }

    public void setRecommended(List<RecommendedPayload> recommended) {
        this.recommended = recommended;
    }

    public List<Top50Payload> getTop50() {
        return top50;
    }

    public void setTop50(List<Top50Payload> top50) {
        this.top50 = top50;
    }


}