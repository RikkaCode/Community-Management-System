import http from "@/http";

// 参数类型
export type NewsParm ={
    currentPage:number,
    pageSize:number,
    type:string
}
export type AddActivityParm ={
    stuId:string,
    activityId:string
}
export type AddTeamParm ={
    stuId:string,
    teamId:string
}

// 查询活动列表
export const getNewsListApi = (parm:NewsParm)=>{
    return http.get("/api/news/list", parm)
}

// 根据id查询
export const getNewsByIdApi = (parm:string)=>{
    return http.get("/api/news/getById",{id:parm})
}

// 报名活动
export const addActivityApi = (parm:AddActivityParm)=>{
    return http.post("/api/joinTActivity", parm)
}

// 报名社团
export const addTeamApi = (parm:AddTeamParm)=>{
    return http.post("/api/joinTeam", parm)
}

// 查询我的社团
export const getMyTeamApi = (stuId:string)=>{
    return http.get("/api/joinTeam/getMyTeam", {stuId:stuId})
}

// 查询我的活动
export const getMyActivityApi = (stuId:string)=>{
    return http.get("/api/joinTActivity/getMyActivity", {stuId:stuId})
}