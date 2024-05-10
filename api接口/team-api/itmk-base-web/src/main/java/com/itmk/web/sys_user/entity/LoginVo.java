package com.itmk.web.sys_user.entity;

import com.itmk.web.sys_menu.entity.CodeVo;
import com.itmk.web.sys_menu.entity.MenuVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginVo {
    private Long userId;
    private String nickName;
    private List<MenuVo> menuList = new ArrayList<>();
    private List<CodeVo> codeList = new ArrayList<>();
}
