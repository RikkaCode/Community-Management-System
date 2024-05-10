package com.itmk.web.home.controller;

import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.home.entity.EchartItem;
import com.itmk.web.home.entity.EchartsVo;
import com.itmk.web.home.entity.TotalVo;
import com.itmk.web.join_team.service.JoinTeamService;
import com.itmk.web.student.service.StudentService;
import com.itmk.web.team.service.TeamService;
import com.itmk.web.team_category.service.TeamCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private JoinTeamService joinTeamService;
    @Autowired
    private TeamCategoryService teamCategoryService;
    @Autowired
    private TeamService teamService;
    // 统计总数
    @GetMapping("/getTtotal")
    public ResultVo getTtotal(){
        // 注册总数
        int reisterCount = studentService.count();
        // 社员总数
        int memberCount = joinTeamService.count();
        // 分类总数
        int categoryCount = teamCategoryService.count();
        // 社团总数
        int teamCount = teamService.count();
        TotalVo vo = new TotalVo();
        vo.setCategoryCount(categoryCount);
        vo.setMemberCount(memberCount);
        vo.setTeamCount(teamCount);
        vo.setReisterCount(reisterCount);
        return ResultUtils.success("查询成功",vo);
    }

    // 直方图数据
    @GetMapping("/getEcharts")
    public ResultVo getEcharts(){
        EchartsVo vo = teamService.echartList();
        return ResultUtils.success("查询成功",vo);
    }

    // 热门社团
    @GetMapping("/hotTeamList")
    public ResultVo hotTeamList(){
        List<EchartItem> vo = teamService.hotTeamList();
        return ResultUtils.success("查询成功",vo);
    }

    // 热门活动
    @GetMapping("/hotActivityList")
    public ResultVo hotActivityList(){
        List<EchartItem> vo = teamService.hotActivityList();
        return ResultUtils.success("查询成功",vo);
    }

    // 男女比例
    @GetMapping("/stuTotal")
    public ResultVo stuTotal(){
        List<EchartItem> vo = studentService.stuTotal();
        return ResultUtils.success("查询成功",vo);
    }
}
