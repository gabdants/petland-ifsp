package com.petland.api.ImagesApi.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBDocument
public class Field {


    private String idField;
    private String name;
    private String idImage;
    private String cordX;
    private String cordY;
    private String obs;
    private String fontFamily;
    private String fontSize;
    private String allign;
    private String color;
    private String required;
    private String modelText;
    private String title;


    @DynamoDBAttribute(attributeName = "idField")
    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute
    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }


    @DynamoDBAttribute
    public String getCordX() {
        return cordX;
    }

    public void setCordX(String cordX) {
        this.cordX = cordX;
    }

    @DynamoDBAttribute
    public String getCordY() {
        return cordY;
    }

    public void setCordY(String cordY) {
        this.cordY = cordY;
    }

    @DynamoDBAttribute
    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @DynamoDBAttribute
    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    @DynamoDBAttribute
    public String getFontSize() {
        return fontSize;
    }


    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    @DynamoDBAttribute
    public String getAllign() {
        return allign;
    }

    public void setAllign(String allign) {
        this.allign = allign;
    }

    @DynamoDBAttribute
    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }

    @DynamoDBAttribute
    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    @DynamoDBAttribute
    public String getModelText() {
        return modelText;
    }

    public void setModelText(String modelText) {
        this.modelText = modelText;
    }

    @DynamoDBAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
