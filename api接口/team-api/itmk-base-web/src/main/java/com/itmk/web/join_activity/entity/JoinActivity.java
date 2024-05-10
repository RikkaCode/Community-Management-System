package com.itmk.web.join_activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("join_activity")
public class JoinActivity {
    @TableId(type = IdType.AUTO)
    private Long joinId;
    private Long stuId;
    private Long activityId;
    private Date createTime;
}
