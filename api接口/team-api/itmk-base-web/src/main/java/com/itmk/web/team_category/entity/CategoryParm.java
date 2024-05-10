package com.itmk.web.team_category.entity;

import lombok.Data;

@Data
public class CategoryParm {
    private Integer currentPage;
    private Integer pageSize;
    private String name;
}
