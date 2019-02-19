package com.kinfoitsolutions.e_booksnew.response.AllSearchDataSuccess;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AllSearchDataPayload {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("book_type")
    @Expose
    private Integer bookType;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("author_id")
    @Expose
    private Integer authorId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("publication")
    @Expose
    private Object publication;
    @SerializedName("publication_year")
    @Expose
    private Object publicationYear;
    @SerializedName("subscription_type")
    @Expose
    private Integer subscriptionType;
    @SerializedName("subscription_price")
    @Expose
    private String subscriptionPrice;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("updated_by")
    @Expose
    private Integer updatedBy;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("book_image")
    @Expose
    private String bookImage;
    @SerializedName("book_file")
    @Expose
    private String bookFile;
    @SerializedName("book_audio")
    @Expose
    private String bookAudio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookType() {
        return bookType;
    }

    public void setBookType(Integer bookType) {
        this.bookType = bookType;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getPublication() {
        return publication;
    }

    public void setPublication(Object publication) {
        this.publication = publication;
    }

    public Object getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Object publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Integer getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(Integer subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(String subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookFile() {
        return bookFile;
    }

    public void setBookFile(String bookFile) {
        this.bookFile = bookFile;
    }

    public String getBookAudio() {
        return bookAudio;
    }

    public void setBookAudio(String bookAudio) {
        this.bookAudio = bookAudio;
    }


}
