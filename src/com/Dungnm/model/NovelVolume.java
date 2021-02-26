package com.Dungnm.model;

import java.util.ArrayList;
import java.util.List;

public class NovelVolume {
    private String name;

    public String getVolumeCover() {
        return volumeCover;
    }

    public void setVolumeCover(String volumeCover) {
        this.volumeCover = volumeCover;
    }

    private String volumeCover;
    private List<ChapterDetail> listChapter = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChapterDetail> getListChapter() {
        return listChapter;
    }

    public void setListChapter(List<ChapterDetail> listChapter) {
        this.listChapter = listChapter;
    }
}
