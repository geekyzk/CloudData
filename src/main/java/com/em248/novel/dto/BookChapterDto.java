package com.em248.novel.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by geekyzk on 29/11/2017.
 */
@Data
@Builder
public class BookChapterDto {

    private String name;

    private String content;

    private Date creaetTime;

    private String sourceUrl;

    private Integer index;
}
