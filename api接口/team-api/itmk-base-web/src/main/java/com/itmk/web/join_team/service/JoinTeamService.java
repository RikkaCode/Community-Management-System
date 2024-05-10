package com.itmk.web.join_team.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.join_team.entity.JoinTeam;
import com.itmk.web.team.entity.Team;
import java.util.List;

public interface JoinTeamService extends IService<JoinTeam> {
    List<Team> getMyTeam(Long stuId);
}
