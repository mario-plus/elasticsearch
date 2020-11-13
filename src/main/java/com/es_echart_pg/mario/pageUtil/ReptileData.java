package com.es_echart_pg.mario.pageUtil;
import com.es_echart_pg.mario.domain.BookContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReptileData {

    public  List<BookContent> getData(String keyword) throws IOException {
        List<BookContent>  bookContents = new ArrayList<>();
        String url = "https://search.jd.com/Search?keyword="+keyword;
        Document document = Jsoup.parse(new URL(url), 3000);
        Element elementById = document.getElementById("J_goodsList");//div
        Elements elementsByTag = elementById.getElementsByTag("li");
        elementsByTag.forEach(element -> {
            String pSrc = element.getElementsByTag("img").attr("src");
            String price  = element.getElementsByTag("i").text();//
            String pName = element.getElementsByClass("p-name p-name-type-2").text();
            BookContent bookContent = new BookContent();
            bookContent.setBookName(pName);
            bookContent.setBookPrice(price);
            bookContent.setPictureSrc(pSrc);
            bookContents.add(bookContent);
        });
        return bookContents;
    }
}
