package com.itmk.web.sys_user.entity;

import lombok.Data;

import java.util.List;

@Data
public class AssignParm {
    private Long assId;
    private List<Long> list;
}
