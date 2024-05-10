package com.itmk.web.news.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.news.entity.NewParm;
import com.itmk.web.news.entity.News;
import com.itmk.web.news.service.NewsService;
import com.itmk.web.team.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.StringUtils.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    // 新增
    @PostMapping
    public ResultVo add(@RequestBody News news){
        news.setCreateTime(new Date());
        news.setStatus("0");
        if (newsService.save(news)){
            return ResultUtils.success("新增成功！");
        }
        return ResultUtils.error("新增失败！");
    }

    // 编辑
    @PutMapping
    public ResultVo edit(@RequestBody News news){
        if (newsService.updateById(news)){
            return ResultUtils.success("编辑成功！");
        }
        return ResultUtils.error("编辑失败！");
    }

    // 删除
    @DeleteMapping("/{id}")
    public ResultVo delete(@PathVariable("id") Long id){
        if (newsService.removeById(id)){
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败！");
    }

    // 列表
    @GetMapping("/list")
    public ResultVo list(NewParm parm){
        // 构造分页对象
        IPage<News> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        // 构造查询条件
        QueryWrapper<News> query = new QueryWrapper<>();
        query.lambda().eq(StringUtils.isNotEmpty(parm.getType()), News::getType, parm.getType())
                .eq(StringUtils.isNotEmpty(parm.getStatus()), News::getStatus, parm.getStatus())
                .like(StringUtils.isNotEmpty(parm.getTitle()), News::getTitle, parm.getTitle())
                .orderByAsc(News::getCreateTime);
        IPage<News> list = newsService.page(page, query);
        return ResultUtils.success("查询成功！", list);
    }

    // 推荐的活动
    @GetMapping("/getTopActivity")
    public ResultVo getTopActivity(){
        // 构造查询条件
        QueryWrapper<News> query = new QueryWrapper<>();
        query.lambda().eq(News::getType,"0")
                .eq(News::getStatus,"1")
                .eq(News::getHasTop,"1").orderByAsc(News::getCreateTime);
        List<News> list = newsService.list(query);
        return ResultUtils.success("查询成功",list);
    }

    // 推荐的公告
    @GetMapping("/getTopNotice")
    public ResultVo getTopNotice(){
        // 构造查询条件
        QueryWrapper<News> query = new QueryWrapper<>();
        query.lambda().eq(News::getType,"2")
                .eq(News::getHasTop,"1").orderByAsc(News::getCreateTime);
        List<News> list = newsService.list(query);
        return ResultUtils.success("查询成功",list);

    }

    // 最新活动
    @GetMapping("/getNewActivity")
    public ResultVo getNewActivity(){
        // 构造查询条件
        QueryWrapper<News> query = new QueryWrapper<>();
        query.lambda().eq(News::getType,"0")
                .eq(News::getStatus,"1")
                .orderByDesc(News::getCreateTime).last(" limit 8");
        List<News> list = newsService.list(query);
        return ResultUtils.success("查询成功",list);

    }

    // 最新新闻
    @GetMapping("/getNewNotice")
    public ResultVo getNewNotice(){
        //构造查询条件
        QueryWrapper<News> query = new QueryWrapper<>();
        query.lambda().eq(News::getType,"1")
                .orderByDesc(News::getCreateTime).last(" limit 8");
        List<News> list = newsService.list(query);
        return ResultUtils.success("查询成功",list);
    }

    // 根据id查询数据
    @GetMapping("/getById")
    public ResultVo getById(Long id){
        News news = newsService.getById(id);
        return ResultUtils.success("查询成功", news);
    }
}
