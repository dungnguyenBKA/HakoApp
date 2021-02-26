package com.Dungnm;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        //updateNovelLink();
        //crawlAllNovels();
        Crawler.crawlNovelDetail("https://ln.hako.re/truyen/6624-chuyen-ve-de-quoc-tearmoon");
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
}