package com.itmk.web.sys_user.service.imp1;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.sys_user.entity.SysUser;
import com.itmk.web.sys_user.mapper.SysUserMapper;
import com.itmk.web.sys_user.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImp1 extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
