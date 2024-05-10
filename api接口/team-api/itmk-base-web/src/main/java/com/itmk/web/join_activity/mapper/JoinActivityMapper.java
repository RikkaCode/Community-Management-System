package com.itmk.web.join_activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.join_activity.entity.JoinActivity;
import com.itmk.web.news.entity.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface JoinActivityMapper extends BaseMapper<JoinActivity> {
    List<News> getMyActivity(@Param("stuId") Long stuId);
}
