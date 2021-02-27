package com.Dungnm;

import com.Dungnm.model.ChapterDetail;
import com.Dungnm.model.ItemLine;
import com.Dungnm.model.NovelDetail;
import com.Dungnm.model.NovelVolume;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Crawler {
    // return JSON of NovelDetail
    public static NovelDetail crawlNovelDetail(String novelDetailUrl){
        NovelDetail novelDetail = new NovelDetail();
        try {
            Document novelDetailDoc = Jsoup.connect(novelDetailUrl).get();
            // name
            Elements seriesName = novelDetailDoc.getElementsByClass("series-name");
            if(!seriesName.isEmpty()){
                novelDetail.setNovelName(seriesName.get(0).child(0).text());
            }

            // info novel
            Elements info = novelDetailDoc.getElementsByClass("info-item");
            if(info.size() == 2){
                novelDetail.setAuthorName(info.get(0).child(1).child(0).text());
                novelDetail.setStatus(info.get(1).child(1).child(0).text());
            } else if(info.size() == 3) {
                novelDetail.setAuthorName(info.get(0).child(1).child(0).text());
                novelDetail.setArtistName(info.get(1).child(1).child(0).text());
                novelDetail.setStatus(info.get(2).child(1).child(0).text());
            }

            Elements seriesCover = novelDetailDoc.getElementsByClass("series-cover");
            if(!seriesCover.isEmpty()) {
                Elements coverEle = seriesCover.get(0).getElementsByClass("content img-in-ratio");
                if(!coverEle.isEmpty()) {
                    String fullCss = coverEle.get(0).attr("style");
                    novelDetail.setCoverUrl(fullCss.substring(fullCss.indexOf("http"), fullCss.indexOf("')")));
                }
            }

            Elements seriesType = novelDetailDoc.getElementsByClass("series-type");
            if(!seriesType.isEmpty()){
                novelDetail.setType(seriesType.get(0).text());
            }

            Elements alterNameBlock = novelDetailDoc.getElementsByClass("block pad-bottom-5");
            ArrayList<String> alterNames = new ArrayList<>();
            alterNameBlock.forEach(alter -> alterNames.add(alter.text()));
            novelDetail.setAlternativeNames(alterNames);

            // genres
            Elements genresBlock = novelDetailDoc.getElementsByClass("series-gerne-item");
            ArrayList<String> tags = new ArrayList<>();
            genresBlock.forEach(genre -> tags.add(genre.text()));
            novelDetail.setTags(tags);

            // short-description
            Elements desc = novelDetailDoc.getElementsByClass("summary-content");
            if(!desc.isEmpty()) {
                novelDetail.setShort_description(desc.get(0).html());
            }

            // crawl volume
            ArrayList<NovelVolume> novelVolumes = new ArrayList<>();
            Elements elements = novelDetailDoc.select("div.col-12.col-lg-9.float-left");
            if(elements.size() > 1){
                Elements listChapterBlock = elements.get(1).getElementsByClass("volume-list at-series basic-section volume-mobile gradual-mobile ");
                listChapterBlock.forEach(chapterBlock -> {
                    NovelVolume novelVolume = new NovelVolume();
                    // volume name
                    Elements volNameBlock = chapterBlock.select("span.sect-title");
                    if(!volNameBlock.isEmpty()) {
                        if(volNameBlock.get(0).children().isEmpty()){
                            novelVolume.setName(volNameBlock.get(0).text());
                        } else {
                            novelVolume.setName(volNameBlock.get(0).child(0).text());
                        }
                    }

                    // volume cover
                    Elements volumeCover = chapterBlock.select("div.content.img-in-ratio");
                    if(!volumeCover.isEmpty()) {
                        String fullCss = volumeCover.get(0).attr("style");
                        novelVolume.setVolumeCover(fullCss.substring(fullCss.indexOf("http"), fullCss.indexOf("')")));
                    }

                    // volume content
                    ArrayList<ChapterDetail> chapterDetails = new ArrayList<>();
                    // listChaptersEle.get(0).getElementsByClass("chapter-name").get(0).select("a").attr("href")
                    Elements listChaptersEle = chapterBlock.getElementsByClass("list-chapters at-series");
                    if(!listChaptersEle.isEmpty()) {
                        listChaptersEle.get(0).getElementsByClass("chapter-name").forEach(item -> {
                            String chapterUrl = Const.BASE_URL + item.select("a").attr("href");
                            try {
                                chapterDetails.add(crawlChapterDetail(chapterUrl));
                            } catch (Exception e){
                                System.out.println("Eror in chapter "+ chapterUrl + ", retry...");
                                e.printStackTrace();
                                try {
                                    Thread.sleep(Const.TIME_SLEEP);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                chapterDetails.add(crawlChapterDetail(chapterUrl));
                            }

                            try {
                                Thread.sleep(Const.TIME_SLEEP);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    novelVolume.setListChapter(chapterDetails);

                    novelVolumes.add(novelVolume);
                });
            }
            novelDetail.setNovelVolumes(novelVolumes);
        } catch (IOException e) {
            System.out.println("Error: " + novelDetailUrl);
            e.printStackTrace();
        }
        return novelDetail;
    }

    // return a ChapterDetail
    private static ChapterDetail crawlChapterDetail(String url){
        ChapterDetail cd = new ChapterDetail();
        try {
            //ArrayList<ItemLine> lines = new ArrayList<>();
            Document doc = Jsoup.connect(url).get();
            Element body = doc.body();

            // get chapter name
            Elements chapterTitles = body.getElementsByClass("title-item");
            if(chapterTitles != null){
                cd.setChapterName(chapterTitles.get(1).text());
            }

            // get chapter content
            Element chapterContent = body.getElementById("chapter-content");
            if (chapterContent != null) {
//                Elements listItemLines = chapterContent.children();
//                listItemLines.forEach(item -> {
//                    if (item.children().isEmpty()) {
//                        // type text
//                        lines.add(new ItemLine(item.text(), ""));
//                    } else {
//                        // type image
//                        lines.add(new ItemLine("", item.children().get(0).attr("src")));
//                    }
//                });
                String content = chapterContent.html();
                cd.setContent(content);
            }

            //cd.setItemLines(lines);
            cd.setUpdateTime(new Date(System.currentTimeMillis()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done chapter " + cd.getChapterName());
        return cd;
    }



    public static String getAllNovelLink(){
        String url;
        ArrayList<String> novelUrls = new ArrayList<>();
        for (int i = 0; i < 33; i++) {
            url = Const.BASE_URL_ALL_NOVEL + i;
            try {
                Elements novelBlocks = Jsoup.connect(url).get().getElementsByClass("thumb_attr series-title");
                novelBlocks.forEach(novel -> {
                    novelUrls.add(Const.BASE_URL + novel.select("a").attr("href"));
                });
                System.out.println("Done page " + i);
            } catch (IOException e) {
                System.out.println("Error at page "+i);
                e.printStackTrace();
            }
        }
        return new Gson().toJson(novelUrls);
    }
}
