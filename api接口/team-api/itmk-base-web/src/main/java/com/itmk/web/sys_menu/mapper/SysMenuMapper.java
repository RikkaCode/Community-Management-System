package com.itmk.web.sys_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.sys_menu.entity.CodeVo;
import com.itmk.web.sys_menu.entity.MenuVo;
import com.itmk.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> getMenuByUserId(@Param("userId") Long userId);
    List<MenuVo> getMenuVoById(@Param("userId") Long userId);
    List<CodeVo> getCodeVoById(@Param("userId") Long userId);
    List<MenuVo> getMenuNoVoById();
    List<CodeVo> getCodeNoVoById();
}