package com.itmk.web.sys_user.entity;

import com.itmk.web.sys_menu.entity.SysMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PermissonVo {
    // 当前用户菜单
    List<SysMenu> menuList = new ArrayList<>();
    // 回显的数据：被分配的用户菜单
    private Object[] checkList;

}
