package com.itmk.web.join_team.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.join_team.entity.JoinTeam;
import com.itmk.web.join_team.service.JoinTeamService;
import com.itmk.web.team.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/joinTeam")
public class JoinTeamController {
    @Autowired
    private JoinTeamService joinTeamService;

    // 申请加入社团
    @PostMapping
    public ResultVo add(@RequestBody JoinTeam joinTeam){
        // 判断是否加入了社团
        QueryWrapper<JoinTeam> query = new QueryWrapper<>();
        query.lambda().eq(JoinTeam::getStuId,joinTeam.getStuId())
                .eq(JoinTeam::getTeamId,joinTeam.getTeamId());
        JoinTeam one = joinTeamService.getOne(query);
        if(one != null){
            return ResultUtils.error("您已提交申请，不可重复提交!");
        }
        // 保存数据
        joinTeam.setStatus("0");
        joinTeam.setCreateTime(new Date());
        if(joinTeamService.save(joinTeam)){
            return ResultUtils.success("申请成功!");
        }
        return ResultUtils.error("申请失败!");
    }

    // 查询我的社团
    @GetMapping("/getMyTeam")
    public ResultVo getMyTeam(Long stuId){
        List<Team> myTeam = joinTeamService.getMyTeam(stuId);
        return ResultUtils.success("查询成功", myTeam);
    }
}
