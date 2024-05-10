package com.itmk.web.join_activity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.join_activity.entity.JoinActivity;
import com.itmk.web.join_activity.mapper.JoinActivityMapper;
import com.itmk.web.join_activity.service.JoinActivityService;
import com.itmk.web.news.entity.News;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JoinActivityServiceImpl extends ServiceImpl<JoinActivityMapper, JoinActivity> implements JoinActivityService {
    @Override
    public List<News> getMyActivity(Long stuId) {
        return this.baseMapper.getMyActivity(stuId);
    }
}
