package com.itmk.web.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.web.home.entity.StuTotalParm;
import com.itmk.web.student.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StudentMapper extends BaseMapper<Student> {
    IPage<Student> getStuPage(IPage<Student> page, @Param("teamId") String teamId,@Param("name") String name);
    List<Student> exportStuPage(@Param("teamId") String teamId);
    List<Student> exportStuActivity(@Param("activityId") String activityId);
    StuTotalParm stuTotal();
}
