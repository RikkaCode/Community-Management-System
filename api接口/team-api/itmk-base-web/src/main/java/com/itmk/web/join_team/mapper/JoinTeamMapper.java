package com.itmk.web.join_team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.join_team.entity.JoinTeam;
import com.itmk.web.team.entity.Team;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface JoinTeamMapper extends BaseMapper<JoinTeam> {
    List<Team> getMyTeam(@Param("stuId") Long stuId);
}
