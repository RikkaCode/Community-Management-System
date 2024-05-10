package com.itmk.web.join_activity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.join_activity.entity.JoinActivity;
import com.itmk.web.join_activity.service.JoinActivityService;
import com.itmk.web.news.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/joinTActivity")
public class JoinActivityController {
    @Autowired
    private JoinActivityService joinActivityService;

    // 申请加入
    @PostMapping
    public ResultVo add(@RequestBody JoinActivity joinTeam){
        // 判断是否加入了社团
        QueryWrapper<JoinActivity> query = new QueryWrapper<>();
        query.lambda().eq(JoinActivity::getStuId,joinTeam.getStuId())
                .eq(JoinActivity::getActivityId,joinTeam.getActivityId());
        JoinActivity one = joinActivityService.getOne(query);
        if(one != null){
            return ResultUtils.error("您已提交申请，不可重复提交!");
        }
        // 保存数据
        joinTeam.setCreateTime(new Date());
        if(joinActivityService.save(joinTeam)){
            return ResultUtils.success("申请成功!");
        }
        return ResultUtils.error("申请失败!");
    }

    // 查询我的活动
    @GetMapping("/getMyActivity")
    public ResultVo getMyActivity(Long stuId){
        List<News> myActivity = joinActivityService.getMyActivity(stuId);
        return ResultUtils.success("查询成功", myActivity);
    }
}
