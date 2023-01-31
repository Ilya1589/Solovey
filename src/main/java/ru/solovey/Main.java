package ru.solovey;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException{
        String url_start = "https://smotrim.ru/search?q=%D1%81%D0%BE%D0%BB%D0%BE%D0%B2%D1%8C%D0%B5%D0%B2";

        Set<String> addedUrl = new HashSet<>();

        /*crawl (1, url_start, addedUrl);
        System.out.println(addedUrl.size());//test*/

        Connection con = Jsoup.connect(url_start);
        Document doc = con.get();
        Elements child_links = doc.select("[href]");
        //        фильтрация полученных дочерних сылок
        Set<String> filteredChildLinks = new HashSet<>();
        for (Element el : child_links){
            try {
                Document childDoc = Jsoup.connect(el.absUrl("href")).get();
                Elements childDescriptions = childDoc.select("meta[content*=соловье]").select("meta[name = description]");
                if (childDescriptions.select("meta[content*=сергей соловье]").size() > 0) childDescriptions.clear();
                if (childDescriptions.select("meta[content*=соловьев live]").size() > 0) childDescriptions.clear();
                Elements childTitle = childDoc.select("title:contains(соловьёв live)");
                if (childDescriptions.size() > 0 && childTitle.size() == 0 && !addedUrl.contains(el.absUrl("href"))) filteredChildLinks.add(el.absUrl("href"));

            }
            catch (IOException e){
                continue;
            }


    }
//    public static void crawl(int level, String url, Set<String> addedUrl) throws IOException{
//        System.out.println("level: " + level);//test
//        while (level <= 5){
//            Set<String> child_links = request(url, addedUrl, level);
//            level++;
//            for (String childUrl : child_links){
//                crawl(level, childUrl, addedUrl);
//            }
//        }
//    }

    /*public static Set<String> request (String url, Set<String> addedUrl, int level) throws IOException {
//        получение дочерних ссылок
        Connection con = Jsoup.connect(url);
        Document doc = con.get();
        Elements child_links = new Elements();
        if (level == 1 ) {
            child_links = doc.select("[href]");
        }
        else {
            child_links = doc.select("a[href*=video], a[href*=brand]");
        }
//        фильтрация полученных дочерних сылок
        Set<String> filteredChildLinks = new HashSet<>();
        for (Element el : child_links){
            try {
                Document childDoc = Jsoup.connect(el.absUrl("href")).get();
                Elements childDescriptions = childDoc.select("meta[content*=соловье]").select("meta[name = description]");
                if (childDescriptions.select("meta[content*=сергей соловье]").size() > 0) childDescriptions.clear();
                if (childDescriptions.select("meta[content*=соловьев live]").size() > 0) childDescriptions.clear();
                Elements childTitle = childDoc.select("title:contains(соловьёв live)");
                if (childDescriptions.size() > 0 && childTitle.size() == 0 && !addedUrl.contains(el.absUrl("href"))) filteredChildLinks.add(el.absUrl("href"));

            }
            catch (IOException e){
                continue;
            }
        }
        for (String element : filteredChildLinks){
            System.out.println(element); //test
            addedUrl.add(element);
        }
        return filteredChildLinks;

    }*/
}

