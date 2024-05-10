package com.itmk.web.team.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.news.entity.News;
import com.itmk.web.news.service.impl.NewsServiceImpl;
import com.itmk.web.team.entity.Team;
import com.itmk.web.team.entity.TeamPage;
import com.itmk.web.team.service.TeamService;
import com.itmk.web.team_category.entity.SelectType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/team")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private NewsServiceImpl newsServiceImpl;

    // 新增
    @PostMapping
    public ResultVo add(@RequestBody Team team){
        team.setCreateTime(new Date());
        if(teamService.save(team)){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    // 编辑
    @PutMapping
    public ResultVo edit(@RequestBody Team team){
        if(teamService.updateById(team)){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    // 删除
    @DeleteMapping("/{id}")
    public ResultVo delete(@PathVariable("id") Long id){
        if(teamService.removeById(id)){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    // 列表
    @GetMapping("/list")
    public ResultVo list(TeamPage parm){
        // 构造分页对象
        IPage<Team> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        // 构造查询条件
        QueryWrapper<Team> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getName())){
            query.lambda().like(Team::getName,parm.getName());
        }
        query.lambda().orderByAsc(Team::getCreateTime);
        IPage<Team> list = teamService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    // 推荐的社团
    @GetMapping("/getTopTeam")
    public ResultVo getTopTeam(){
        // 构建查询条件
        QueryWrapper<Team> query = new QueryWrapper<>();
        query.lambda().eq(Team::getHasTop, "1")
                .orderByAsc(Team::getCreateTime);
        List<Team> list = teamService.list(query);
        return ResultUtils.success("查询成功", list);
    }


    // 分类id查社团
    @GetMapping("/getTeamByCategoryId")
    public ResultVo getTeamByCategoryId(Long categoryId){
        // 构造查询条件
        QueryWrapper<Team> query = new QueryWrapper<>();
        query.lambda().eq(Team::getCategoryId,categoryId)
                .orderByAsc(Team::getCreateTime);
        List<Team> list = teamService.list(query);
        return ResultUtils.success("查询成功", list);
    }

    //根据id查询数据
    @GetMapping("/getById")
    public ResultVo getById(Long id){
        Team news = teamService.getById(id);
        return ResultUtils.success("查询成功",news);
    }

    //获取下拉社团数据
    @GetMapping("/getSelectList")
    public ResultVo getSelectList(){
        // 查询所有的分类数据
        List<Team> list = teamService.list();
        // 存储返回值的
        List<SelectType> selectList = new ArrayList<>();
        // 组装下拉数据
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream()
                .forEach(item ->{
                    SelectType type = new SelectType();
                    type.setLabel(item.getName());
                    type.setValue(item.getId());
                    selectList.add(type);
                });
        return ResultUtils.success("查询成功",selectList);
    }
}
