package com.itmk.web.news.entity;

import lombok.Data;

@Data
public class NewParm {
    private Integer currentPage;
    private Integer pageSize;
    private String title;
    private String type;
    private String status;
}
