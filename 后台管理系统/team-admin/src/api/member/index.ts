import http from "@/http";

// 列表参数类型
export type MemberListType = {
    currentPage:number,
    pageSize:number,
    name:string,
    phone:string,
}

// 停用列表参数
export type MemberStopType = {
    sutId:string,
    status?:string,
    password?:string,
}

// 列表
export const getMemberListApi = (parm:MemberListType)=>{
    return http.get("/api/student/list", parm)
}

// 停用
export const stopStuApi = (parm:MemberStopType)=>{
    return http.post("/api/student/stopStu", parm)
}

// 启用
export const startStuApi = (parm:MemberStopType)=>{
    return http.post("/api/student/startStu", parm)
}

// 重置密码
export const resetPasswordApi = (parm:MemberStopType)=>{
    return http.post("/api/student/resetPassword", parm)
}

// 删除
export const deleteStuApi = (sutId:string)=>{
    return http.delete(`/api/student/${sutId}`)
}