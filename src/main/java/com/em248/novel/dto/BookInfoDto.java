package com.em248.novel.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by geekyzk on 29/11/2017.
 */
@Data
@Builder
public class BookInfoDto {


    private String name;

    private String author;

    private String sourceUrl;

    private Date createDate;

    private Date updateDate;

}
