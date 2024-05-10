package com.itmk.web.sys_user.entity;

import lombok.Data;

@Data
public class LoginParm {
    private String username;
    private String password;
    private String code;
}
