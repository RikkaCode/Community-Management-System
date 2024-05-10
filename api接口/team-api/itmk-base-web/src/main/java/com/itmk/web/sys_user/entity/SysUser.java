package com.itmk.web.sys_user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("sys_user")
public class SysUser {
    // MyBatis Plus 默认使用实体类字段名直接映射到数据库列名 ！！！采用驼峰命名法以防止解析错误
    @TableId(type = IdType.AUTO)
    private Long userId;
    private String username;
    private String password;
    private String nickName;
    private String sex;
    private String phone;
    private String status;
    private String isAdmin;
}
