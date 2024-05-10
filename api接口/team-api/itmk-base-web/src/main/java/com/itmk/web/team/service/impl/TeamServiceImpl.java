package com.itmk.web.team.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.home.entity.EchartItem;
import com.itmk.web.home.entity.EchartsVo;
import com.itmk.web.team.entity.Team;
import com.itmk.web.team.mapper.TeamMapper;
import com.itmk.web.team.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {
    @Override
    public EchartsVo echartList() {
        List<EchartItem> list = this.baseMapper.echartList();
        EchartsVo vo = new EchartsVo();
        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            names.add(list.get(i).getName());
            values.add(list.get(i).getValue());
        }
        vo.setNames(names);
        vo.setValues(values);
        return vo;
    }

    @Override
    public List<EchartItem> hotTeamList() {
        return this.baseMapper.hotTeamList();
    }

    @Override
    public List<EchartItem> hotActivityList() {
        return this.baseMapper.hotActivityList();
    }
}
