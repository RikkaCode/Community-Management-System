package com.itmk.web.sys_user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_menu.entity.CodeVo;
import com.itmk.web.sys_menu.entity.MakeMenuTree;
import com.itmk.web.sys_menu.entity.SysMenu;
import com.itmk.web.sys_menu.service.SysMenuService;
import com.itmk.web.sys_user.entity.*;
import com.itmk.web.sys_user.service.SysUserService;
import com.itmk.web.user_menu.service.UserMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private UserMenuService userMenuService;

    // 新增
    @PostMapping
    public ResultVo add(@RequestBody SysUser sysUser){
        // 用户查询
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername, sysUser.getUsername())
                .eq(SysUser::getPassword, sysUser.getPassword());
        SysUser one = sysUserService.getOne(query);
        if(one != null){
            return ResultUtils.error("用户名已存在!");
        }
        if(sysUserService.save(sysUser)){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    // 编辑
    @PutMapping
    public ResultVo edit(@RequestBody SysUser sysUser){
        // 用户查询
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername, sysUser.getUsername())
                .eq(SysUser::getPassword, sysUser.getPassword());
        SysUser one = sysUserService.getOne(query);
        if (!one.getUserId().equals(sysUser.getUserId())){
            return ResultUtils.error("用户名已存在!");
        }
        if (sysUserService.updateById(sysUser)){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    // 删除
    @DeleteMapping("/{userId}")
    public ResultVo delete(@PathVariable("userId") Long userId){
        if(sysUserService.removeById(userId)){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    // 列表
    @GetMapping("/list")
    public ResultVo list(PageParm parm){
        // 构造分页对象
        IPage<SysUser> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        // 构造查询条件
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getNickName())){
            query.lambda().like(SysUser::getNickName,parm.getNickName());
        }
        // 分页查询
        IPage<SysUser> list = sysUserService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    // 生成验证码
    @PostMapping("/image")
    public ResultVo imageCode(HttpServletRequest request) {
        // 生成验证码
        String text = defaultKaptcha.createText();
        // 把生成的验证码存到session里面
        HttpSession session = request.getSession();
        session.setAttribute("userCode", text);
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

    // 登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm parm, HttpServletRequest request){
        // 验证码验证
        HttpSession session = request.getSession();
        // 从sesion里面获取session
        String userCode = (String) session.getAttribute("userCode");
        // code从前端传递过来的
        String code = parm.getCode();
        if(StringUtils.isEmpty(userCode)){
            return ResultUtils.error("验证码过期!");
        }
        // 对比验证码
        if(!userCode.equals(code)){
            return ResultUtils.error("验证码错误!");
        }
        // 用户查询
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername,parm.getUsername())
                .eq(SysUser::getPassword,parm.getPassword());
        SysUser one = sysUserService.getOne(query);
        if(one == null){
            return ResultUtils.error("用户名或者密码错误!");
        }
        // 是否是管理员
        List<com.itmk.web.sys_menu.entity.MenuVo> menuVoById = null;
        List<CodeVo> codeVoById = null;
        if (StringUtils.isNotEmpty(one.getIsAdmin()) && one.getIsAdmin().equals("1")) {
            menuVoById = sysMenuService.getMenuNoVoById();
            codeVoById = sysMenuService.getCodeNoVoById();
        }else {
            menuVoById = sysMenuService.getMenuVoById(one.getUserId());
            codeVoById = sysMenuService.getCodeVoById(one.getUserId());
        }
        MenuVos vos = new MenuVos();
        LoginVo vo = new LoginVo();
        vo.setUserId(one.getUserId());
        vo.setNickName(one.getNickName());
        vo.setCodeList(codeVoById);
        vo.setMenuList(menuVoById);
        return ResultUtils.success("登录成功!",vo);
    }

    // 分配权限树的查询与回显
    @GetMapping("/getAssignTree")
    public ResultVo getAssignTree(Long userId,Long assId){
        // 根据用户id查询用户信息
        SysUser sysUser = sysUserService.getById(userId);
        // 判断是否是超级管理员: 超级管理员拥有所有的权限菜单
        List<SysMenu> menuList = null;
        if(StringUtils.isNotEmpty(sysUser.getIsAdmin()) && sysUser.getIsAdmin().equals("1")){
            menuList  = sysMenuService.list();
        }else{ // 不是超级管理员，根据用户id查询
            menuList = sysMenuService.getMenuByUserId(userId);
        }
        // 查询被分配用户的菜单
        List<SysMenu> byMenuByUserId = sysMenuService.getMenuByUserId(assId);
        // 组装树
        List<SysMenu> makeTree = MakeMenuTree.makeTree(menuList,0L);
        // 查询被分配用户的回显菜单id
        List<Long> ids = new ArrayList<>();
        Optional.ofNullable(byMenuByUserId).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null)
                .forEach(item ->{
                    ids.add(item.getMenuId());
                });
        // 返回数据
        PermissonVo vo = new PermissonVo();
        vo.setMenuList(makeTree);
        vo.setCheckList(ids.toArray());
        return ResultUtils.success("查询成功", vo);
    }

    // 分配菜单保存
    @PostMapping("/assignSave")
    public ResultVo assignSave(@RequestBody AssignParm parm){
        // 判断是否是超级管理员
        SysUser user = sysUserService.getById(parm.getAssId());
        if(user != null && StringUtils.isNotEmpty(user.getIsAdmin()) && user.getIsAdmin().equals("1")){
            return ResultUtils.error("当前用户是超级管理员，无需分配菜单!");
        }
        userMenuService.saveUserMenu(parm);
        return ResultUtils.success("分配菜单成功!");
    }

    // 查询菜单权限
//    @GetMapping("/getUserMenus")
//    public ResultVo getUserMenus(Long userId){
//        List<MenuVo> menuVoById = sysMenuService.getMenuVoById(userId);
//        List<CodeVo> codeVoById = sysMenuService.getCodeVoById(userId);
//        MenuVos vos = new MenuVos();
//        vos.setCodeList(codeVoById);
//        vos.setMenuList(menuVoById);
//        return ResultUtils.success("查询成功", vos);
//    }

    // 修改密码
    @PutMapping("/updatePassword")
    public ResultVo updatePassword(@RequestBody UpdateParm parm) {
        // 用户查询,判断原密码是不是正确的
        SysUser user = sysUserService.getById(parm.getUserId());
        if(user != null && !parm.getOldPassword().equals(user.getPassword())){
            return ResultUtils.error("原密码不正确!");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserId(parm.getUserId());
        sysUser.setPassword(parm.getNewPassword());
        if (sysUserService.updateById(sysUser)) {
            return ResultUtils.success("修改成功!");
        }
        return ResultUtils.error("修改失败!");
    }
}
