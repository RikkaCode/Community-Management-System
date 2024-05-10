import http from "@/http";
import { NewsType, PageList, NewsApply } from "./NewsModel";

// 新增
export const addNewsApi = (parm:NewsType)=>{
    return http.post("/api/news", parm)
}

// 编辑
export const editNewsApi = (parm:NewsType)=>{
    return http.put("/api/news", parm)
}

// 列表
export const getListApi = (parm:PageList)=>{
    return http.get("/api/news/list", parm)
}

// 删除
export const deleteNewsApi = (parm:NewsType)=>{
    return http.delete(`/api/news/${parm.id}`)
}

// 审核
export const applyNewsApi = (parm:NewsApply)=>{
    return http.put("/api/news", parm)
}