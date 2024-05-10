package com.itmk.web.student.entity;

import lombok.Data;


@Data
public class SteamParm {
    private Integer currentPage;
    private Integer pageSize;
    private String teamId;
    private String name;
}
