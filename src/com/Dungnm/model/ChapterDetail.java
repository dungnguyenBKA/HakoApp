package com.Dungnm.model;

import java.util.Date;
import java.util.List;

public class ChapterDetail {
    private String chapterName;
    private Date updateTime;
    private List<ItemLine> itemLines;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    public String getChapterName() {
        return chapterName;
    }

    public ChapterDetail() {}

    public ChapterDetail(String chapterName, Date updateTime, List<ItemLine> itemLines) {
        this.chapterName = chapterName;
        this.updateTime = updateTime;
        this.itemLines = itemLines;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<ItemLine> getItemLines() {
        return itemLines;
    }

    public void setItemLines(List<ItemLine> itemLines) {
        this.itemLines = itemLines;
    }
}
