package com.itmk.web.team_category.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.team.entity.Team;
import com.itmk.web.team_category.entity.CategoryParm;
import com.itmk.web.team_category.entity.SelectType;
import com.itmk.web.team_category.entity.TeamCategory;
import com.itmk.web.team_category.service.TeamCategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/category")
public class TeamCategoryController {
    @Autowired
    private TeamCategoryService teamCategoryService;

    //新增
    @PostMapping
    public ResultVo add(@RequestBody TeamCategory teamCategory){
        if(teamCategoryService.save(teamCategory)){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody TeamCategory teamCategory){
        if(teamCategoryService.updateById(teamCategory)){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{id}")
    public ResultVo delete(@PathVariable("id") Long id){
        if(teamCategoryService.removeById(id)){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    //列表
    @GetMapping("/list")
    public ResultVo list(CategoryParm parm){
        //构造分页对象
        IPage<TeamCategory> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<TeamCategory> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getName())){
            query.lambda().like(TeamCategory::getName,parm.getName());
        }
        query.lambda().orderByAsc(TeamCategory::getOrderNum);
        IPage<TeamCategory> list = teamCategoryService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    // 获取下拉数据
    @GetMapping("/getSelectList")
    public ResultVo getSelectList() {
        List<TeamCategory> list = teamCategoryService.list();
        List<SelectType> selectList = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream()
                .forEach(item -> {
                    SelectType type = new SelectType();
                    type.setLabel(item.getName());
                    type.setValue(item.getId());
                    selectList.add(type);
                });
        return ResultUtils.success("查询成功", selectList);
    }

    // 网站社团分类
    @GetMapping("/getWebList")
    public ResultVo getWebList(){
        QueryWrapper<TeamCategory> query = new QueryWrapper<>();
        query.lambda().orderByDesc(TeamCategory::getOrderNum);
        List<TeamCategory> list = teamCategoryService.list(query);
        return ResultUtils.success("查询成功", list);
    }
}
