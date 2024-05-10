package com.itmk.web.team.entity;
import lombok.Data;


@Data
public class TeamPage {
    private Integer currentPage;
    private Integer pageSize;
    private String name;
}
