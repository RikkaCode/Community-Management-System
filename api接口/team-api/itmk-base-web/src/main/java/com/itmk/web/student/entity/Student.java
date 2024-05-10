package com.itmk.web.student.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("student")
public class Student {
    @TableId(type = IdType.AUTO)
    private Long sutId;
    private String name;
    private String phone;
    private String grade;
    private String major;
    private String classes;
    private String username;
    private String password;
    private String sex;
    // 验证码
    @TableField(exist = false)
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private String status;
    @TableField(exist = false)
    private String teamName;
}
