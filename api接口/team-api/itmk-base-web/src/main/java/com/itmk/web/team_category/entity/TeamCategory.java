package com.itmk.web.team_category.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("team_category")
public class TeamCategory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer orderNum;
}
