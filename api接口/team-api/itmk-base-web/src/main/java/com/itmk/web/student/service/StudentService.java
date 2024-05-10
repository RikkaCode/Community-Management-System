package com.itmk.web.student.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.home.entity.EchartItem;
import com.itmk.web.home.entity.StuTotalParm;
import com.itmk.web.student.entity.SteamParm;
import com.itmk.web.student.entity.Student;


import java.util.List;

public interface StudentService extends IService<Student> {
    IPage<Student> getStuPage(SteamParm parm);
    List<Student> exportStuPage(String teamId);
    List<Student> exportStuActivity(String activityId);
    List<EchartItem> stuTotal();
}
