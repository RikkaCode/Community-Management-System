package com.itmk.web.join_activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.join_activity.entity.JoinActivity;
import com.itmk.web.news.entity.News;

import java.util.List;


public interface JoinActivityService extends IService<JoinActivity> {
    List<News> getMyActivity(Long stuId);
}
