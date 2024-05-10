package com.itmk.web.team.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.home.entity.EchartItem;
import com.itmk.web.home.entity.EchartsVo;
import com.itmk.web.team.entity.Team;

import java.util.List;


public interface TeamService extends IService<Team> {
    EchartsVo echartList();
    List<EchartItem> hotTeamList();
    List<EchartItem> hotActivityList();
}
