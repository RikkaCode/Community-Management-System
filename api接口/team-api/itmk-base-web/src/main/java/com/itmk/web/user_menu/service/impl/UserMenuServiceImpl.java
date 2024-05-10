package com.itmk.web.user_menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.sys_user.entity.AssignParm;
import com.itmk.web.user_menu.entity.UserMenu;
import com.itmk.web.user_menu.mapper.UserMenuMapper;
import com.itmk.web.user_menu.service.UserMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserMenuServiceImpl extends ServiceImpl<UserMenuMapper, UserMenu> implements UserMenuService {
    @Override
    @Transactional
    public void saveUserMenu(AssignParm parm) {
        // 删除用户原来的菜单数据
        QueryWrapper<UserMenu> query = new QueryWrapper<>();
        query.lambda().eq(UserMenu::getUserId,parm.getAssId());
        int delete = this.baseMapper.delete(query);
        // 重新插入数据
        this.baseMapper.saveUserMenu(parm.getAssId(),parm.getList());
    }
}
