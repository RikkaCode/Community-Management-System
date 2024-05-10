package com.itmk.web.sys_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.sys_menu.entity.CodeVo;
import com.itmk.web.sys_menu.entity.MenuVo;
import com.itmk.web.sys_menu.entity.SysMenu;
import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> getParent();
    List<SysMenu> getMenuByUserId(Long userId);
    List<MenuVo> getMenuVoById(Long userId);
    List<CodeVo> getCodeVoById(Long userId);
    List<MenuVo> getMenuNoVoById();
    List<CodeVo> getCodeNoVoById();
}
