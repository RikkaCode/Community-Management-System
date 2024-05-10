import http from "@/http";
import { ListParm, User,LoginType, AssignParm, SaveAssignParm } from "./UserModel";

// 新增
export const addUserApi = (parm:User)=>{
    return http.post("/api/user", parm)
}

// 列表
export const getListApi = (parm:ListParm)=>{
    return http.get("/api/user/list", parm)
}

// 编辑
export const editUserApi = (parm:User)=>{
    return http.put("/api/user", parm)
}

// 删除
export const deleteUserApi = (parm:User)=>{
    return http.delete(`/api/user/${parm.userId}`)
}

// 验证码
export const getImgApi = ()=>{
    return http.post("/api/user/image")
}

// 登录
export const loginApi = (parm:LoginType) => {
    return http.post("/api/user/login", parm)
}

// 获取菜单树数据
export const getAssignTreeApi = (parm:AssignParm)=>{
    return http.get("/api/user/getAssignTree", parm)
}

// 分配菜单保存
export const assignSaveApi = (parm:SaveAssignParm) => {
    return http.post("/api/user/assignSave", parm)
}

// 查询用户菜单
export const getUserMenuApi = (userId:string) => {
    return http.post("/api/user/assignSave", {userId:userId})
}

// 编辑
export const updatePasswordApi = (parm:User) => {
    return http.put("/api/user/updatePassword", parm)
}