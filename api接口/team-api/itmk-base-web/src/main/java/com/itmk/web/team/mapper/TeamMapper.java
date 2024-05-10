package com.itmk.web.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.home.entity.EchartItem;
import com.itmk.web.team.entity.Team;

import java.util.List;

public interface TeamMapper extends BaseMapper<Team> {
    List<EchartItem> echartList();
    List<EchartItem> hotTeamList();
    List<EchartItem> hotActivityList();
}
