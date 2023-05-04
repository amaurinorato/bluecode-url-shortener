package com.example.bluecode.urlshortener.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UrlModel {

    @Id
    private String shorten;
    @Column(name = "long_version")
    private String longVersion;
    private Integer access;

    public UrlModel(String shorten, String longVersion, Integer access) {
        this.shorten = shorten;
        this.longVersion = longVersion;
        this.access = access;
    }

    public String getLongVersion() {
        return longVersion;
    }

    public void setLongVersion(String longVersion) {
        this.longVersion = longVersion;
    }

    public String getShorten() {
        return shorten;
    }

    public void setShorten(String shorten) {
        this.shorten = shorten;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public void increaseAccess() {
        this.access += 1;
    }
}
