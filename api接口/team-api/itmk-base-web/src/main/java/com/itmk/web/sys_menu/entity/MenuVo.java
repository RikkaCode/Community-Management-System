package com.itmk.web.sys_menu.entity;

import lombok.Data;

@Data
public class MenuVo {
    private Long menuId;
    private String title;
    private String path;
    private String icon;
}
