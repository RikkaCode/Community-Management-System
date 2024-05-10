package com.itmk.web.student.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.home.entity.EchartItem;
import com.itmk.web.home.entity.StuTotalParm;
import com.itmk.web.student.entity.SteamParm;
import com.itmk.web.student.entity.Student;
import com.itmk.web.student.mapper.StudentMapper;
import com.itmk.web.student.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public IPage<Student> getStuPage(SteamParm parm) {
        // 构造分页对象
        IPage<Student> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        return this.baseMapper.getStuPage(page, parm.getTeamId(), parm.getName());
    }

    @Override
    public List<Student> exportStuPage(String teamId) {
        return this.baseMapper.exportStuPage(teamId);
    }

    @Override
    public List<Student> exportStuActivity(String activityId) {
        return this.baseMapper.exportStuActivity(activityId);
    }

    @Override
    public List<EchartItem> stuTotal() {
        StuTotalParm total = this.baseMapper.stuTotal();
        List<EchartItem> list = new ArrayList<>();
        if (total != null) {
            EchartItem item = new EchartItem();
            item.setName("男");
            item.setValue(total.getMcount());
            list.add(item);
        }
        if (total != null) {
            EchartItem item = new EchartItem();
            item.setName("女");
            item.setValue(total.getWcount());
            list.add(item);
        }
        return list;
    }
}
