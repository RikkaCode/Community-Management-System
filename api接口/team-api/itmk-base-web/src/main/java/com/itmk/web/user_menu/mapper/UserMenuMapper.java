package com.itmk.web.user_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.sys_menu.entity.SysMenu;
import com.itmk.web.user_menu.entity.UserMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMenuMapper extends BaseMapper<UserMenu>{
    boolean saveUserMenu(@Param("userId") Long userId, @Param("menuIds") List<Long> menuIds);
}
