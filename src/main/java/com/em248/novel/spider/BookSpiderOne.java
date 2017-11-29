package com.em248.novel.spider;

import com.em248.novel.dto.BookChapterDto;
import com.em248.novel.dto.BookInfoDto;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by geekyzk on 29/11/2017.
 */
public class BookSpiderOne extends AbstractBookSpider{

    private String s = "920895234054625192";

    @Override
    List<String> searchBook(String bookName) {
        List<String> searchResult = searchTemplateOne(s, bookName);
        return searchResult;
    }

    @Override
    BookInfoDto searchBookInfo(Document document) {
        return bookInfoTemplateOne(document);
    }

    @Override
    List<BookChapterDto> bookChapter(Document document) {
        return null;
    }

    public static void main(String[] args) {
        BookSpiderOne one = new BookSpiderOne();
        List<String> result = one.searchBook("一念永恒");
        one.novelPage(result.get(0));
        System.out.println(one.bookInfo());
    }
}
