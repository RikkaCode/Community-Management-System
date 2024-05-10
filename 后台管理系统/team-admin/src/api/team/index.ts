import http from '@/http/index';
import { TeamType, pageList, MemberList } from './TeamModel';

// 获取下拉
export const getSelectApi = ()=>{
    return http.get("/api/category/getSelectList")
}

// 新增社团
export const addTeamApi = (parm:TeamType)=>{
    return http.post("/api/team", parm)
}

// 列表查询
export const getListApi = (parm:pageList)=>{
    return http.get("/api/team/list", parm)
}

// 编辑社团
export const editTeamApi = (parm:TeamType)=>{
    return http.put("/api/team", parm)
}

// 删除社团
export const deleteTeamApi = (parm:TeamType)=>{
    return http.delete(`/api/team/${parm.id}`)
}

// 社团成员列表查询
export const getStuPageApi = (parm:MemberList)=>{
    return http.get("/api/student/getStuPage",parm)
}
export const getSelectListApi = ()=>{
    return http.get("/api/team/getSelectList")
}