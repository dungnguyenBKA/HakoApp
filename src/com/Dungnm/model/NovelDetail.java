package com.Dungnm.model;

import java.util.List;

public class NovelDetail {
    private List<NovelVolume> novelVolumes;
    // genre
    private List<String> tags;
    private String novelName;
    private String authorName;
    private String artistName;
    private String status;
    private String short_description;
    private List<String> alternativeNames;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // completed, ongoing, stop
    private String type;

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    private String coverUrl;

    public List<NovelVolume> getNovelVolumes() {
        return novelVolumes;
    }

    public void setNovelVolumes(List<NovelVolume> novelVolumes) {
        this.novelVolumes = novelVolumes;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public List<String> getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(List<String> alternativeNames) {
        this.alternativeNames = alternativeNames;
    }
}
