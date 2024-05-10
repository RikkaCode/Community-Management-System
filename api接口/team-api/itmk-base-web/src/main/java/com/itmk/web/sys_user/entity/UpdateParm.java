package com.itmk.web.sys_user.entity;

import lombok.Data;


@Data
public class UpdateParm {
    private Long userId;
    private Long sutId;
    private String oldPassword;
    private String newPassword;
}
