package com.app.ecoshare.Models;

import android.graphics.Bitmap;

import java.time.LocalDateTime;
import java.util.List;

public class Report {

    private Integer id;
    private List<String> category;
    private String description;
    private Characteristics characteristics;
    private Boolean isResolve;
    private Boolean isAnon;
    private String bitmap;

    public Report(List<String> listCategoryOfObject, String description,
                  Characteristics characteristic, Boolean isResolve, Boolean isAnon, String bitmap) {

        this.category = listCategoryOfObject;
        this.description = description;
        this.characteristics = characteristic;
        this.isResolve = isResolve;
        this.isAnon = isAnon;
        this.bitmap = bitmap;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Boolean getResolve() {
        return isResolve;
    }

    public Characteristics getCharacteristic() {
        return characteristics;
    }

    public void setCharacteristic(Characteristics characteristic) {
        this.characteristics = characteristic;
    }

    public void setResolve(Boolean resolve) {
        isResolve = resolve;
    }

    public Boolean getAnon() {
        return isAnon;
    }

    public void setAnon(Boolean anon) {
        isAnon = anon;
    }

    public Characteristics getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Characteristics characteristics) {
        this.characteristics = characteristics;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }
}
