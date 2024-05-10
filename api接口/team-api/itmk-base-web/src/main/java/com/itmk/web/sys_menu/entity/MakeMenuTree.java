package com.itmk.web.sys_menu.entity;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MakeMenuTree {
    public static List<SysMenu> makeTree(List<SysMenu> menuList, Long pid) {
        // 接收组装后的树数据
        List<SysMenu> list = new ArrayList<>();
        // 判断menuList是否为空, 如果为空直接返回
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    // 组装树数据
                    SysMenu menu = new SysMenu();
                    // 快速复制
                    BeanUtils.copyProperties(item, menu);
                    // 设置
                    menu.setLabel(item.getTitle());
                    menu.setValue(item.getMenuId());
                    //递归查找下级,自己调用自己
                    List<SysMenu> children = makeTree(menuList, item.getMenuId());
                    menu.setChildren(children);
                    list.add(menu);
                });
        return list;
    }
}
