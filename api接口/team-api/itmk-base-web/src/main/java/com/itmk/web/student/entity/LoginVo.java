package com.itmk.web.student.entity;

import lombok.Data;

@Data
public class LoginVo {
    private Long sutId;
    private String name;
    private String phone;
    private String grade;
    private String major;
    private String classes;
    private String username;
    private String sex;
}
