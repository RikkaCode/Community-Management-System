package com.itmk.web.student.entity;

import lombok.Data;

@Data
public class StuParm {
    private Integer currentPage;
    private Integer pageSize;
    private String name;
    private String phone;
}
