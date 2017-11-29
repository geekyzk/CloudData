package com.em248.novel.spider;

import com.em248.novel.dto.BookChapterDto;
import com.em248.novel.dto.BookInfoDto;
import com.xiaoleilu.hutool.lang.Console;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by geekyzk on 29/11/2017.
 */
abstract class AbstractBookSpider {

    private String searchTemplateUrlOne = "http://zhannei.baidu.com/cse/search?s=%s&entry=1&q=%s";

    protected Document pageDocument;

    public List<String> searchTemplateOne(String s, String bookName) {
        try {
            Console.log("Request search url: {}",String.format(searchTemplateUrlOne, s, bookName));
            Document document = Jsoup.connect(String.format(searchTemplateUrlOne, s, bookName)).get();
            Elements bookList = document.getElementsByClass("result-item-title result-game-item-title");
            List<String> result =  bookList.stream()
                    .filter(element -> {
                        Element a = element.getElementsByTag("a").first();
                        return bookName.equals(a.attr("title"));
                    })
                    .map(e -> e.getElementsByTag("a").first().attr("abs:href"))
                    .collect(Collectors.toList());
            Console.log("search result : {}",result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BookInfoDto bookInfoTemplateOne(Document document) {
        Element info = document.getElementById("info");
        String name = info.getElementsByTag("h1").first().text();
        String author = info.getElementsByTag("p").first().text();
        return BookInfoDto.builder().author(author)
                .createDate(new Date())
                .sourceUrl(document.baseUri())
                .name(name)
                .build();
    }

    public List<BookChapterDto> bookChapterTemplateOne(Document document) {
        Element list = document.getElementById("list");
        Elements chapterList = list.getElementsByTag("dd");
        for(int i = 0 ; i < chapterList.size();i++) {
            
        }
    }




    abstract List<String> searchBook(String bookName);


    public void novelPage(String bookUrl) {
         novelPage(bookUrl, 3);
    }

    public void novelPage(String bookUrl,Integer reConnect){
        try {
          this.pageDocument =  Jsoup.connect(bookUrl).get();
        } catch (IOException e) {
            if (reConnect != null && reConnect >= 1) {
                Console.log(e,"Get [{}] page error,reconnect {}",bookUrl,reConnect);
                novelPage(bookUrl, --reConnect);
            }else{
                Console.log(e,"Get [{}] error",bookUrl);
                this.pageDocument = null;
            }
        }
    }

    abstract BookInfoDto searchBookInfo(Document document);

    BookInfoDto bookInfo() {
        return searchBookInfo(pageDocument);
    }


    abstract List<BookChapterDto> bookChapter(Document document);


}
