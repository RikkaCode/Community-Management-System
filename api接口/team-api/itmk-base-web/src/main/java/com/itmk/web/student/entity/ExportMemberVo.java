package com.itmk.web.student.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;


@Data
public class ExportMemberVo {
    @Excel(name = "姓名", orderNum = "1", width=30)
    private String name;
    @Excel(name = "性别", orderNum = "1", width=30,replace = {"男_0", "女_1"})
    private String sex;
    @Excel(name = "电话", orderNum = "1", width=50)
    private String phone;
    @Excel(name = "专业", orderNum = "1", width=50)
    private String major;
    @Excel(name = "班级", orderNum = "1", width=50)
    private String classes;
    @Excel(name = "年级", orderNum = "1", width=50)
    private String grade;

}
