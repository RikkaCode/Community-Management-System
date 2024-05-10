package com.itmk.web.join_team.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.join_team.entity.JoinTeam;
import com.itmk.web.join_team.mapper.JoinTeamMapper;
import com.itmk.web.join_team.service.JoinTeamService;
import com.itmk.web.team.entity.Team;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoinTeamServiceImpl extends ServiceImpl<JoinTeamMapper, JoinTeam> implements JoinTeamService {
    @Override
    public List<Team> getMyTeam(Long stuId) {
        return this.baseMapper.getMyTeam(stuId);
    }
}
