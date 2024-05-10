package com.itmk.web.join_team.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("join_team")
public class JoinTeam {
    @TableId(type = IdType.AUTO)
    private Long joinId;
    private Long stuId;
    private Long teamId;
    private String status;
    private Date createTime;
}
