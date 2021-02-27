package com.Dungnm;

import com.Dungnm.model.NovelDetail;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        //updateNovelLink();
        //crawlAllNovels();
        //Crawler.crawlNovelDetail("https://ln.hako.re/truyen/6624-chuyen-ve-de-quoc-tearmoon");
        crawlAListNovel();
    }

    private static void updateNovelLink() {
        try {
            FileWriter myWriter = new FileWriter(Const.FILE_NOVEL_URL);
            myWriter.write(Crawler.getAllNovelLink());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void crawlAllNovels() {
        try {
            List<String> novelUrls = new Gson().fromJson(
                    new JsonReader(new FileReader(Const.FILE_NOVEL_URL)),
                    new TypeToken<List<String>>() {
                    }.getType()
            );
            List<String> subString = novelUrls.subList(0, 10);
            subString.forEach(url -> {
                try {
                    System.out.println("Start "+url);
                    Crawler.crawlNovelDetail(url);
                    System.out.println("Done "+url);
                } catch (Exception e) {
                    System.out.println("Error "+url);
                    e.printStackTrace();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void crawlAListNovel() {
        try {
            List<NovelDetail> novelDetails = new ArrayList<>();
            List<String> novelUrls = new ArrayList<>();
            novelUrls.add("https://ln.hako.re/truyen/7063-ill-have-sherbet");
            novelUrls.add("https://ln.hako.re/truyen/8118-o-miai-shitakunakattanode-muri-nandaina-joken-o-tsuketara-dokyusei-ga-kita-kudan-ni-tsuite");
            novelUrls.add("https://ln.hako.re/truyen/8349-rokudenashi-majutsu-koushi-to-akashic-records");
            novelUrls.add("https://ln.hako.re/truyen/3285-youkoso-jitsuryoku-shijou-shugi-no-kyoushitsu-e");
            novelUrls.add("https://ln.hako.re/truyen/7244-osananajimi-ga-zettai-ni-makenai-love-comedy");

            //List<String> subString = novelUrls.subList(0, 10);
            novelUrls.forEach(url -> {
                try {
                    System.out.println("Start "+url);
                    novelDetails.add(Crawler.crawlNovelDetail(url));
                    System.out.println("Done "+url);
                } catch (Exception e) {
                    System.out.println("Error "+url);
                    e.printStackTrace();
                }
            });

            FileWriter myWriter = new FileWriter(Const.FILE_NOVELS_DATA);
            myWriter.write(new Gson().toJson(novelDetails));
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}