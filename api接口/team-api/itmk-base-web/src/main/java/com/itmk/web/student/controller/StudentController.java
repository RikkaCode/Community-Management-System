package com.itmk.web.student.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.student.entity.*;
import com.itmk.web.student.service.StudentService;
import com.itmk.web.sys_user.entity.UpdateParm;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    // 注册
    @PostMapping("/register")
    public ResultVo register(@RequestBody Student student) {
        // 检测账户和密码是否被占用
        QueryWrapper<Student> query = new QueryWrapper<>();
        query.lambda().eq(Student::getUsername, student.getUsername())
                .eq(Student::getPassword, DigestUtils.md5DigestAsHex(student.getPassword().getBytes()));
        Student one = studentService.getOne(query);
        if (one != null) {
            return ResultUtils.error("账号和密码被占用!");
        }
        student.setCreateTime(new Date());
        student.setStatus("0");
        // 密码加密
        student.setPassword(DigestUtils.md5DigestAsHex(student.getPassword().getBytes()));
        if (studentService.save(student)) {
            return ResultUtils.success("注册成功!");
        }
        return ResultUtils.error("注册失败!");
    }

    // 登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody Student student,HttpServletRequest request) {
        // 验证码验证
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        if(StringUtils.isEmpty(code)){
            return ResultUtils.error("验证码过期!");
        }
        if(!code.equals(student.getCode())){
            return ResultUtils.error("验证码错误!");
        }
        // 根据用户名和密码查询数据库
        QueryWrapper<Student> query = new QueryWrapper<>();
        query.lambda().eq(Student::getUsername, student.getUsername())
                .eq(Student::getPassword, DigestUtils.md5DigestAsHex(student.getPassword().getBytes()));
        Student one = studentService.getOne(query);
        if(one == null){
            return ResultUtils.error("账号或者密码错误!");
        }
        // 判断是否被停用
        if(one.getStatus().equals("1")){
            return ResultUtils.error("账号被停用，请联系管理员!");
        }
        LoginVo vo = new LoginVo();
        BeanUtils.copyProperties(one,vo);
        return ResultUtils.success("登录成功!", vo);
    }

    // 生成验证码
    @PostMapping("/image")
    public ResultVo imageCode(HttpServletRequest request) {
        // 生成验证码  6321
        String text = defaultKaptcha.createText();
        // 把生成的验证码存到session里面
        HttpSession session = request.getSession();
        session.setAttribute("code", text);
        // 生成图片,转换为base64
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(outputStream.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            ResultVo result = new ResultVo("生成成功", 200, captchaBase64);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    // 列表
    @GetMapping("/list")
    public ResultVo list(StuParm parm){
        // 构造分页
        IPage<Student> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        // 构造查询条件
        QueryWrapper<Student> query = new QueryWrapper<>();
        query.lambda().like(StringUtils.isNotEmpty(parm.getName()),Student::getName,parm.getName())
                .like(StringUtils.isNotEmpty(parm.getPhone()),Student::getPhone,parm.getPhone())
                .orderByDesc(Student::getCreateTime);
        IPage<Student> list = studentService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    // 停用
    @PostMapping("/stopStu")
    public ResultVo stopStu(@RequestBody StopParm parm){
        Student student = new Student();
        student.setSutId(parm.getSutId());
        student.setStatus(parm.getStatus());
        if(studentService.updateById(student)){
            return ResultUtils.success("停用成功!");
        }
        return ResultUtils.error("停用失败!");
    }

    // 启用
    @PostMapping("/startStu")
    public ResultVo startStu(@RequestBody StopParm parm){
        Student student = new Student();
        student.setSutId(parm.getSutId());
        student.setStatus(parm.getStatus());
        if(studentService.updateById(student)){
            return ResultUtils.success("启用成功!");
        }
        return ResultUtils.error("启用失败!");
    }

    // 重置密码
    @PostMapping("/resetPassword")
    public ResultVo resetPassword(@RequestBody StopParm parm){
        Student student = new Student();
        student.setSutId(parm.getSutId());
        student.setPassword(DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()));
        if(studentService.updateById(student)){
            return ResultUtils.success("重置密码成功!");
        }
        return ResultUtils.error("重置密码失败!");
    }

    // 删除成员
    @DeleteMapping("/{sutId}")
    public ResultVo deleteStu(@PathVariable("sutId") Long sutId){
        if(studentService.removeById(sutId)){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    // 修改信息
    @PutMapping("/updateInfo")
    public ResultVo updateInfo(@RequestBody Student student){
        if(studentService.updateById(student)){
            return ResultUtils.success("信息修改成功!");
        }
        return ResultUtils.error("信息修改失败!");
    }

    // 修改密码
    @PutMapping("/updatePassword")
    public ResultVo updatePassword(@RequestBody UpdateParm parm) {
        //用户查询,判断原密码是不是正确的
        Student user = studentService.getById(parm.getSutId());
        String old = DigestUtils.md5DigestAsHex(parm.getOldPassword().getBytes());
        String dold = user.getPassword();
        if(user != null && !old.equals(dold)){
            return ResultUtils.error("原密码不正确!");
        }
        Student sysUser = new Student();
        sysUser.setSutId(parm.getSutId());
        sysUser.setPassword(DigestUtils.md5DigestAsHex(parm.getNewPassword().getBytes()));
        if (studentService.updateById(sysUser)) {
            return ResultUtils.success("修改成功!");
        }
        return ResultUtils.error("修改失败!");
    }

    // 列表
    @GetMapping("/getStuPage")
    public ResultVo getStuPage(SteamParm parm){
        IPage<Student> list = studentService.getStuPage(parm);
        return ResultUtils.success("查询成功",list);
    }

    // 导出社团成员
    @RequestMapping("/exportStuInfo")
    public void exportStuInfo(HttpServletResponse response, String teamId) throws Exception {
        // 导出excel得数据类型
        List<ExportMemberVo> memberVoList = new ArrayList<>();
        // 从数据库查询查出来团队的学员
        List<Student> members = studentService.exportStuPage(teamId);
        // 组装导出excel的数据格式
        for (int i = 0; i < members.size(); i++) {
            ExportMemberVo vo = new ExportMemberVo();
            BeanUtils.copyProperties(members.get(i), vo);
            memberVoList.add(vo);
        }
        // 导出
        String fileName = "学生信息.xlsx";
        ExportParams exportParams = new ExportParams();
        exportParams.setType(ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExportMemberVo.class, memberVoList);
        downloadExcel(fileName, workbook, response);
    }

    // 导出活动学生
    @RequestMapping("/exportActivity")
    public void exportActivity(HttpServletResponse response, String activityId) throws Exception {
        // 导出excel的数据类型
        List<ExportMemberVo> memberVoList = new ArrayList<>();
        // 从数据库查询出来团队的学员
        List<Student> members = studentService.exportStuActivity(activityId);
        // 组装导出excel需要的数据格式
        for (int i = 0; i < members.size(); i++) {
            ExportMemberVo vo = new ExportMemberVo();
            BeanUtils.copyProperties(members.get(i), vo);
            memberVoList.add(vo);
        }
        // 导出
        String fileName = "参与人信息.xlsx";
        ExportParams exportParams = new ExportParams();
        exportParams.setType(ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExportMemberVo.class, memberVoList);
        downloadExcel(fileName, workbook, response);
    }

    public static void downloadExcel(String fileName, Workbook workbook, HttpServletResponse response) throws Exception {
        try {
            if (StringUtils.isEmpty(fileName)) {
                throw new RuntimeException("导出文件名不能为空");
            }
            String encodeFileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName);
            response.setHeader("FileName", encodeFileName);
            response.setHeader("Access-Control-Expose-Headers", "FileName");
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            workbook.close();
        }
    }
}
